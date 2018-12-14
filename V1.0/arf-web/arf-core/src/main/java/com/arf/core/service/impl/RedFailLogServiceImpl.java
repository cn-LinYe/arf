/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;


import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import com.arf.core.dao.RedFailLogDao;
import com.arf.core.dao.BaseDao;
import com.arf.core.entity.RedFailLog;
import com.arf.core.service.RedFailLogService;


/**
 * Service - 红包失败记录
 * 
 * @author arf
 * @version 4.0
 */
@Service("redFailLogServiceImpl")
public class RedFailLogServiceImpl extends BaseServiceImpl<RedFailLog, Long> implements RedFailLogService {

	@Resource(name = "redFailLogDaoImpl")
	private RedFailLogDao redFailLogDao;

	@Override
	protected BaseDao<RedFailLog, Long> getBaseDao() {
		return redFailLogDao;
	}

}