package com.arf.smart.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.smart.dao.ISmartLockDao;
import com.arf.smart.entity.Smartlock;
import com.arf.smart.service.ISmartLockService;

@Repository("smartLockServiceImpl")
public class SmartLockServiceImpl extends BaseServiceImpl<Smartlock, Long> implements ISmartLockService{

	@Resource(name = "smartLockDaoImpl")
	private ISmartLockDao smartLockDaoImpl;
	
	@Override
	protected BaseDao<Smartlock, Long> getBaseDao() {
		return smartLockDaoImpl;
	}

	@Override
	public List<Map<String, Object>> getLocksByUser(String userName) {		
		return smartLockDaoImpl.getLocksByUser(userName);
	}

}
