package com.arf.platform.service.impl;

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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.arf.core.cache.redis.RedisService;
import com.arf.core.entity.CommunityModel;
import com.arf.core.entity.LicensePlateModel;
import com.arf.core.entity.ParkRateModel;
import com.arf.core.service.LicensePlateModelService;
import com.arf.core.service.ParkRateModelService;
import com.arf.core.util.HttpUtil;
import com.arf.eparking.entity.ParkingInfo;
import com.arf.eparking.entity.ParkingOrderRecord;
import com.arf.eparking.service.ParkingOrderRecordService;
import com.arf.platform.LPConstants;
import com.arf.platform.entity.PParkingcar;
import com.arf.platform.entity.PrivilegeCar;
import com.arf.platform.service.BaseService;
import com.arf.platform.service.IParkingUpReportBusinessDealService;
import com.arf.platform.service.PParkingcarService;
import com.arf.platform.vo.ModifyArrivedLicenseVo;
import com.arf.platform.vo.RequestDataVo;
import com.google.gson.Gson;

@Transactional
@Service("modifyArrivedLicenseBusinessDealServiceImpl")
public class ModifyArrivedLicenseBusinessDealServiceImpl implements IParkingUpReportBusinessDealService {

	private static Logger log = LoggerFactory.getLogger(ModifyArrivedLicenseBusinessDealServiceImpl.class);
	
	@Resource(name = "PParkingcarServiceImpl")
	private PParkingcarService pParkingcarServiceImpl;
	
	@Resource(name = "parkRateModelServiceImpl")
	private ParkRateModelService parkRateModelServiceImpl;
	
	@Resource(name = "licensePlateModelServiceImpl")
	private LicensePlateModelService licensePlateModelServiceImpl;
	
	@Resource(name = "parkingOrderRecordServiceImpl")
	private ParkingOrderRecordService parkingOrderRecordServiceImpl;
	
	@Resource(name = "redisService")
	private RedisService redisService;
	
	@Value("${DWON_REQUEST_URL}")
	protected String DWON_REQUEST_URL;
	
	@Override
	public Map<String, Object> process(String version, String communityNo,RequestDataVo vo) {
		Map<String,Object> map = null;
		int ver = Integer.parseInt(version.trim());
		//判断版本号
		if(ver == 1){
			if(vo.getParkingType() == BaseService.PARKING_TYPE_COMMUNITY){
				map = processVersion1((ModifyArrivedLicenseVo)vo);
			}else if(vo.getParkingType() == BaseService.PARKING_TYPE_EMERGENCY){
				map = processVersion1Emergency((ModifyArrivedLicenseVo)vo);
			}
			if(map != null){
				return map;
			}
		}
		map = new HashMap<String,Object>();
		map.put(LPConstants.RESULTMAP_KEY_STATUS,LPConstants.RETURNCODE_PARAM_ERROR );
		map.put(LPConstants.RESULTMAP_KEY_MSG, "Param Error!");
		map.put(LPConstants.RESULTMAP_KEY_DATA, null);
		return map;
	}

	private Map<String, Object> processVersion1(ModifyArrivedLicenseVo vo) {
		Map<String,Object> map = new HashMap<String,Object>();
		String communityNo = vo.getCommunityNo();//小区编号(停车场编码)
//		CommunityModel communityModel = vo.getCommunity();
		String license = vo.getLicense();//原始车牌号码
		String modifiedLicense = vo.getModifiedLicense();//修改后的车牌号码
		Long timestamp = vo.getTimestamp();//道闸系统当前Unix时间戳
		Long arrivedTimestamp = vo.getArrivedTimestamp();//车辆原始来车时间Unix时间戳
		Long modifiedArrivedTimestamp = vo.getModifiedArrivedTimestamp();//修改后的来车时间Unix时间戳,保留字段,如需要对来车时间也进行修改,可使用该字段
		//来车记录表
		PParkingcar pParkingcar = pParkingcarServiceImpl.findByCommLicArr(communityNo, license, new Date(Long.valueOf(arrivedTimestamp)*1000l));
		if(pParkingcar != null){
			pParkingcar.setLicense(modifiedLicense);
			pParkingcarServiceImpl.update(pParkingcar);
		}else{
			map.put(LPConstants.RESULTMAP_KEY_STATUS, -201);
			map.put(LPConstants.RESULTMAP_KEY_MSG, "not find the arrived record");
			map.put(LPConstants.RESULTMAP_KEY_DATA, null);
			return map;
		}
		//白名单表修改进出状态
		ParkRateModel parkRateModel = parkRateModelServiceImpl.selectByLicenseComunity(communityNo, license);
		if(parkRateModel != null){
			parkRateModel.setInStatus(1);//进出状态修改 1:入 0:出
			parkRateModel.setInTime(Integer.valueOf(arrivedTimestamp.toString()));//进场时间
			parkRateModel.setModifyDate(new Date());
			parkRateModelServiceImpl.update(parkRateModel);
		}
		//车牌表获取用户名
		String userName = null;
		List<LicensePlateModel> listLicensePlateModel = licensePlateModelServiceImpl.findByLicenseAndNotNulluser(license);
		if(CollectionUtils.isNotEmpty(listLicensePlateModel)){
			LicensePlateModel licensePlateModel = listLicensePlateModel.get(0);
			userName = licensePlateModel.getUser_name();
		}
		//如果是白名单发送安心点指令
		Short stopType = pParkingcar.getStopType();
		if(parkRateModel != null){
			saveAxd(vo, parkRateModel, userName,(int)stopType);
		}
		map.put(LPConstants.RESULTMAP_KEY_STATUS, LPConstants.RETURNCODE_SUCCESS);
		map.put(LPConstants.RESULTMAP_KEY_MSG, "OK");
		map.put(LPConstants.RESULTMAP_KEY_DATA, null);
		return map;
	}
	
