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
import com.arf.core.dao.DepositLogDao;
import com.arf.core.entity.DepositLog;
import com.arf.core.entity.Member;
import com.arf.core.service.DepositLogService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service - 预存款记录
 * 
 * @author arf
 * @version 4.0
 */
@Service("depositLogServiceImpl")
public class DepositLogServiceImpl extends BaseServiceImpl<DepositLog, Long> implements DepositLogService {

	@Resource(name = "depositLogDaoImpl")
	private DepositLogDao depositLogDao;

	@Override
	protected BaseDao<DepositLog, Long> getBaseDao() {
		return depositLogDao;
	}

	@Transactional(readOnly = true)
	public Page<DepositLog> findPage(Member member, Pageable pageable) {
		return depositLogDao.findPage(member, pageable);
	}

}