package com.arf.platform.service.impl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.arf.platform.HttpRequestDealBusinessMsg;
import com.arf.platform.service.IAbnormalCarNoticeHttpService;
import com.arf.platform.service.IAxdParameterHttpService;
import com.arf.platform.service.ICancelOrderHttpService;
import com.arf.platform.service.ICreateOrderHttpService;
import com.arf.platform.service.IHttpDownReqTestService;
import com.arf.platform.service.IPakingRateHttpService;
import com.arf.platform.service.IPayModeNotifyService;
import com.arf.platform.vo.DownAbnormalCarNoticeVo;
import com.arf.platform.vo.DownAxdParameterVo;
import com.arf.platform.vo.DownCancelOrderVo;
import com.arf.platform.vo.DownCreateOrderVo;
import com.arf.platform.vo.DownPakingRateVo;
import com.arf.platform.vo.DownPayModeNotifyVo;

@Service("httpDownReqTestServiceImpl")
public class HttpDownReqTestServiceImpl implements IHttpDownReqTestService {
	
	/**
     * 车位预定服务
     */
    @Resource(name = "createOrderHttpServiceImpl")
    private ICreateOrderHttpService createOrderHttpServiceImpl;
    
    /**
     * 取消订单/取消预约服务
     */
    @Resource(name = "cancelOrderHttpServiceImpl")
    private ICancelOrderHttpService cancelOrderHttpServiceImpl;
    
    /**
     * 取消订单/取消预约服务
     */
    @Resource(name = "abnormalCarNoticeHttpServiceImpl")
    private IAbnormalCarNoticeHttpService abnormalCarNoticeHttpServiceImpl;
    
    /**
     * 设置停车场安心点开关及参数(时间、次数等)服务
     */
    @Resource(name = "axdParameterHttpServiceImpl")
    private IAxdParameterHttpService axdParameterHttpServiceImpl;
    
    /**
     * 停车费率查询服务
     */
    @Resource(name = "pakingRateHttpServiceImpl")
    private IPakingRateHttpService pakingRateHttpServiceImpl;
    
    /**
     * 车辆收费方式通知
     */
    @Resource(name = "payModeNotifyHttpServiceImpl")
    private IPayModeNotifyService payModeNotifyHttpServiceImpl;
    
	@Override
	public HttpRequestDealBusinessMsg createOrder(String communityNo,DownCreateOrderVo vo, HttpServletRequest request) {
		HttpRequestDealBusinessMsg msg = null;
		//版本1
		if("1".equals(vo.getVersion().trim())){
			msg = createOrderHttpServiceImpl.processV1(communityNo,vo, request);
		}
		if(msg == null){
			msg = new HttpRequestDealBusinessMsg();
			msg.setStatus(HttpRequestDealBusinessMsg.STATUS_FAILED);
			msg.setMsg("车位预定失败！");
			return msg;
		}
		return msg;
	}

	@Override
	public HttpRequestDealBusinessMsg cancelOrder(String communityNo,DownCancelOrderVo vo, HttpServletRequest request) {
		HttpRequestDealBusinessMsg msg = null;
		//版本1
		if("1".equals(vo.getVersion().trim())){
			msg = cancelOrderHttpServiceImpl.processV1(communityNo,vo, request);
		}
		if(msg == null){
			msg = new HttpRequestDealBusinessMsg();
			msg.setStatus(HttpRequestDealBusinessMsg.STATUS_FAILED);
			msg.setMsg("取消订单/取消预约失败！");
			return msg;
		}
		return msg;
	}

	@Override
	public HttpRequestDealBusinessMsg abnormalCarNotice(String communityNo,DownAbnormalCarNoticeVo vo, HttpServletRequest request) {
		HttpRequestDealBusinessMsg msg = null;
		//版本1
		if("1".equals(vo.getVersion().trim())){
			msg = abnormalCarNoticeHttpServiceImpl.processV1(communityNo,vo, request);
		}
		if(msg == null){
			msg = new HttpRequestDealBusinessMsg();
			msg.setStatus(HttpRequestDealBusinessMsg.STATUS_FAILED);
			msg.setMsg("异常车辆通知失败！");
			return msg;
		}
		return msg;
	}

	@Override
	public HttpRequestDealBusinessMsg axdParameter(String communityNo,DownAxdParameterVo vo, HttpServletRequest request) {
		HttpRequestDealBusinessMsg msg = null;
		//版本1
		if("1".equals(vo.getVersion().trim())){
			msg = axdParameterHttpServiceImpl.processV1(communityNo,vo, request);
		}
		if(msg == null){
			msg = new HttpRequestDealBusinessMsg();
			msg.setStatus(HttpRequestDealBusinessMsg.STATUS_FAILED);
			msg.setMsg("设置停车场安心点开关及参数(时间、次数等)失败！");
			return msg;
		}
		return msg;
	}

	@Override
	public HttpRequestDealBusinessMsg pakingRate(String communityNo,DownPakingRateVo vo, HttpServletRequest request) {
		HttpRequestDealBusinessMsg msg = null;
		//版本1
		if("1".equals(vo.getVersion().trim())){
			msg = pakingRateHttpServiceImpl.processV1(communityNo,vo, request);
		}
		if(msg == null){
			msg = new HttpRequestDealBusinessMsg();
			msg.setStatus(HttpRequestDealBusinessMsg.STATUS_FAILED);
			msg.setMsg("停车费率查询失败！");
			return msg;
		}
		return msg;
	}

	@Override
	public HttpRequestDealBusinessMsg payMode(String communityNo,
			DownPayModeNotifyVo vo, HttpServletRequest request) {
		HttpRequestDealBusinessMsg msg = null;
		//版本1
		if("1".equals(vo.getVersion().trim())){
			msg = payModeNotifyHttpServiceImpl.processV1(communityNo,vo, request);
		}
		if(msg == null){
			msg = new HttpRequestDealBusinessMsg();
			msg.setStatus(HttpRequestDealBusinessMsg.STATUS_FAILED);
			msg.setMsg("车辆收费方式通知失败！");
			return msg;
		}
		return msg;
	}

	
	
}