	private Map<String, Object> processVersion1Emergency(ModifyArrivedLicenseVo vo) {
		Map<String,Object> map = new HashMap<String,Object>();
		String communityNo = vo.getCommunityNo();//小区编号(停车场编码)
//		ParkingInfo parkingInfo = vo.getParkingInfo();
		String license = vo.getLicense();//原始车牌号码
		String modifiedLicense = vo.getModifiedLicense();//修改后的车牌号码
		Long timestamp = vo.getTimestamp();//道闸系统当前Unix时间戳
		Long arrivedTimestamp = vo.getArrivedTimestamp();//车辆原始来车时间Unix时间戳
		Long modifiedArrivedTimestamp = vo.getModifiedArrivedTimestamp();//修改后的来车时间Unix时间戳,保留字段,如需要对来车时间也进行修改,可使用该字段
		Integer fieldType = vo.getFieldType();//0场外 1场内
		//场内，是否是预约车？
		if(fieldType == 1){
			ParkingOrderRecord parkingOrderRecord = parkingOrderRecordServiceImpl.findByLicenArriveTime(license,new Date());
			if(parkingOrderRecord != null){//订单存在，修改订单状态
				if(parkingOrderRecord.getStatus() == ParkingOrderRecord.Status.Waiting.ordinal()){
					parkingOrderRecord.setStatus(ParkingOrderRecord.Status.Finished.ordinal());
					parkingOrderRecord.setArriveTime(new Date(Long.valueOf(arrivedTimestamp) * 1000L));
					parkingOrderRecordServiceImpl.update(parkingOrderRecord);
				}
			}
			//白名单表修改进出状态
			ParkRateModel parkRateModel = parkRateModelServiceImpl.selectByLicenseComunity(communityNo, license);
			if(parkRateModel != null){
				parkRateModel.setInStatus(1);//进出状态修改 1:入 0:出
				parkRateModel.setInTime(Integer.valueOf(arrivedTimestamp.toString()));//进场时间
				parkRateModel.setModifyDate(new Date());
				parkRateModelServiceImpl.update(parkRateModel);
			}
			//车牌表获取用户名
			String userName = null;
			List<LicensePlateModel> listLicensePlateModel = licensePlateModelServiceImpl.findByLicenseAndNotNulluser(license);
			if(CollectionUtils.isNotEmpty(listLicensePlateModel)){
				LicensePlateModel licensePlateModel = listLicensePlateModel.get(0);
				userName = licensePlateModel.getUser_name();
			}
			//如果是白名单发送安心点指令
			if(parkRateModel != null){
				int stopType = 0;//停车类型：0月卡车,1临时车,2免费车,3电子账户车
				saveAxd(vo, parkRateModel, userName,stopType);
			}
		}else if(fieldType == 0){//场外
			//来车记录表
			PParkingcar pParkingcar = pParkingcarServiceImpl.findByCommLicArr(communityNo, license, new Date(Long.valueOf(arrivedTimestamp)*1000l));
			if(pParkingcar != null){
				pParkingcar.setLicense(modifiedLicense);
				pParkingcarServiceImpl.update(pParkingcar);
			}else{
				map.put(LPConstants.RESULTMAP_KEY_STATUS, -201);
				map.put(LPConstants.RESULTMAP_KEY_MSG, "not find the arrived record");
				map.put(LPConstants.RESULTMAP_KEY_DATA, null);
				return map;
			}
			//白名单表修改进出状态
			ParkRateModel parkRateModel = parkRateModelServiceImpl.selectByLicenseComunity(communityNo, license);
			if(parkRateModel != null){
				parkRateModel.setInStatus(1);//进出状态修改 1:入 0:出
				parkRateModel.setInTime(Integer.valueOf(arrivedTimestamp.toString()));//进场时间
				parkRateModel.setModifyDate(new Date());
				parkRateModelServiceImpl.update(parkRateModel);
			}
			//车牌表获取用户名
			String userName = null;
			List<LicensePlateModel> listLicensePlateModel = licensePlateModelServiceImpl.findByLicenseAndNotNulluser(license);
			if(CollectionUtils.isNotEmpty(listLicensePlateModel)){
				LicensePlateModel licensePlateModel = listLicensePlateModel.get(0);
				userName = licensePlateModel.getUser_name();
			}
			//如果是白名单发送安心点指令
			Short stopType = pParkingcar.getStopType();
			if(parkRateModel != null){
				saveAxd(vo, parkRateModel, userName,(int)stopType);
			}
		}
		map = new HashMap<String,Object>();
		map.put(LPConstants.RESULTMAP_KEY_STATUS, LPConstants.RETURNCODE_SUCCESS);
		map.put(LPConstants.RESULTMAP_KEY_MSG, "OK");
		map.put(LPConstants.RESULTMAP_KEY_DATA, null);
		return map;
	}
	
