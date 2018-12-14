package com.arf.access.dao.impl;

import org.springframework.stereotype.Repository;

import com.arf.access.dao.IAccessAppUnlockLogDao;
import com.arf.access.entity.AccessAppUnlockLog;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("accessAppUnlockLogDao")
public class AccessAppUnlockLogDaoImpl extends BaseDaoImpl<AccessAppUnlockLog, Long>
implements IAccessAppUnlockLogDao {

}
