package com.arf.gas.service;

import java.util.List;

import com.arf.core.service.BaseService;
import com.arf.gas.entity.RefuelGasRewardCardConf;
import com.arf.gas.entity.RefuelGasRewardCardConf.PolicyType;

public interface IRefuelGasRewardCardConfService extends BaseService<RefuelGasRewardCardConf, Long>{

	/**
	 * 根据油站编号，奖励类型查询
	 * @param gasNum
	 * @param businessNum
	 * @param policyType
	 * @return
	 */
	public List<RefuelGasRewardCardConf> findByGasNumAndPolicyType(Integer gasNum,Integer businessNum,PolicyType policyType);
}
