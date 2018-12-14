package com.arf.gas.service;

import java.util.Map;

import com.arf.core.service.BaseService;
import com.arf.gas.entity.RefuelGasStation;

public interface IRefuelGasStationService extends BaseService<RefuelGasStation, Long>{

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
