package com.arf.platform.service;

import javax.servlet.http.HttpServletRequest;

import com.arf.platform.HttpRequestDealBusinessMsg;
import com.arf.platform.vo.DownAbnormalCarNoticeVo;

public interface IAbnormalCarNoticeHttpService {
	
	HttpRequestDealBusinessMsg processV1(String communityNo,DownAbnormalCarNoticeVo vo, HttpServletRequest request);

}
