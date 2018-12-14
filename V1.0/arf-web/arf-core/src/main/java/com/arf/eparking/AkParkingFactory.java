package com.arf.eparking;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.arf.core.cache.redis.RedisService;
import com.arf.core.dto.ParkingFeeDto;
import com.arf.core.entity.CommunityModel;
import com.arf.core.entity.ParkRateModel;
import com.arf.core.entity.ParkRateRecordModel;
import com.arf.platform.entity.PParkingcar;
import com.arf.platform.entity.RStoprecord;
import com.arf.platform.service.RStoprecordService;
import com.google.gson.Gson;

@Component
public class AkParkingFactory {
	/**
	 * 此类为调用安快接口方法
	 * **/
	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Value("${SOCKET_SERVER_URL}")
	protected String SOCKET_URL;
	
	@Value("${SOCKET_SERVER_TIMEOUT}")
	protected String SOCKET_TIMEOUT;
	
	@Resource(name = "RStoprecordServiceImpl")
	private RStoprecordService stoprecordService;
	
	@Resource(name = "redisService")
	private RedisService redisService;
	
	/**
	 * 安快 解锁车牌 (安心点设置授权)
	 * @param parkRate
	 * @param community
	 * @param licensePlate
	 * @return
	 */
	public Map<String, Object> saveAxdPlate(Integer carType,ParkRateModel parkRate,CommunityModel community,String licensePlate){
		Long now=new Date().getTime()/1000;//秒
		Map<String, String> data = new HashMap<String, String>();
		data.put("carType", carType.toString());
		data.put("license", licensePlate);
		Integer axdAliveTime = community.getAxdAliveTime();
		if(axdAliveTime != null && axdAliveTime > 0){
			Long authorizationEndTime=now+axdAliveTime*60;
			data.put("authorizationEndTime",authorizationEndTime.toString());
		}
		String bizData = JSONObject.toJSONString(data);
		Map<String, Object> usr = new HashMap<String,Object>();
		try {
			String resJson = httpRequest(31,bizData,community.getCommunity_number());
			usr = parseJSON2Map(resJson);
			log.info(String.format("AK[saveAxdPlate]调用socket-server"+this.SOCKET_URL+"成功, 参数"+JSON.toJSONString(bizData)
			+"interfaceType"+"31"+"communityNo"+community.getCommunity_number()+", 返回值:" + resJson));
			usr.put("parkRate", parkRate);
			return usr;
		} catch (Exception e) {
			e.printStackTrace();
			usr.put("parkRate", parkRate);
			log.info(String.format("AK[saveAxdPlate]调用socket-server"+this.SOCKET_URL+"成功, 参数"+JSON.toJSONString(bizData)
			+"interfaceType"+"31"+"communityNo"+community.getCommunity_number()));
			return usr;
		}
	}
	
