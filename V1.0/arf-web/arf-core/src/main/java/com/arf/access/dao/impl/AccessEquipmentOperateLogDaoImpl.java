package com.arf.access.dao.impl;

import org.springframework.stereotype.Repository;

import com.arf.access.dao.IAccessEquipmentOperateLogDao;
import com.arf.access.entity.AccessEquipmentOperateLog;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("accessEquipmentOperateLogDao")
public class AccessEquipmentOperateLogDaoImpl extends BaseDaoImpl<AccessEquipmentOperateLog, Long> implements IAccessEquipmentOperateLogDao{

}
