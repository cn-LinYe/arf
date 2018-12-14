package com.arf.gas.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.base.PageResult;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.gas.dao.IRefuelGasCardOrderDao;
import com.arf.gas.entity.RefuelGasCardOrder;
import com.arf.gas.service.IRefuelGasCardOrderService;

@Service("refuelGasCardOrderServiceImpl")
public class RefuelGasCardOrderServiceImpl extends BaseServiceImpl<RefuelGasCardOrder,Long> implements IRefuelGasCardOrderService{

	@Resource(name="refuelGasCardOrderDaoImpl")
	IRefuelGasCardOrderDao refuelGasCardOrderDao;
	
	@Override
	protected BaseDao<RefuelGasCardOrder, Long> getBaseDao() {
		return refuelGasCardOrderDao;
	}

	@Override
	public RefuelGasCardOrder findByOrderNo(String orderNo) {
		return refuelGasCardOrderDao.findByOrderNo(orderNo);
	}

	@Override
	public RefuelGasCardOrder findByCarNumBusiness(Integer gunNum, String userName) {
		return refuelGasCardOrderDao.findByCarNumBusiness(gunNum, userName);
	}

	@Override
	public PageResult<Map<String,Object>> findByCondition(Integer gasNum,Integer audit,String orderNo, Integer orderStatus, String memberUserName,
			String startTime, String endTime, Integer pageNo, Integer pageSize) {
		return refuelGasCardOrderDao.findByCondition(gasNum,audit,orderNo, orderStatus, memberUserName, startTime, endTime, pageNo, pageSize);
	}
	

	@Override
	public Map<String, Object> statisticsCardAmountAndNumber(String startTime, String endTime, Integer gasNum) {
		return refuelGasCardOrderDao.statisticsCardAmountAndNumber(startTime, endTime,gasNum);
	}


	@Override
	public Map<String, Object> statisticsRefulesMember(String startTime, String endTime, Integer gasNum) {
		return refuelGasCardOrderDao.statisticsRefulesMember(startTime, endTime, gasNum);
	}

	@Override
	public PageResult<RefuelGasCardOrder> findByCondition(Integer gasNum,String orderNo, Integer orderStatus, String memberUserName,
			String startTime, String endTime, Integer pageNo, Integer pageSize) {
		return refuelGasCardOrderDao.findByCondition(gasNum,orderNo, orderStatus, memberUserName, startTime, endTime, pageNo, pageSize);
	}


}
