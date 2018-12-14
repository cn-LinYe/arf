package com.arf.platform.service;

import javax.servlet.http.HttpServletRequest;

import com.arf.platform.HttpRequestDealBusinessMsg;
import com.arf.platform.vo.DownPayModeNotifyVo;

public interface IPayModeNotifyService {

	HttpRequestDealBusinessMsg processV1(String communityNo,DownPayModeNotifyVo vo, HttpServletRequest request);
}
