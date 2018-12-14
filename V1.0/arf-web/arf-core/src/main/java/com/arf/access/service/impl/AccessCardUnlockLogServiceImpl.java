package com.arf.access.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.access.dao.IAccessCardUnlockLogDao;
import com.arf.access.entity.AccessCardUnlockLog;
import com.arf.access.service.IAccessCardUnlockLogService;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;

@Service("accessCardUnlockLogServiceImpl")
public class AccessCardUnlockLogServiceImpl extends
		BaseServiceImpl<AccessCardUnlockLog, Long> implements
		IAccessCardUnlockLogService {

	@Resource(name = "accessCardUnlockLogDaoImpl")
	private IAccessCardUnlockLogDao accessCardUnlockLogDaoImpl;
	
	@Override
	protected BaseDao<AccessCardUnlockLog, Long> getBaseDao() {
		return accessCardUnlockLogDaoImpl;
	}

	
	
}
