package com.arf.gas.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.gas.dao.IRefuelGasOilConfDao;
import com.arf.gas.entity.RefuelGasOilConf;
import com.arf.gas.entity.RefuelGasOilConf.RetailWholesale;
import com.arf.gas.entity.RefuelGasOilConf.Status;
import com.arf.gas.service.RefuelGasOilConfService;

@Service("refuelGasOilConfServiceImpl")
public class RefuelGasOilConfServiceImpl extends BaseServiceImpl<RefuelGasOilConf, Long> implements RefuelGasOilConfService{

	@Resource(name="refuelGasOilConfDaoImpl")
	IRefuelGasOilConfDao refuelGasOilConfDao;
	
	@Override
	protected BaseDao<RefuelGasOilConf, Long> getBaseDao() {
		return refuelGasOilConfDao;
	}
	
	@Override
	public List<RefuelGasOilConf> findAllOilType(Integer gasNum, Integer businessNum, RetailWholesale retailWholesale,List<Byte> oilTypeList) {
		return refuelGasOilConfDao.findAllOilType(gasNum, businessNum, retailWholesale,oilTypeList);
	}

	@Override
	public int updateStatus() {
		return refuelGasOilConfDao.updateStatus();
	}


}