	/**
	 * 安快获取停车费信息
	 * @param community
	 * @param parkingCar
	 * @param license
	 * @param arrivedTime
	 * @return
	 */
	public ParkingFeeDto getParkingFee(CommunityModel community,PParkingcar parkingCar,String license){
		String communityNo=community.getCommunity_number();
		JSONObject bizDataAk = new JSONObject();
		bizDataAk.put("licenseNumber", license);
		
		String resultAk = null;
		try {
			resultAk = httpRequest(26,bizDataAk.toJSONString(),communityNo);
			log.info("获取停车费信息fetchParkingFee--AK，道闸返回信息：" + resultAk);
			if(StringUtils.isBlank(resultAk)){
				return new ParkingFeeDto(ParkingFeeDto.SOCKET_SERVER_EXCEPTION, "服务器返回数据为空"); 
			}
			JSONObject resObj = JSON.parseObject(resultAk);
			int code = resObj.getIntValue("code");
			String msg = resObj.getString("msg");
			if (code != 0) {
				return new ParkingFeeDto(code, msg); 
			}
			JSONObject js = JSON.parseObject(resObj.getString("extrDatas"));
			//此处又从新请求了一次socket-server，原因：socket-server有时候会返回{"extrDatas":null,"code":0,"msg":"操作成功"}
			if(js == null){
				Thread.sleep(700);
				String result = httpRequest(26,bizDataAk.toJSONString(),communityNo);
				log.info("获取停车费信息fetchParkingFee--AK，道闸返回信息：" + result);
				if(StringUtils.isBlank(result)){
					return new ParkingFeeDto(ParkingFeeDto.SOCKET_SERVER_EXCEPTION, "服务器返回数据为空"); 
				}
				resObj = JSON.parseObject(result);
				code = resObj.getIntValue("code");
				msg = resObj.getString("msg");
				if (code != 0) {
					return new ParkingFeeDto(code, msg); 
				}
				js = JSON.parseObject(resObj.getString("extrDatas"));
				if(js == null){
					return new ParkingFeeDto(ParkingFeeDto.SOCKET_SERVER_EXCEPTION, "服务器返回数据为空"); 
				}
			}
			Date entryTime = js.getDate("entryTime");
			Date now = new Date();
			Long intime = (now.getTime()-entryTime.getTime())/1000;//停车时长
			String orderNo = js.getString("orderNo");
			ParkingFeeDto feeDto = new ParkingFeeDto(ParkingFeeDto.RET_SUCCESS, msg, intime, parkingCar.getArriveTime().getTime() / 1000,
					communityNo, community.getCommunityName(), license, orderNo,js.getBigDecimal("receivableAmount").divide(new BigDecimal(100)));
			
			List<RStoprecord> paidRecords = stoprecordService.findAllPaidRecord(communityNo, license, parkingCar.getArriveTime());
			BigDecimal paidTotalFee = new BigDecimal(0);
			if(!CollectionUtils.isEmpty(paidRecords)){
				for(RStoprecord paidRecord : paidRecords){
					if(paidRecord.getFee() != null){
						paidTotalFee = paidTotalFee.add(paidRecord.getFee());
					}
					if (paidRecord.getDeductibleFee()!=null) {
						paidTotalFee=paidTotalFee.add(paidRecord.getDeductibleFee());
					}
				}
			}
			
			feeDto.setPaidFee(paidTotalFee);
			if(feeDto.getRet() == ParkingFeeDto.RET_SUCCESS && feeDto.getUnpaidFee() != null){
				feeDto.setTotalFee(paidTotalFee.add(feeDto.getUnpaidFee()));
			}else{
				feeDto.setTotalFee(new BigDecimal(0));
			}
			feeDto.setParkingCarId(parkingCar.getId());
			return feeDto;
		} catch (Exception e) {
			log.error("获取临时停车费信息时出现异常--AK,参数" + JSON.toJSONString(bizDataAk),e);
			return new ParkingFeeDto(ParkingFeeDto.EXCEPTION,e.getMessage());
		}
	}
	/**
	 * 安快临时车缴费通知车闸
	 * @param outTradeNo
	 * @param license
	 * @param totalFee
	 * @param community
	 * @return
	 */
	public String tempParkingFeeNotifyGateway(String outTradeNo,String license,BigDecimal totalFee,CommunityModel community){
		String communityNum = community.getCommunity_number();
		String akBarrierGateOrderNo = redisService.get("PParkingcarServiceImpl.genTempParkingRecord.barrierGateOrderNo:" + outTradeNo);
		log.info("临时停车费通知道闸系统获取到的"+"PParkingcarServiceImpl.genTempParkingRecord.barrierGateOrderNo:" + outTradeNo +
				license + "-" + communityNum+"为:"+akBarrierGateOrderNo);
		JSONObject bizData = new JSONObject();
		bizData.put("arfOrderNo", outTradeNo);
		bizData.put("licenseNumber", license);
		bizData.put("orderNo", akBarrierGateOrderNo);
		bizData.put("deductionAmount", 0);
		Integer payAmount = 0;
		if(totalFee!=null){
			payAmount = totalFee.multiply(new BigDecimal(100)).intValue();
		}
		bizData.put("payAmount", payAmount);
		
		String resJson=null;
		try{
			resJson = httpRequest(27,bizData.toJSONString(),communityNum);
		}catch (Exception e) {
			log.error("下发socket服务器异常",e);
		}
		log.info("[临时停车费通知道闸系统 tempParkingFeeNotifyGateway--AK]道闸返回信息：" + resJson);
		return resJson;
	}
	/**
	 * 安快获取离场滞留时长
	 * @param community
	 * @param license
	 * @return
	 */
	public Integer getStayTime(CommunityModel community,String license){
		Integer stayTime=null;//停车滞留时间
		Map<String, String> data = new HashMap<String, String>();
		data.put("type", "1");
		String bizData = JSONObject.toJSONString(data);
		com.alibaba.fastjson.JSONObject resultObj = null;
		try {
			String jsonStr = httpRequest(36,bizData,community.getCommunity_number());
			log.info("[生成临时停车待支付订单接口genTempParkingRecord--AK]道闸返回信息：" + jsonStr);
			resultObj = JSON.parseObject(jsonStr);
			if (0 != resultObj.getIntValue("code")) {
				log.info("[生成临时停车待支付订单接口genTempParkingRecord--AK]获取车辆离场滞留时间失败");
			}else{
				String resultStr = resultObj.getString("extrDatas");
				com.alibaba.fastjson.JSONObject result = JSON.parseObject(resultStr);
				stayTime=result.getInteger("leaveTime");
			}
		} catch (Exception e) {
			log.error("[生成临时停车待支付订单接口genTempParkingRecord--AK]获取车辆离场滞留时间异常", e);
		}
		return stayTime;
	}
	/**
	 * 安快获取离场车牌
	 * @param gateId
	 * @param communityNumber
	 * @return
	 */
	public String getLeaveLicense(String gateId,String communityNumber){
		String identifyLicense=null;
		Map<String, String> data = new HashMap<String, String>();
		data.put("dataType", "2");
		data.put("no", gateId);
		String bizData = JSONObject.toJSONString(data);
		
		com.alibaba.fastjson.JSONObject resultObj = null;
		try {
			String jsonStr = httpRequest(37,bizData,communityNumber);
			log.info("[微信用户绑定车牌记录wxGetBindLicenses接口获取出口识别车牌--AK]道闸返回信息：" + jsonStr);
			resultObj = JSON.parseObject(jsonStr);
			if (0 != resultObj.getIntValue("code")) {
				log.info("[微信用户绑定车牌记录wxGetBindLicenses接口获取出口识别车牌--AK]获取识别车牌失败");
			} else {
				String resultStr = resultObj.getString("extrDatas");
				if(StringUtils.isNotBlank(resultStr)){
					com.alibaba.fastjson.JSONObject result = JSON.parseObject(resultStr);
					String licenseLists = result.getString("licenseLists");
					if(StringUtils.isNotBlank(licenseLists)){
						JSONArray array = JSONArray.parseArray(licenseLists);
						for(Object obj:array){
							JSONObject json = JSONObject.parseObject(obj.toString());
							String exporNo = json.getString("exporNo");
							if(gateId.equals(exporNo)){
								identifyLicense= json.getString("license");
							}
						}
					}
				}
			}

		} catch (Exception e) {
			log.error("[微信用户绑定车牌记录wxGetBindLicenses接口获取出口识别车牌--AK]请求道闸时发生异常", e);
		}
		return identifyLicense;
	}
	
