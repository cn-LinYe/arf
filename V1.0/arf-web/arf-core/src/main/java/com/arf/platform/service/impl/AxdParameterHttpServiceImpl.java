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
import com.arf.platform.service.IAxdParameterHttpService;
import com.arf.platform.utils.JsonUtils;
import com.arf.platform.vo.DownAxdParameterVo;

import net.sf.json.JSONObject;

@Service("axdParameterHttpServiceImpl")
public class AxdParameterHttpServiceImpl implements IAxdParameterHttpService {
	
	@Value("${DWON_REQUEST_URL}")
	protected String DWON_REQUEST_URL;
	
	private static Logger log = LoggerFactory.getLogger(AxdParameterHttpServiceImpl.class);

	@SuppressWarnings({ "rawtypes" })
	@Override
	public HttpRequestDealBusinessMsg processV1(String communityNo,DownAxdParameterVo vo, HttpServletRequest request) {
		String version = vo.getVersion();//接口版本号
//		String communityNo = vo.getCommunityNo();//communityNo
//		String userId = vo.getUserId();//用户id(用户名)
		Integer isAxd = vo.getIsAxd();//是否开启安心点 0、不开启 1、开启
		Integer times = vo.getTimes();//设置允许出场次数
		String authorizedTime = vo.getAuthorizedTime();//授权时间0、每天 1、每周 2、每月  多长时间内允许出场次数，与次数(times)配合使用
		String license = vo.getLicense();//车牌号
		
		//业务数据转为json字符串
		String bizData = JsonUtils.objToJSONStr(vo);
		
		// 参数验证
		HttpRequestDealBusinessMsg message = checkParamV1(version,communityNo,isAxd,times,authorizedTime,license);
		if(message != null){
			return message;
		}
		//请求URL
		String url = DWON_REQUEST_URL;
		log.info("===== 请求url : " + url);
		log.info("===== 请求interface_lanpeng : " + "62，设置安心点参数");
		log.info("===== 请求communityNo : " + communityNo);
		log.info("===== 请求bizData : " + bizData);
		//请求参数
		Map<String,String> params = new HashMap<String,String>();
		params.put(LPConstants.REQUEST_KEY_INTERFACE, "62");
		params.put(LPConstants.REQUEST_KEY_COMMUNITY_NO, communityNo);
		params.put(LPConstants.REQUEST_KEY_BIZDATA, bizData);
		
		String result = null;
		try {
			//向平台发送请求，返回json数据
			result = HttpUtil.doHttpPost(url, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("===== 62，设置安心点参数：蓝鹏返回数据 result : " + result + "，communityNo : " + communityNo);
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

	private HttpRequestDealBusinessMsg checkParamV1(String version, String communityNo,Integer isAxd,
			Integer times, String authorizedTime,String license) {
		//非空验证
		if(StringUtils.isBlank(version)
				||StringUtils.isBlank(communityNo)
				||MStringUntils.isNullObject(isAxd)
				||MStringUntils.isNullObject(times)
				||StringUtils.isBlank(authorizedTime)
				||StringUtils.isBlank(license)){
			return new HttpRequestDealBusinessMsg(HttpRequestDealBusinessMsg.STATUS_PARAM_ERROR, "参数校验错误");
		}
		return null;
	}

}
