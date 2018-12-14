package com.arf.gas.dao;

import java.util.Map;

import com.arf.core.dao.BaseDao;
import com.arf.gas.entity.RefuelGasStation;

public interface IRefuelGasStationDao extends BaseDao<RefuelGasStation, Long>{

	/**
	 * 根据油站编号查询油站信息
	 * @param gasNum
	 * @param businessNum
	 * @return
	 */
	public Map<String, Object> findByGasNum(Integer gasNum, Integer businessNum);
	
	/**
	 * 根据油站编号查询油站信息
	 * @param gasNum
	 * @param businessNum
	 * @return
	 */
	public RefuelGasStation findByGasNumAndBusiness(Integer gasNum, Integer businessNum);
}
