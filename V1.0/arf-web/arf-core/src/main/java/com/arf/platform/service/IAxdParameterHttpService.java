package com.arf.platform.service;

import javax.servlet.http.HttpServletRequest;

import com.arf.platform.HttpRequestDealBusinessMsg;
import com.arf.platform.vo.DownAxdParameterVo;

public interface IAxdParameterHttpService {
	
	HttpRequestDealBusinessMsg processV1(String communityNo,DownAxdParameterVo vo, HttpServletRequest request);

}
