package com.arf.violation.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.arf.base.PageResult;
import com.arf.core.dao.BaseDao;
import com.arf.violation.entity.TrafficViolationOrder;

public interface ITrafficViolationOrderDao extends BaseDao<TrafficViolationOrder, Long>{

	
	/**
	 * 根据additionServiceNo查询订单
	 * @param additionServiceNo
	 * @return
	 */
	public TrafficViolationOrder findByAdditionServiceNo(String additionServiceNo);
	/**
	 * 根据orderNo查询订单
	 * @param orderNo
	 * @return
	 */
	public TrafficViolationOrder findByOrderNo(String orderNo);
	

	/**
	 * 查询当天订单
	 * @param modifyDate
	 * @return
	 */
	public List<TrafficViolationOrder> findByModifyDate(Date todayStartDate,Date todayEndDate,String businessNum);
	/**
	 * 根据Status查询订单数量
	 * @param Status
	 * @return
	 */
	public Integer findByStatus(Integer Status,String businessNum);
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
}
