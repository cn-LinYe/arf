/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;


import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import com.arf.core.dao.ViolationRecordLogDao;
import com.arf.core.dao.BaseDao;
import com.arf.core.entity.ViolationRecordLog;
import com.arf.core.service.ViolationRecordLogService;


/**
 * Service - 违章查询记录
 * 
 * @author arf
 * @version 4.0
 */
@Service("violationRecordLogServiceImpl")
public class ViolationRecordLogServiceImpl extends BaseServiceImpl<ViolationRecordLog, Long> implements ViolationRecordLogService {

	@Resource(name = "violationRecordLogDaoImpl")
	private ViolationRecordLogDao violationRecordLogDao;

	@Override
	protected BaseDao<ViolationRecordLog, Long> getBaseDao() {
		return violationRecordLogDao;
	}

}