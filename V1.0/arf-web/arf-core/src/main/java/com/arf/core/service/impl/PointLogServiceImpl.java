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
import com.arf.core.dao.PointLogDao;
import com.arf.core.entity.Admin;
import com.arf.core.entity.Member;
import com.arf.core.entity.PointLog;
import com.arf.core.service.PointLogService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service - 积分记录
 * 
 * @author arf
 * @version 4.0
 */
@Service("pointLogServiceImpl")
public class PointLogServiceImpl extends BaseServiceImpl<PointLog, Long> implements PointLogService {

	@Resource(name = "pointLogDaoImpl")
	private PointLogDao pointLogDao;

	@Override
	protected BaseDao<PointLog, Long> getBaseDao() {
		return pointLogDao;
	}

	@Transactional(readOnly = true)
	public Page<PointLog> findPage(Member member, Pageable pageable) {
		return pointLogDao.findPage(member, pageable);
	}

	@Override
	public Page<PointLog> findPage(Admin admin, Pageable pageable) {
		return pointLogDao.findPage(admin, pageable);
	}

}