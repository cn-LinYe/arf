package com.arf.gas.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.gas.dao.IRefuelGasRewardCardConfDao;
import com.arf.gas.entity.RefuelGasRewardCardConf;
import com.arf.gas.entity.RefuelGasRewardCardConf.PolicyType;
import com.arf.gas.service.IRefuelGasRewardCardConfService;

@Service("refuelGasRewardCardConfService")
public class RefuelGasRewardCardConfServiceImpl extends BaseServiceImpl<RefuelGasRewardCardConf, Long> implements IRefuelGasRewardCardConfService{

	@Resource(name="refuelGasRewardCardConfDao")
	IRefuelGasRewardCardConfDao refuelGasRewardCardConfDao;
	
	@Override
	protected BaseDao<RefuelGasRewardCardConf, Long> getBaseDao() {
		return refuelGasRewardCardConfDao;
	}
	
	@Override
	public List<RefuelGasRewardCardConf> findByGasNumAndPolicyType(Integer gasNum, Integer businessNum,
			PolicyType policyType) {
		return refuelGasRewardCardConfDao.findByGasNumAndPolicyType(gasNum, businessNum, policyType);
	}

}