	/**
	 * 安快月租缴费通知
	 * @param parkRateRecordModel
	 * @param community
	 * @return
	 */
	public boolean manualNotifyEcc(ParkRateRecordModel parkRateRecordModel,CommunityModel community){
		boolean notifyEcc = false;
		String communityNumber=community.getCommunity_number();
		Integer totalFee = (int) (parkRateRecordModel.getAmount().doubleValue() * 100);
		JSONObject bizData = new JSONObject();
		String barrierGateOrderNo = redisService.get("ParkRateController.parkRateService.barrierGateOrderNo:" + parkRateRecordModel.getLicensePlateNumber() + "-" + communityNumber);
		log.info("在线支付月租车费用回调时获取到的"+"ParkRateController.parkRateService.barrierGateOrderNo:" +
		parkRateRecordModel.getLicensePlateNumber() + "-" + communityNumber+"为:"+barrierGateOrderNo);
		
		bizData.put("arfOrderNo", parkRateRecordModel.getOut_trade_no());
		bizData.put("license", parkRateRecordModel.getLicensePlateNumber());
		bizData.put("startTime", parkRateRecordModel.getStartTime());
		bizData.put("endTime", parkRateRecordModel.getEndTime());
		bizData.put("renewMonth", parkRateRecordModel.getChargeMonths());
		bizData.put("deductionFee", 0);
		bizData.put("payFee", totalFee);
		
		String resultJsonstr = null;
		try {
			resultJsonstr = httpRequest(29,bizData.toJSONString(),communityNumber);
			log.info("[月租续费通知道闸manualNotifyEcc--AK]道闸返回信息：" + resultJsonstr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(StringUtils.isBlank(resultJsonstr)){
			notifyEcc = false;
		}else{
			JSONObject resJson = JSON.parseObject(resultJsonstr);
			notifyEcc = resJson.getIntValue("code") == 0;
		}
		return notifyEcc;
	}
	
	/**
	 * 安快获取月租车费用
	 * @param extrDatas
	 * @param parkRateModel
	 * @return
	 */
	public Map<String, Object> getParkRate(Map<String, Object> map,Map<String, Object> extrDatas,ParkRateModel parkRateModel){
		Map<String, String> data = new HashMap<String, String>();
		data.put("licenseNumber", parkRateModel.getLicensePlateNumber());
		String bizData = JSONObject.toJSONString(data);
		com.alibaba.fastjson.JSONObject resultObj = null;
		try {
			String jsonStr = httpRequest(28,bizData,parkRateModel.getCommunityNumber());
			log.info("[月租车查询停车费接口parkRateService--AK]道闸返回信息：" + jsonStr);
			resultObj = JSON.parseObject(jsonStr);
		} catch (Exception e) {
			log.error("[月租车查询停车费接口parkRateService--AK]请求道闸时发生异常", e);
			map.put("code", 63005);
			map.put("msg", "获取费用失败,道闸系统网络异常");
			map.put("extrDatas", new HashMap<String, Object>());
			return map;
		}
		if (0 != resultObj.getIntValue("code")) {
			log.info("[月租车查询停车费接口parkRateService--AK]获取费用失败————>"+resultObj.getString("msg"));
			map.put("code", resultObj.getIntValue("code"));
			map.put("msg", resultObj.getString("msg"));
			map.put("extrDatas", new HashMap<String, Object>());
			return map;
		}
		
		com.alibaba.fastjson.JSONObject extrDataObj = JSON.parseObject(resultObj.getString("extrDatas"));
		if(extrDataObj == null){
			map.put("code", 63005);
			map.put("msg", "小区物业没有设置包月费用信息");
			map.put("extrDatas", new HashMap<String, Object>());
			return map;
		}
		Integer pricetPerMonth=null;
		Integer pricePerQuarter=null;
		Integer pricePerYear=null;
		Integer payRule = null;
		String ruleListStr = extrDataObj.getString("ruleLists");
		if(StringUtils.isNotBlank(ruleListStr)){
			JSONArray array = JSONArray.parseArray(ruleListStr);
			for(Object obj:array){
				JSONObject json = JSONObject.parseObject(obj.toString());
				Integer rule = json.getInteger("payRule");
				Integer payUnit = json.getInteger("payUnit");
				Integer payAmount = json.getInteger("payAmount");
				if(payUnit==1){
					if(rule==1){
						pricetPerMonth=payAmount;
						payRule = rule;
					}
					if(rule==2){
						pricePerQuarter=payAmount;
						//payRule = rule;
					}
					if(rule==3){
						pricePerYear=payAmount;
						//payRule = rule;
					}
					if(rule==4){
						pricetPerMonth=payAmount*30;
						payRule = rule;
					}
				}
			}
		}
		if(payRule==null){
			log.info("[月租车查询停车费接口parkRateService--LP]获取费用失败");
			map.put("code", 63005);
			String msg = "抱歉,物业管理处还没有设置月租停车费用信息";
			map.put("msg", msg);
			return map;
		}
		extrDatas.put("propretyID", parkRateModel.getPropretyId());
		extrDatas.put("name", parkRateModel.getName());
		extrDatas.put("pricetPerMonth",pricetPerMonth);
		extrDatas.put("pricePerYear", pricePerYear);
		extrDatas.put("pricePerQuarter", pricePerQuarter);
		extrDatas.put("licenseEndTime", extrDataObj.get("payEndTime"));
		extrDatas.put("monthlyRentType",payRule);
		log.info("[月租车查询停车费接口parkRateService--AK]获取费用成功");
		
		map.put("code", 63003);
		map.put("msg", "获取成功");
		map.put("extrDatas", extrDatas);
		return map;
	}
	
	/**
	 * 安快同步白名单
	 * @param communityNumber
	 * @return
	 */
	public Map<String, Object> syncLicense(String communityNumber){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String bizData = JSONObject.toJSONString(new HashMap<String, String>());
			String jsonStr = "";
			try {
				jsonStr = httpRequest(34,bizData,communityNumber);
			} catch (Exception e) {
				e.printStackTrace();
				map.put("code", "47003");
				map.put("msg", "同步失败");
				log.info("[同步白名单syncLicense-AK socket]同步失败, communityNumber:%s", communityNumber);
			}
			if (!StringUtils.isEmpty(jsonStr)) {
				com.alibaba.fastjson.JSONObject json = JSON.parseObject(jsonStr);
				if (json.getIntValue("code") != 0) {
					map.put("code", "47001");
					map.put("msg", "同步失败");
					log.info("[同步白名单syncLicense-AK socket]同步失败, communityNumber:%s", communityNumber);
					return map;
				}
				map.put("code", "47002");
				map.put("msg", "同步成功");
				log.info("[同步白名单syncLicense-AK socket]同步成功, communityNumber:%s", communityNumber);
			} else {
				map.put("code", "47003");
				map.put("msg", "同步失败");
				log.info("[同步白名单syncLicense-AK socket]同步失败, communityNumber:%s", communityNumber);
			}
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", "47003");
			map.put("msg", "同步失败");
			return map;
		}
	}
	
	/**
	 * 安快开启关闭安心点（设置安心点）
	 * @param isAxd
	 * @param communityNumber
	 * @return
	 */
	public Map<String, Object> setAxd(Integer isAxd,String communityNumber){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Map<String, String> data = new HashMap<String, String>();
			String type="1";
			if(isAxd==0){
				type="2";
			}else{
				type="1";
			}
			data.put("type",type);
			String bizData = JSONObject.toJSONString(data);
			log.info(String
					.format("[设置安心点setAxd: AK]通过调用Socket-Server开始设置安心点---->url='" + this.SOCKET_URL + "'"));
			String res =  httpRequest(38,bizData,communityNumber);
			com.alibaba.fastjson.JSONObject json = JSON.parseObject(res);
			log.info(String.format("[设置安心点setAxd: AK]通过调用Socket-Server设置安心点的返回数据---->" + res));
			int code = json.getIntValue("code");
			if (code == 0) {
				map.put("code", "49003");
				map.put("msg", "设置成功");
				log.info(String.format("[设置安心点setAxd:AK]设置成功," + "communityNumber:%s,isAxd:%s",
						communityNumber, isAxd));
				return map;
			}
			map.put("code", code);
			map.put("msg", "云端已设置该小区成功，但下发道闸失败(" + json.getString("msg") + ")");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", "49004");
			map.put("msg", "云端已设置该小区成功，但下发道闸失败(服务器异常)");
			log.error(String.format("[设置安心点setAxd: AK]服务器异常," + "communityNumber:%s,isAxd:%s",
					communityNumber, isAxd));
		}
		return map;
	}
	
