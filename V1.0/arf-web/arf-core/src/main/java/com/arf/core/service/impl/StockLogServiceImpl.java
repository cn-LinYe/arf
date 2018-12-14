/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;

import javax.annotation.Resource;

import com.arf.core.Page;
import com.arf.core.Pageable;
import com.arf.core.dao.BaseDao;
import com.arf.core.dao.StockLogDao;
import com.arf.core.entity.Admin;
import com.arf.core.entity.StockLog;
import com.arf.core.service.StockLogService;

import org.springframework.stereotype.Service;

/**
 * Service - 库存记录
 * 
 * @author arf
 * @version 4.0
 */
@Service("stockLogServiceImpl")
public class StockLogServiceImpl extends BaseServiceImpl<StockLog, Long> implements StockLogService {

	@Resource(name = "stockLogDaoImpl")
	private StockLogDao stockLogDao;

	@Override
	protected BaseDao<StockLog, Long> getBaseDao() {
		return stockLogDao;
	}

	@Override
	public Page<StockLog> findPage(Admin admin, Pageable pageable) {
		return stockLogDao.findPage(admin, pageable);
	}

}