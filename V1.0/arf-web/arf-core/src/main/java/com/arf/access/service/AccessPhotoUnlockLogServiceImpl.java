package com.arf.access.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.access.dao.IAccessPhotoUnlockLogDao;
import com.arf.access.entity.AccessPhotoUnlockLog;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;

@Service("accessPhotoUnlockLogService")
public class AccessPhotoUnlockLogServiceImpl extends BaseServiceImpl<AccessPhotoUnlockLog, Long> implements IAccessPhotoUnlockLogService{

	@Resource(name="accessPhotoUnlockLogDao")
	IAccessPhotoUnlockLogDao accessPhotoUnlockLogDao;
	
	@Override
	protected BaseDao<AccessPhotoUnlockLog, Long> getBaseDao() {
		return accessPhotoUnlockLogDao;
	}

}
