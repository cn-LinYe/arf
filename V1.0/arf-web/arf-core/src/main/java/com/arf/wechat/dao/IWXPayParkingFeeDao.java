package com.arf.wechat.dao;

import java.util.List;

import com.arf.base.PageResult;
import com.arf.core.dao.BaseDao;
import com.arf.wechat.entity.WXPayParkingFee;

public interface IWXPayParkingFeeDao extends BaseDao<WXPayParkingFee, Long> {

	PageResult<WXPayParkingFee> getRecordByOpenId(String openId, Integer pageSize,Integer pageNO);
	
	WXPayParkingFee findByOutTradeNo(String outTradeNo);
	
	void updateBatch(List<Long> recordId,WXPayParkingFee.Status status);
}
