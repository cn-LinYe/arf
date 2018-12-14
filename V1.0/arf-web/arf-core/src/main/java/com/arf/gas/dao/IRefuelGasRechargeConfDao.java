package com.arf.gas.dao;

import java.util.List;

import com.arf.core.dao.BaseDao;
import com.arf.gas.entity.RefuelGasRechargeConf;

public interface IRefuelGasRechargeConfDao extends BaseDao<RefuelGasRechargeConf, Long>{
	/**
	 * 根据油站编号及状态查询
	 * @param gasNum
	 * @param status
	 */
	List<RefuelGasRechargeConf> findByGasNum(Integer gasNum,RefuelGasRechargeConf.Status status);
}
