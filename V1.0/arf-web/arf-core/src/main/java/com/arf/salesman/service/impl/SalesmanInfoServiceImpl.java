package com.arf.salesman.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.salesman.dao.ISalesmanInfoDao;
import com.arf.salesman.entity.SalesmanInfo;
import com.arf.salesman.service.ISalesmanInfoService;
@Service("salesmanInfoServiceImpl")
public class SalesmanInfoServiceImpl extends BaseServiceImpl<SalesmanInfo, Long> implements ISalesmanInfoService{

	@Resource(name="salesmanInfoDaoImpl")
	ISalesmanInfoDao salesmanInfoDaoImpl;

	@Override
	protected BaseDao<SalesmanInfo, Long> getBaseDao() {
		return salesmanInfoDaoImpl;
	}

	@Override
	public SalesmanInfo findByUserName(String userName) {
		return salesmanInfoDaoImpl.findByUserName(userName);
	}

	
}
