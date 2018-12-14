package com.arf.gas.dao;

import java.util.List;

import com.arf.core.dao.BaseDao;
import com.arf.gas.entity.RefuelGasSupplyOrderChild;

public interface IRefuelGasSupplyOrderChildDao  extends BaseDao<RefuelGasSupplyOrderChild, Long>{
	/**
	 * 根据订单编号查询
	 * @param orderNo
	 * @return
	 */
	List<RefuelGasSupplyOrderChild> findByOrderNo(String orderNo);
}
