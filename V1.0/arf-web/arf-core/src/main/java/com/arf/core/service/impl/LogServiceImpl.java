/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;

import javax.annotation.Resource;

import com.arf.core.dao.BaseDao;
import com.arf.core.dao.LogDao;
import com.arf.core.entity.Log;
import com.arf.core.service.LogService;

import org.springframework.stereotype.Service;

/**
 * Service - 日志
 * 
 * @author arf
 * @version 4.0
 */
@Service("logServiceImpl")
public class LogServiceImpl extends BaseServiceImpl<Log, Long> implements LogService {

	@Resource(name = "logDaoImpl")
	private LogDao logDao;

	@Override
	protected BaseDao<Log, Long> getBaseDao() {
		return logDao;
	}

	public void clear() {
		logDao.removeAll();
	}

}