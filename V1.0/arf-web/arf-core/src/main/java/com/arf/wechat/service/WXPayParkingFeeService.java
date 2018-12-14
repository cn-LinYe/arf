package com.arf.wechat.service;

import java.util.List;

import com.arf.base.PageResult;
import com.arf.core.service.BaseService;
import com.arf.wechat.entity.WXPayParkingFee;

public interface WXPayParkingFeeService extends BaseService<WXPayParkingFee, Long> {

	PageResult<WXPayParkingFee> getRecordByOpenId(String openId, Integer pageSize,Integer pageNO);
	
	WXPayParkingFee findByOutTradeNo(String outTradeNo);
	
	void updateBatch(List<Long> recordId,WXPayParkingFee.Status status);
}
