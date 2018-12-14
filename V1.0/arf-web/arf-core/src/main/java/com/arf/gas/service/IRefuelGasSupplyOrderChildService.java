package com.arf.gas.service;

import java.util.List;

import com.arf.core.service.BaseService;
import com.arf.gas.entity.RefuelGasSupplyOrderChild;

public interface IRefuelGasSupplyOrderChildService extends BaseService<RefuelGasSupplyOrderChild, Long>{

	/**
	 * 根据订单编号查询
	 * @param orderNo
	 * @return
	 */
	List<RefuelGasSupplyOrderChild> findByOrderNo(String orderNo);
}
