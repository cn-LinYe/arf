package com.arf.platform.service;

import javax.servlet.http.HttpServletRequest;

import com.arf.platform.HttpRequestDealBusinessMsg;
import com.arf.platform.vo.DownCancelOrderVo;

public interface ICancelOrderHttpService {

	HttpRequestDealBusinessMsg processV1(String communityNo,DownCancelOrderVo vo, HttpServletRequest request);
}
