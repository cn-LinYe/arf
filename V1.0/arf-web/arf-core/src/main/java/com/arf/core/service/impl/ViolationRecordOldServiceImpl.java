/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;


import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import com.arf.core.dao.ViolationRecordOldDao;
import com.arf.core.dao.BaseDao;
import com.arf.core.entity.ViolationRecordOld;
import com.arf.core.service.ViolationRecordOldService;


/**
 * Service - 旧违章记录表
 * 
 * @author arf
 * @version 4.0
 */
@Service("violationRecordOldServiceImpl")
public class ViolationRecordOldServiceImpl extends BaseServiceImpl<ViolationRecordOld, Long> implements ViolationRecordOldService {

	@Resource(name = "violationRecordOldDaoImpl")
	private ViolationRecordOldDao violationRecordOldDao;

	@Override
	protected BaseDao<ViolationRecordOld, Long> getBaseDao() {
		return violationRecordOldDao;
	}

}