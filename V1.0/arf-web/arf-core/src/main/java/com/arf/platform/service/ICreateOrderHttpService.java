package com.arf.platform.service;

import javax.servlet.http.HttpServletRequest;

import com.arf.platform.HttpRequestDealBusinessMsg;
import com.arf.platform.vo.DownCreateOrderVo;

public interface ICreateOrderHttpService {

	HttpRequestDealBusinessMsg processV1(String communityNo,DownCreateOrderVo vo, HttpServletRequest request);
}
