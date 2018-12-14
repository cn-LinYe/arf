package com.arf.access.dao.impl;

import org.springframework.stereotype.Repository;

import com.arf.access.dao.IAccessCardUnlockLogDao;
import com.arf.access.entity.AccessCardUnlockLog;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("accessCardUnlockLogDaoImpl")
public class AccessCardUnlockLogDaoImpl extends
		BaseDaoImpl<AccessCardUnlockLog, Long> implements
		IAccessCardUnlockLogDao {

	
	
}
