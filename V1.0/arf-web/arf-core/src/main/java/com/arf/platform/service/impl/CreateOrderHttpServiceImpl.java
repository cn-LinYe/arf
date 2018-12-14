package com.arf.platform.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.arf.core.util.HttpUtil;
import com.arf.core.util.MStringUntils;
import com.arf.platform.HttpRequestDealBusinessMsg;
import com.arf.platform.LPConstants;
import com.arf.platform.service.ICreateOrderHttpService;
import com.arf.platform.utils.JsonUtils;
import com.arf.platform.vo.DownCreateOrderVo;

import net.sf.json.JSONObject;

@Service("createOrderHttpServiceImpl")
public class CreateOrderHttpServiceImpl implements ICreateOrderHttpService {
	
	@Value("${DWON_REQUEST_URL}")
	protected String DWON_REQUEST_URL;
	
	private static Logger log = LoggerFactory.getLogger(CreateOrderHttpServiceImpl.class);

	@SuppressWarnings({ "rawtypes" })
	@Override
	public HttpRequestDealBusinessMsg processV1(String communityNo,DownCreateOrderVo vo, HttpServletRequest request) {
		String version = vo.getVersion();//接口版本号
		String oderSn = vo.getOderSn();//订单编号
		String berthNo = vo.getBerthNo();//泊位号
		String license = vo.getLicense();//车牌
		String userTel = vo.getUserTel();//手机号码
		String OrderTime = vo.getOrderTime();//下单时间
		String arriveTime = vo.getArriveTime();//预计到达时间
		String waitComeTime = vo.getWaitComeTime();//等待车主到达延长时间
		String leaveTime = vo.getLeaveTime();//预计离开时间
		String userName = vo.getUserName();//车主用户
		String isPayAgent = vo.getIsPayAgent();//是否代付
		Integer parkingType = vo.getParkingType();//停车场类型 0小区 1 紧急场所
		
		//业务数据转为json字符串
		String bizData = JsonUtils.objToJSONStr(vo);
		
		// 参数验证
		HttpRequestDealBusinessMsg message = checkParamV1(version,oderSn,berthNo,license,userTel,OrderTime,arriveTime,
				waitComeTime,leaveTime,userName,isPayAgent,communityNo,parkingType);
		if(message != null){
			return message;
		}
		//请求URL
		String url = DWON_REQUEST_URL;
		log.info("===== 请求url : " + url);
		log.info("===== 请求interface_lanpeng : " + "0，预定车位");
		log.info("===== 请求communityNo : " + communityNo);
		log.info("===== 请求bizData : " + bizData);
		//请求参数
		Map<String,String> params = new HashMap<String,String>();	
		params.put(LPConstants.REQUEST_KEY_INTERFACE, "0");
		params.put(LPConstants.REQUEST_KEY_COMMUNITY_NO, communityNo);
		params.put(LPConstants.REQUEST_KEY_BIZDATA, bizData);
		
		String result = null;
		try {
			//向平台(socket)发送请求，返回json数据
			result = HttpUtil.doHttpPost(url, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("===== 0，预定车位：蓝鹏返回数据 result : " + result + "，communityNo : " + communityNo);
		if(StringUtils.isBlank(result)){
			message = new HttpRequestDealBusinessMsg(HttpRequestDealBusinessMsg.STATUS_FAILED,"无响应");
			return message;
		}else{
			log.info("===== 平台返回数据 result : " + result);
			//解析响应json数据
			Map responseMap = new HashMap();
			try {
				JSONObject jsonObject = JSONObject.fromObject(result);
				responseMap = JsonUtils.parseToMap(jsonObject, responseMap);
			} catch (Exception e) {
				e.printStackTrace();
				//返回的数据不是标准的json数据，即数据缺失或返回错误
				message = new HttpRequestDealBusinessMsg(HttpRequestDealBusinessMsg.STATUS_SUCCESS,"有响应，但返回的数据不是标准的json数据，无法解析");
				return message;
			}
			String code = responseMap.get(LPConstants.RESULTMAP_KEY_STATUS).toString();
			String msg = responseMap.get(LPConstants.RESULTMAP_KEY_MSG).toString();
			Object extrDatas = responseMap.get(LPConstants.RESULTMAP_KEY_DATA);
			
			Map<String,Object> dataMap = new HashMap<String,Object>();
			dataMap.put("extrDatas", extrDatas);
			
			message = new HttpRequestDealBusinessMsg(HttpRequestDealBusinessMsg.STATUS_SUCCESS,"有响应");
			message.setCode(Integer.valueOf(code.trim()));
			message.setMsg(msg);
			message.setBusinessData(dataMap);
		}
		return message;
	}

	private HttpRequestDealBusinessMsg checkParamV1(String version, String oderSn, String berthNo, String license,
			String userTel, String orderTime, String arriveTime, String waitComeTime, String leaveTime, String userName,
			String isPayAgent,String communityNo,Integer parkingType) {
		//非空验证
		if(StringUtils.isBlank(version)
				||StringUtils.isBlank(oderSn)
				||StringUtils.isBlank(license)
				||StringUtils.isBlank(userTel)
				||StringUtils.isBlank(orderTime)
				||StringUtils.isBlank(arriveTime)
				||StringUtils.isBlank(waitComeTime)
				||StringUtils.isBlank(userName)
				||StringUtils.isBlank(isPayAgent)
				||StringUtils.isBlank(communityNo)
				||MStringUntils.isNullObject(parkingType)){
			return new HttpRequestDealBusinessMsg(HttpRequestDealBusinessMsg.STATUS_PARAM_ERROR, "参数校验错误");
		}
		
		return null;
	}

}
