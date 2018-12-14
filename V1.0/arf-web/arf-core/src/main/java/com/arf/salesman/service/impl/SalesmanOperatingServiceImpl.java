package com.arf.salesman.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.salesman.dao.ISalesmanOperatingDao;
import com.arf.salesman.entity.SalemanOperating;
import com.arf.salesman.service.ISalesmanOperatingService;
@Service("salesmanOperatingServiceImpl")
public class SalesmanOperatingServiceImpl extends BaseServiceImpl<SalemanOperating, Long> implements ISalesmanOperatingService{

	@Resource(name="salesmanOperatingDaoImpl")
	ISalesmanOperatingDao salesmanOperatingDao;

	@Override
	protected BaseDao<SalemanOperating, Long> getBaseDao() {
		return salesmanOperatingDao;
	}

	@Override
	public SalemanOperating findByCommunity(String comuunityNum) {
		return salesmanOperatingDao.findByCommunity(comuunityNum);
	}
	
}
