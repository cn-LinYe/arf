package com.arf.access.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.access.dao.IAccessAppUnlockLogDao;
import com.arf.access.entity.AccessAppUnlockLog;
import com.arf.access.service.IAccessAppUnlockLogService;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;

@Service("accessAppUnlockLogService")
public class AccessAppUnlockLogServiceImpl extends BaseServiceImpl<AccessAppUnlockLog, Long>
implements IAccessAppUnlockLogService {
	
	@Resource(name = "accessAppUnlockLogDao")
	private IAccessAppUnlockLogDao accessAppUnlockLogDao;
	
	@Override
	protected BaseDao<AccessAppUnlockLog, Long> getBaseDao() {
		return accessAppUnlockLogDao;
	}
	
}
