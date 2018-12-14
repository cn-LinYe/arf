package com.arf.gas.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.gas.dao.IRefuelGasTubingGunDao;
import com.arf.gas.entity.RefuelGasTubingGun;
import com.arf.gas.service.IRefuelGasTubingGunService;

@Service("refuelGasTubingGunServiceImpl")
public class RefuelGasTubingGunServiceImpl extends BaseServiceImpl<RefuelGasTubingGun, Long> implements IRefuelGasTubingGunService{

	@Resource(name="refuelGasTubingGunDaoImpl")
	IRefuelGasTubingGunDao refuelGasTubingGunDao;
	
	@Override
	protected BaseDao<RefuelGasTubingGun, Long> getBaseDao() {
		return refuelGasTubingGunDao;
	}
	
	@Override
	public List<RefuelGasTubingGun> findByGasNum(Integer gasNum, Integer businessNum,Integer tubingNum) {
		return refuelGasTubingGunDao.findByGasNum(gasNum, businessNum,tubingNum);
	}

}
