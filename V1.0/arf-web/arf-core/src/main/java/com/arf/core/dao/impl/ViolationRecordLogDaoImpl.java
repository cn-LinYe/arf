package com.arf.core.dao.impl;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.ViolationRecordLogDao;
import com.arf.core.entity.ViolationRecordLog;

/**
 * Dao - 违章查询记录
 * 
 * @author arf
 * @version 4.0
 */
@Repository("violationRecordLogDaoImpl")
public class ViolationRecordLogDaoImpl extends BaseDaoImpl<ViolationRecordLog, Long> implements ViolationRecordLogDao {
	
}
