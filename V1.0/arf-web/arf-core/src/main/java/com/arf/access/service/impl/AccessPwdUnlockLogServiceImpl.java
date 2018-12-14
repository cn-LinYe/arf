package com.arf.access.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.access.dao.IAccessPwdUnlockLogDao;
import com.arf.access.entity.AccessPwdUnlockLog;
import com.arf.access.service.IAccessPwdUnlockLogService;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;

@Service("accessPwdUnlockLogService")
public class AccessPwdUnlockLogServiceImpl extends BaseServiceImpl<AccessPwdUnlockLog, Long>
		implements IAccessPwdUnlockLogService {

	@Resource(name = "accessPwdUnlockLogDao")
	IAccessPwdUnlockLogDao accessPwdUnlockLogDao;
	
	@Override
	protected BaseDao<AccessPwdUnlockLog, Long> getBaseDao() {
		return accessPwdUnlockLogDao;
	}

	
}
