package com.arf.gas.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.arf.base.PageResult;
import com.arf.core.dao.BaseDao;
import com.arf.gas.dto.RefuelGasOrderDto;
import com.arf.gas.entity.RefuelGasOrder;
import com.arf.gas.entity.RefuelGasOrder.OrderStatus;

public interface IRefuelGasOrderDao extends BaseDao<RefuelGasOrder, Long>{
	/**
	 * 根据用户名车牌查询
	 * @param userName
	 * @param license
	 * @param gasOrderNo
	 * @param businessNum
	 * @param gasNum
	 * @param orderStatus
	 * @return
	 */
	List<RefuelGasOrderDto> findByUserNameAndLicense(String userName ,String license,String gasOrderNo,Integer businessNum,Integer gasNum,Integer orderStatus);
	/**
	 * 根据订单编号查找
	 * @param orderNo
	 * @return
	 */
	RefuelGasOrder findByOrderNo(String orderNo);
	
	/**
	 * 根据用户名，加油站，时间查询会员加油订单信息列表（可供加油站或会员查询）
	 * @param userName
	 * @param gasNum
	 * @param businessNum
	 * @param pageSize
	 * @param pageNo
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public PageResult<Map<String, Object>> findByUserNameAndGasNum(String userName,Integer gasNum,Integer businessNum,
			Integer pageSize,Integer pageNo,Date startTime,Date endTime,Integer orderStatus);
	
	/**
	 * 获取加油站总的加油金额
	 * @param gasNum
	 * @param businessNum
	 * @return
	 */
	Integer findTotalOrderAmountByGasNum(Integer gasNum,Integer businessNum);
	
	/**
	 * 根据订单状态查找
	 * @param orderNo
	 * @return
	 */
	List<RefuelGasOrder> findByOrderStatus(RefuelGasOrder.OrderStatus orderStatus,Integer gasNum);
	
}
