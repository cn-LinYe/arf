package com.arf.laundry.dao;

import com.arf.core.dao.BaseDao;
import com.arf.laundry.entity.LaundryOrderRefund;

public interface ILaundryOrderRefundDao extends BaseDao<LaundryOrderRefund, Long> {

	/**
	 * 通过订单编号查询
	 * @param outTradeNo
	 * @return
	 */
	LaundryOrderRefund findByOutTradeNo(String outTradeNo);

}
