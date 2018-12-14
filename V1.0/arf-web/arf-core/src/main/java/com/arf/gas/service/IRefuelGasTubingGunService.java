package com.arf.gas.service;

import java.util.List;

import com.arf.core.service.BaseService;
import com.arf.gas.entity.RefuelGasTubingGun;

public interface IRefuelGasTubingGunService extends BaseService<RefuelGasTubingGun, Long>{

	/**
	 * 查询商户的所有油箱
	 * @param gasNum
	 * @param businessNum
	 * @return
	 */
	public List<RefuelGasTubingGun> findByGasNum(Integer gasNum, Integer businessNum,Integer tubingNum);
}
