package com.arf.laundry.dao;

import java.util.Map;

import com.arf.base.PageResult;
import com.arf.core.dao.BaseDao;
import com.arf.laundry.dto.MerchantOrderDto;
import com.arf.laundry.entity.LaundryOrder;
import com.arf.laundry.search.LaundryOrderCondition;

public interface ILaundryOrderDao extends BaseDao<LaundryOrder, Long> {

	/**
	 * 根据订单号查询订单信息
	 * @param outTradeNo
	 * @return
	 */
	LaundryOrder findByOutTradeNo(String outTradeNo);
	
	/**
	 * 根据订单号查询商户订单所需信息
	 * @param outTradeNo
	 * @return
	 */
	MerchantOrderDto findMerchantOrderByOutTradeNo(String outTradeNo);
	
	/**
	 * 根据条件分页查询商户所需订单信息
	 * @param condition
	 * @return
	 */
	PageResult<Map<String,Object>> findListByCondition(LaundryOrderCondition condition);
	
	/**
	 * 对无退款申请的订单，在delayDays天后未签收则进行自动签收
	 * @param delayDays
	 */
	void autoReceivedOrder(Integer delayDays);
}
