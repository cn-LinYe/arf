package com.arf.gas.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.gas.dao.IRefuelGasRechargeConfDao;
import com.arf.gas.entity.RefuelGasRechargeConf;
import com.arf.gas.entity.RefuelGasRechargeConf.Status;
import com.arf.gas.service.IRefuelGasRechargeConfService;
@Service("refuelGasRechargeConfServiceImpl")
public class RefuelGasRechargeConfServiceImpl extends BaseServiceImpl<RefuelGasRechargeConf, Long>implements IRefuelGasRechargeConfService{

	@Resource(name="refuelGasRechargeConfDaoImpl")
	IRefuelGasRechargeConfDao refuelGasRechargeConfDao;
	@Override
	protected BaseDao<RefuelGasRechargeConf, Long> getBaseDao() {
		return refuelGasRechargeConfDao;
	}
	@Override
	public List<RefuelGasRechargeConf> findByGasNum(Integer gasNum, Status status) {
		return refuelGasRechargeConfDao.findByGasNum(gasNum, status);
	}

}
