package com.arf.access.dao.impl;

import org.springframework.stereotype.Repository;

import com.arf.access.dao.IAccessPhotoUnlockLogDao;
import com.arf.access.entity.AccessPhotoUnlockLog;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("accessPhotoUnlockLogDao")
public class AccessPhotoUnlockLogDaoImpl extends BaseDaoImpl<AccessPhotoUnlockLog, Long> implements IAccessPhotoUnlockLogDao{

}
