package com.arf.access.dao.impl;

import org.springframework.stereotype.Repository;

import com.arf.access.dao.IAccessPwdUnlockLogDao;
import com.arf.access.entity.AccessPwdUnlockLog;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("accessPwdUnlockLogDao")
public class AccessPwdUnlockLogDaoImpl extends BaseDaoImpl<AccessPwdUnlockLog, Long> implements IAccessPwdUnlockLogDao {

}
