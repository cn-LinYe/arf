package com.arf.gas.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.base.PageResult;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.gas.dao.IRefuelGasRechargeDao;
import com.arf.gas.entity.RefuelGasRecharge;
import com.arf.gas.service.IRefuelGasRechargeService;

@Service("refuelGasRechargeService")
public class RefuelGasRechargeServiceImpl extends BaseServiceImpl<RefuelGasRecharge, Long> implements IRefuelGasRechargeService{

	@Resource(name="refuelGasRechargeDao")
	IRefuelGasRechargeDao refuelGasRechargeDao;
	
	@Override
	protected BaseDao<RefuelGasRecharge, Long> getBaseDao() {
		return refuelGasRechargeDao;
	}

	@Override
	public RefuelGasRecharge findByOrderNo(String orderNo) {
		return refuelGasRechargeDao.findByOrderNo(orderNo);
	}

	@Override
	public PageResult<Map<String, Object>> findRechargeByUserNameAndGasNum(String userName, Integer gasNum,
			Integer businessNum, Integer pageSize, Integer pageNo, Date startTime, Date endTime, Integer orderStatus) {
		return refuelGasRechargeDao.findRechargeByUserNameAndGasNum(userName, gasNum, businessNum, pageSize, pageNo
				, startTime, endTime, orderStatus);
	}

	@Override
	public List<RefuelGasRecharge> findByCardNumber(List<String> cardNumbers) {
		return refuelGasRechargeDao.findByCardNumber(cardNumbers);
	}

}
