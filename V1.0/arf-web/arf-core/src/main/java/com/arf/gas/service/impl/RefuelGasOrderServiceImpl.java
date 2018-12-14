package com.arf.gas.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.base.PageResult;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.gas.dao.IRefuelGasOrderDao;
import com.arf.gas.dto.RefuelGasOrderDto;
import com.arf.gas.entity.RefuelGasOrder;
import com.arf.gas.entity.RefuelGasOrder.OrderStatus;
import com.arf.gas.service.IRefuelGasOrderService;

@Service("refuelGasOrderServiceImpl")
public class RefuelGasOrderServiceImpl extends BaseServiceImpl<RefuelGasOrder, Long> implements IRefuelGasOrderService{

	@Resource(name="refuelGasOrderDaoImpl")
	IRefuelGasOrderDao refuelGasOrderDao;
	@Override
	protected BaseDao<RefuelGasOrder, Long> getBaseDao() {
		return refuelGasOrderDao;
	}
	@Override
	public List<RefuelGasOrderDto> findByUserNameAndLicense(String userName ,String license,String gasOrderNo,Integer businessNum,Integer gasNum,Integer orderStatus) {
		return refuelGasOrderDao.findByUserNameAndLicense(userName, license, gasOrderNo, businessNum, gasNum,orderStatus);
	}
	@Override
	public RefuelGasOrder findByOrderNo(String orderNo) {
		return refuelGasOrderDao.findByOrderNo(orderNo);
	}
	@Override
	public PageResult<Map<String, Object>> findByUserNameAndGasNum(String userName, Integer gasNum, Integer businessNum,
			Integer pageSize, Integer pageNo, Date startTime, Date endTime,Integer orderStatus) {
		return refuelGasOrderDao.findByUserNameAndGasNum(userName, gasNum, businessNum, pageSize, pageNo, startTime, endTime,orderStatus);
	}
	@Override
	public Integer findTotalOrderAmountByGasNum(Integer gasNum, Integer businessNum) {
		return refuelGasOrderDao.findTotalOrderAmountByGasNum(gasNum, businessNum);
	}
	@Override
	public List<RefuelGasOrder> findByOrderStatus(OrderStatus orderStatus, Integer gasNum) {
		return refuelGasOrderDao.findByOrderStatus(orderStatus, gasNum);
	}

}
