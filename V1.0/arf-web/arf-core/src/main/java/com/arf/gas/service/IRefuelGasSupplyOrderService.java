package com.arf.gas.service;

import java.util.Date;
import java.util.List;

import com.arf.base.PageResult;
import com.arf.core.service.BaseService;
import com.arf.gas.entity.RefuelGasSupplyOrder;

public interface IRefuelGasSupplyOrderService extends BaseService<RefuelGasSupplyOrder, Long>{

	/**
	 * 根据油站编号，订单状态查询补油订单
	 * @param gasNum
	 * @param businessNum
	 * @param orderStatus
	 * @return
	 */
	public List<RefuelGasSupplyOrder> findByGasNum(Integer gasNum, Integer businessNum,List<RefuelGasSupplyOrder.OrderStatus> orderStatus
			, Date startTime, Date endTime);
	
	/**
	 * 按条件查找补油订单信息
	 * @param gasNum
	 * @param businessNum
	 * @param orderStatus
	 * @param startTime
	 * @param endTime
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	public PageResult<RefuelGasSupplyOrder> findByCondition(Integer gasNum, Integer businessNum,RefuelGasSupplyOrder.OrderStatus orderStatus
			, String startTime, String endTime,Integer pageSize,Integer pageNo);
	
	/**
	 * 根据订单编号查询
	 * @param orderNo
	 * @return
	 */
	RefuelGasSupplyOrder findByOrderNo(String orderNo);
}
