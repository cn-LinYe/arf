package com.arf.laundry.service;

import com.arf.core.service.BaseService;
import com.arf.laundry.entity.LaundryOrderRefund;

public interface ILaundryOrderRefundService extends BaseService<LaundryOrderRefund, Long> {

	/**
	 * 通过订单编号查询
	 * @param outTradeNo
	 * @return
	 */
	LaundryOrderRefund findByOutTradeNo(String outTradeNo);
	
}
