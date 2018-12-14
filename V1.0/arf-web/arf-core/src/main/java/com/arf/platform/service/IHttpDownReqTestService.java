package com.arf.platform.service;

import javax.servlet.http.HttpServletRequest;

import com.arf.platform.HttpRequestDealBusinessMsg;
import com.arf.platform.vo.DownAbnormalCarNoticeVo;
import com.arf.platform.vo.DownAxdParameterVo;
import com.arf.platform.vo.DownCancelOrderVo;
import com.arf.platform.vo.DownCreateOrderVo;
import com.arf.platform.vo.DownPakingRateVo;
import com.arf.platform.vo.DownPayModeNotifyVo;

public interface IHttpDownReqTestService {

	/**
	 * 预定车位测试
	 * @param vo
	 * @param request
	 * @return
	 */
	HttpRequestDealBusinessMsg createOrder(String communityNo,DownCreateOrderVo vo,HttpServletRequest request);
	
	/**
	 * 取消订单/取消预约测试
	 * @param vo
	 * @param request
	 * @return
	 */
	HttpRequestDealBusinessMsg cancelOrder(String communityNo,DownCancelOrderVo vo,HttpServletRequest request);
	
	/**
	 * 异常车辆通知测试
	 * @param vo
	 * @param request
	 * @return
	 */
	HttpRequestDealBusinessMsg abnormalCarNotice(String communityNo,DownAbnormalCarNoticeVo vo,HttpServletRequest request);
	
	/**
	 * 设置停车场安心点开关及参数(时间、次数等)测试
	 * @param vo
	 * @param request
	 * @return
	 */
	HttpRequestDealBusinessMsg axdParameter(String communityNo,DownAxdParameterVo vo,HttpServletRequest request);
	
	/**
	 * 停车费率查询测试
	 * @param vo
	 * @param request
	 * @return
	 */
	HttpRequestDealBusinessMsg pakingRate(String communityNo,DownPakingRateVo vo,HttpServletRequest request);
	
	/**
	 * 车辆收费方式通知
	 * @param vo
	 * @param request
	 * @return
	 */
	HttpRequestDealBusinessMsg payMode(String communityNo,DownPayModeNotifyVo vo,HttpServletRequest request);
}