	private void saveAxd(ModifyArrivedLicenseVo vo,ParkRateModel parkRateModel,String userName,Integer stopType){
		String license = vo.getLicense();
		try {
			boolean isCommand = false;
			String isOpening = null;
			String axdTime = redisService.get(ParkRateModel.Key_Cache_Inout_Free_List_Prefix + ":" + vo.getCommunityNo() + "_" + license);
			if(StringUtils.isNotBlank(axdTime)){//缓存中存在，说明在自由出入范围内(用户使用过安心点)
				if(stopType == 0 && parkRateModel != null){//月租车
					//发送开闸指令
					isCommand = true;
					isOpening = "1";
				}
			}else{//缓存中不存在，说明在自由出入范围外(用户很久没使用安心点；或者是：用户没有下载安心点，或下载了安心点没有绑定车辆)
				if(stopType == 0 && parkRateModel != null){//月租车
					String community = parkRateModel.getCommunityNumber();
					PrivilegeCar privilegeCar = parkRateModelServiceImpl.findPrivilegeCarByLicenseCommunity(license, community);
					if(privilegeCar != null){//特权车
						//发送开闸指令
						isCommand = true;
						isOpening = "1";
					}else{
						//发送锁闸指令
						isCommand = true;
						isOpening = "3";
					}
				}
			}
			if(userName == null || "nullUser".equals(userName)){
				userName = "";
			}
			if(isCommand){
				Map<String, Object> mapReq = new HashMap<String, Object>();
				final Map<String, String> mapLP = new HashMap<String, String>();
				final String url = DWON_REQUEST_URL;
				mapReq.put("version", 1);
				mapReq.put("userId", userName);
				mapReq.put("license", license);
				mapReq.put("isOpening", isOpening);
				String mapIn = new Gson().toJson(mapReq);
				mapLP.put("interface_lanpeng", "61");
				mapLP.put("communityNo", vo.getCommunityNo());
				mapLP.put("bizData", mapIn);
				String result = HttpUtil.doHttpPost(url, mapLP);
				log.info(String.format(">>>>>:[停车场来车汇报processVersion1]调用蓝鹏开闸/锁闸授权指令"+url+"成功,参数"+JSON.toJSONString(mapLP)+",返回值:" + result));
				JSONObject jsonObject = JSONObject.parseObject(result);
				if(jsonObject.getInteger("code") == 0){
					if("1".equals(isOpening)){
						parkRateModel.setUserLocks(1);
						parkRateModel.setLock_status(1);
					}else if("3".equals(isOpening)){
						parkRateModel.setUserLocks(3);
						parkRateModel.setLock_status(3);
					}
					parkRateModelServiceImpl.update(parkRateModel);
				}
			}
		} catch (Exception e) {
			log.error(">>>>>:[停车场来车汇报processVersion1]自由出入配置异常", e);
		}
	}

}
