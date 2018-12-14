/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;


import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import com.arf.core.dao.CommunityLogDao;
import com.arf.core.dao.BaseDao;
import com.arf.core.entity.CommunityLog;
import com.arf.core.service.CommunityLogService;


/**
 * Service - 用户切换小区的记录
 * 
 * @author arf
 * @version 4.0
 */
@Service("communityLogServiceImpl")
public class CommunityLogServiceImpl extends BaseServiceImpl<CommunityLog, Long> implements CommunityLogService {

	@Resource(name = "communityLogDaoImpl")
	private CommunityLogDao communityLogDao;

	@Override
	protected BaseDao<CommunityLog, Long> getBaseDao() {
		return communityLogDao;
	}

}