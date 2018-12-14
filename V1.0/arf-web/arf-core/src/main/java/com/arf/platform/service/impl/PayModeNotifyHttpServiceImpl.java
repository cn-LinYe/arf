package com.arf.platform.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.arf.core.util.HttpUtil;
import com.arf.core.util.MStringUntils;
import com.arf.platform.HttpRequestDealBusinessMsg;
import com.arf.platform.LPConstants;
import com.arf.platform.service.IPayModeNotifyService;
import com.arf.platform.utils.JsonUtils;
import com.arf.platform.vo.DownPayModeNotifyVo;

@Service("payModeNotifyHttpServiceImpl")
public class PayModeNotifyHttpServiceImpl implements IPayModeNotifyService {
	
	@Value("${DWON_REQUEST_URL}")
	protected String DWON_REQUEST_URL;
	
	private static Logger log = LoggerFactory.getLogger(PayModeNotifyHttpServiceImpl.class);

	@SuppressWarnings({ "rawtypes" })
	@Override
	public HttpRequestDealBusinessMsg processV1(String communityNo,DownPayModeNotifyVo vo, HttpServletRequest request) {
		String version = vo.getVersion();//接口版本号,目前是1
		String license = vo.getLicense();//车牌号码，UTF8编码
		Integer payMode = vo.getPayMode();//0：无安尔发结算方式；1：电子钱包结算
		Integer balance = vo.getBalance();//电子钱包余额(分)
		String orderNo = vo.getOrderNo();//订单编号，UTF8编码
		String berthNo = vo.getBerthNo();//泊位编号，UTF8编码
		Integer isOrder = vo.getIsOrder();//0：否；1：是
		
		//业务数据转为json字符串
		String bizData = JsonUtils.objToJSONStr(vo);
		
		// 参数验证
		HttpRequestDealBusinessMsg message = checkParamV1(version,license,payMode,balance,orderNo,berthNo,isOrder);
		if(message != null){
			return message;
		}
		//请求URL
		String url = DWON_REQUEST_URL;
		log.info("===== 请求url : " + url);
		log.info("===== 请求interface_lanpeng : " + "65，车辆收费方式通知");
		log.info("===== 请求communityNo : " + communityNo);
		log.info("===== 请求bizData : " + bizData);
		//请求参数
		Map<String,String> params = new HashMap<String,String>();
		params.put(LPConstants.REQUEST_KEY_INTERFACE, "65");
		params.put(LPConstants.REQUEST_KEY_COMMUNITY_NO, communityNo);
		params.put(LPConstants.REQUEST_KEY_BIZDATA, bizData);
		String result = null;
		try {
			//向平台发送请求，返回json数据
			result = HttpUtil.doHttpPost(url, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("===== 65，车辆收费方式通知：蓝鹏返回数据 result : " + result + "，communityNo : " + communityNo);
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

	private HttpRequestDealBusinessMsg checkParamV1(String version,String license, Integer payMode, Integer balance, String orderNo,
			String berthNo, Integer isOrder) {
		//非空验证
		if(StringUtils.isBlank(version)
				||StringUtils.isBlank(license)
				||MStringUntils.isNullObject(payMode)
				||MStringUntils.isNullObject(balance)
				||berthNo == null
				||MStringUntils.isNullObject(isOrder)){
			return new HttpRequestDealBusinessMsg(HttpRequestDealBusinessMsg.STATUS_PARAM_ERROR, "参数校验错误");
		}
				
		return null;
	}

}
