package com.arf.violation.service;

import java.util.Map;

import com.arf.base.PageResult;
import java.util.Date;
import java.util.List;

import com.arf.core.service.BaseService;
import com.arf.violation.entity.TrafficViolationOrder;

public interface ITrafficViolationOrderService extends BaseService<TrafficViolationOrder, Long>{

	/**
	 * 根据orderNo查询订单
	 * @param orderNo
	 * @return
	 */
	public TrafficViolationOrder findByOrderNo(String orderNo);
	/**
	 * 根据additionServiceNo查询订单
	 * @param additionServiceNo
	 * @return
	 */
	public TrafficViolationOrder findByAdditionServiceNo(String additionServiceNo);
	
	/**
	 * 根据条件查询我的订单
	 * @param outTradeNo
	 * @param userName
	 * @param pageSize
	 * @param pageNo
	 * @param status
	 * @return
	 */
	public PageResult<Map<String,Object>> findByCondition(String outTradeNo,String userName,Integer pageSize,Integer pageNo,TrafficViolationOrder.Status status,String businessNum,String startTime,String endTime);
	/**
	 * 根据Status查询当天订单数量
	 * @param Status
	 * @return
	 */
	public Integer findByStatus(Integer Status,String businessNum);
	/**
	 * 查询当天订单
	 * @param modifyDate
	 * @return
	 */
	public List<TrafficViolationOrder> findByModifyDate(Date todayStartDate,Date todayEndDate,String businessNum);
}
