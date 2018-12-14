package com.arf.gas.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.gas.dao.IRefuelGasStationDao;
import com.arf.gas.entity.RefuelGasStation;
import com.arf.gas.service.IRefuelGasStationService;

@Service("refuelGasStationServiceImpl")
public class RefuelGasStationServiceImpl extends BaseServiceImpl<RefuelGasStation, Long> implements IRefuelGasStationService{

	@Resource(name="refuelGasStationDaoImpl")
	IRefuelGasStationDao refuelGasStationDao;
	
	@Override
	protected BaseDao<RefuelGasStation, Long> getBaseDao() {
		return refuelGasStationDao;
	}

	@Override
	public Map<String, Object> findByGasNum(Integer gasNum, Integer businessNum) {
		return refuelGasStationDao.findByGasNum(gasNum, businessNum);
	}

	@Override
	public RefuelGasStation findByGasNumAndBusiness(Integer gasNum, Integer businessNum) {
		return refuelGasStationDao.findByGasNumAndBusiness(gasNum, businessNum);
	}

}
