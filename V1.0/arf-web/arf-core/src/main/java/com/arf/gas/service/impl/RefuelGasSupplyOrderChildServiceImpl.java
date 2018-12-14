package com.arf.gas.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.gas.dao.IRefuelGasSupplyOrderChildDao;
import com.arf.gas.entity.RefuelGasSupplyOrderChild;
import com.arf.gas.service.IRefuelGasSupplyOrderChildService;

@Service("refuelGasSupplyOrderChildServiceImpl")
public class RefuelGasSupplyOrderChildServiceImpl extends BaseServiceImpl<RefuelGasSupplyOrderChild, Long> implements IRefuelGasSupplyOrderChildService{

	@Resource(name="refuelGasSupplyOrderChildDaoServiceImpl")
	IRefuelGasSupplyOrderChildDao refuelGasSupplyOrderChildDao;
	
	@Override
	protected BaseDao<RefuelGasSupplyOrderChild, Long> getBaseDao() {
		return refuelGasSupplyOrderChildDao;
	}

	@Override
	public List<RefuelGasSupplyOrderChild> findByOrderNo(String orderNo) {
		return refuelGasSupplyOrderChildDao.findByOrderNo(orderNo);
	}
	
}
