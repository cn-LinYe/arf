package com.arf.access.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.access.dao.IAccessEquipmentOperateLogDao;
import com.arf.access.entity.AccessEquipmentOperateLog;
import com.arf.access.service.IAccessEquipmentOperateLogService;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;

@Service("accessEquipmentOperateLogService")
public class AccessEquipmentOperateLogServiceImpl extends BaseServiceImpl<AccessEquipmentOperateLog, Long> implements IAccessEquipmentOperateLogService{

	@Resource(name="accessEquipmentOperateLogDao")
	IAccessEquipmentOperateLogDao accessEquipmentOperateLogDao;
	
	@Override
	protected BaseDao<AccessEquipmentOperateLog, Long> getBaseDao() {
		return accessEquipmentOperateLogDao;
	}

}
