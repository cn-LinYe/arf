package com.arf.gas.dao;

import java.util.List;

import com.arf.core.dao.BaseDao;
import com.arf.gas.entity.RefuelGasTubingGun;

public interface IRefuelGasTubingGunDao extends BaseDao<RefuelGasTubingGun, Long>{

	/**
	 * 查询商户的所有油箱
	 * @param gasNum
	 * @param businessNum
	 * @return
	 */
	public List<RefuelGasTubingGun> findByGasNum(Integer gasNum, Integer businessNum,Integer tubingNum);
}
