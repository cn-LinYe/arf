package com.arf.laundry.service;

import java.util.Map;

import com.arf.base.PageResult;
import com.arf.core.service.BaseService;
import com.arf.laundry.dto.MerchantOrderDto;
import com.arf.laundry.entity.LaundryOrder;
import com.arf.laundry.search.LaundryOrderCondition;

public interface ILaundryOrderService extends BaseService<LaundryOrder, Long> {

	String SYS_CONFIG_PARENT = "LAUNDRY";
	
	/**
	 * 根据订单号查询订单
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
	 * 根据条件分页查询商户订单所需信息
	 * @param condition
	 * @return
	 */
	PageResult<Map<String,Object>> findListByCondition(LaundryOrderCondition condition);
	
	/**
	 * 洗衣订单超过delayDays天且无投诉用户没确认签收，则自动签收
	 * @param delayDays
	 */
	void autoReceivedOrder(Integer delayDays);

}