	/**
	 * 向道闸发送http请求
	 * @param interfaceType  操作类型
	 * @param bizData  参数
	 * @param communityNumber 小区编号
	 * @return
	 */
	private String httpRequest(Integer interfaceType,String bizData,String communityNumber){
		try {
			HttpHeaders headers = new HttpHeaders();
			MultiValueMap<String, String> map_parm = new LinkedMultiValueMap<String, String>();
			map_parm.add("interfaceType", interfaceType.toString());
			map_parm.add("communityNumber", communityNumber);
			map_parm.add("bizData", bizData);
			HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(
					map_parm, headers);
			SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
			Integer TIMEOUT=5000;
			if(StringUtils.isNotBlank(this.SOCKET_TIMEOUT)){
				TIMEOUT=Integer.parseInt(SOCKET_TIMEOUT);
			}
			requestFactory.setReadTimeout(TIMEOUT);
			requestFactory.setConnectTimeout(TIMEOUT);
			RestTemplate rs = new RestTemplate(requestFactory);
			String jsonStr = rs.postForObject(this.SOCKET_URL, requestEntity, String.class);
			return jsonStr;
		} catch (Exception e) {
			log.error(String.format(">>>>>[向安快道闸发送http请求]异常"),e);
			return null;
		}
	}
	
	public static Map<String, Object> parseJSON2Map(String jsonStr) {
		@SuppressWarnings("unchecked")
		Map<String, Object> map = new Gson().fromJson(jsonStr, Map.class);
		return map;
	}
	
}
