package com.arf.platform.service;

import javax.servlet.http.HttpServletRequest;

import com.arf.platform.HttpRequestDealBusinessMsg;
import com.arf.platform.vo.DownPakingRateVo;

public interface IPakingRateHttpService {

	HttpRequestDealBusinessMsg processV1(String communityNo,DownPakingRateVo vo, HttpServletRequest request);
	
}
