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
import com.arf.platform.HttpRequestDealBusinessMsg;
import com.arf.platform.LPConstants;
import com.arf.platform.service.IAbnormalCarNoticeHttpService;
import com.arf.platform.utils.JsonUtils;
import com.arf.platform.vo.DownAbnormalCarNoticeVo;

import net.sf.json.JSONObject;

@Service("abnormalCarNoticeHttpServiceImpl")
public class AbnormalCarNoticeHttpServiceImpl implements IAbnormalCarNoticeHttpService {
	
	@Value("${DWON_REQUEST_URL}")
	protected String DWON_REQUEST_URL;
	
	private static Logger log = LoggerFactory.getLogger(AbnormalCarNoticeHttpServiceImpl.class);

	@SuppressWarnings({ "rawtypes" })
	@Override
	public HttpRequestDealBusinessMsg processV1(String communityNo,DownAbnormalCarNoticeVo vo, HttpServletRequest request) {
		String version = vo.getVersion();//接口版本号
		String licenseLen = vo.getLicenseLen();//车牌长度，字节数，为如果为0时没有license
		String license = vo.getLicense();//车牌
		String exceptionCode = vo.getExceptionCode();//异常代码
		
		//业务数据转为json字符串
		String bizData = JsonUtils.objToJSONStr(vo);
		
		// 参数验证
		HttpRequestDealBusinessMsg message = checkParamV1(version,licenseLen,license,exceptionCode,communityNo);
		if(message != null){
			return message;
		}
		//请求URL
		String url = DWON_REQUEST_URL;
		log.info("===== 请求url : " + url);
		log.info("===== 请求interface_lanpeng : " + "10，异常车辆通知");
		log.info("===== 请求communityNo : " + communityNo);
		log.info("===== 请求bizData : " + bizData);
		//请求参数
		Map<String,String> params = new HashMap<String,String>();
		params.put(LPConstants.REQUEST_KEY_INTERFACE, "10");
		params.put(LPConstants.REQUEST_KEY_COMMUNITY_NO, communityNo);
		params.put(LPConstants.REQUEST_KEY_BIZDATA, bizData);
		
		String result = null;
		try {
			//向平台发送请求，返回json数据
			result = HttpUtil.doHttpPost(url, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("===== 10，异常车辆通知：蓝鹏返回数据 result : " + result + "，communityNo : " + communityNo);
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

	private HttpRequestDealBusinessMsg checkParamV1(String version, String licenseLen, String license,
			String exceptionCode,String communityNo) {
		//非空验证
		if(StringUtils.isBlank(version)
				||StringUtils.isBlank(licenseLen)
				||StringUtils.isBlank(exceptionCode)
				||StringUtils.isBlank(license)
				||StringUtils.isBlank(communityNo)){
			return new HttpRequestDealBusinessMsg(HttpRequestDealBusinessMsg.STATUS_PARAM_ERROR, "参数校验错误");
		}
		
		return null;
	}

}
