package com.arf.gas.service;

import java.util.List;

import com.arf.core.service.BaseService;
import com.arf.gas.entity.RefuelGasRechargeConf;

public interface IRefuelGasRechargeConfService extends BaseService<RefuelGasRechargeConf, Long>{

	/**
	 * 根据油站编号及状态查询
	 * @param gasNum
	 * @param status
	 */
	List<RefuelGasRechargeConf> findByGasNum(Integer gasNum,RefuelGasRechargeConf.Status status);
}
