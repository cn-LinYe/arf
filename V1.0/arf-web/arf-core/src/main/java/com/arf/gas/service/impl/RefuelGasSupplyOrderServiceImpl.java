package com.arf.gas.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.base.PageResult;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.gas.dao.IRefuelGasSupplyOrderDao;
import com.arf.gas.entity.RefuelGasSupplyOrder;
import com.arf.gas.entity.RefuelGasSupplyOrder.OrderStatus;
import com.arf.gas.service.IRefuelGasSupplyOrderService;

@Service("refuelGasSupplyOrderServiceImpl")
public class RefuelGasSupplyOrderServiceImpl extends BaseServiceImpl<RefuelGasSupplyOrder, Long> implements IRefuelGasSupplyOrderService{

	@Resource(name="refuelGasSupplyOrderDaoImpl")
	IRefuelGasSupplyOrderDao refuelGasSupplyOrderDao;
	
	@Override
	protected BaseDao<RefuelGasSupplyOrder, Long> getBaseDao() {
		return refuelGasSupplyOrderDao;
	}

	@Override
	public List<RefuelGasSupplyOrder> findByGasNum(Integer gasNum, Integer businessNum, List<RefuelGasSupplyOrder.OrderStatus> orderStatus, Date startTime, Date endTime) {
		return refuelGasSupplyOrderDao.findByGasNum(gasNum, businessNum, orderStatus,startTime, endTime);
	}

	@Override
	public PageResult<RefuelGasSupplyOrder> findByCondition(Integer gasNum, Integer businessNum,
			RefuelGasSupplyOrder.OrderStatus orderStatus, String startTime, String endTime, Integer pageSize, Integer pageNo) {
		return refuelGasSupplyOrderDao.findByCondition(gasNum, businessNum, orderStatus, startTime, endTime, pageSize, pageNo);
	}

	@Override
	public RefuelGasSupplyOrder findByOrderNo(String orderNo) {
		return refuelGasSupplyOrderDao.findByOrderNo(orderNo);
	}

}
