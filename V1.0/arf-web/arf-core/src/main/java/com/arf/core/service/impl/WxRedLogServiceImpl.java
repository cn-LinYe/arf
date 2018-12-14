/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;


import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import com.arf.core.dao.WxRedLogDao;
import com.arf.core.dao.BaseDao;
import com.arf.core.entity.WxRedLog;
import com.arf.core.service.WxRedLogService;


/**
 * Service - 微信红包发送的记录
 * 
 * @author arf
 * @version 4.0
 */
@Service("wxRedLogServiceImpl")
public class WxRedLogServiceImpl extends BaseServiceImpl<WxRedLog, Long> implements WxRedLogService {

	@Resource(name = "wxRedLogDaoImpl")
	private WxRedLogDao wxRedLogDao;

	@Override
	protected BaseDao<WxRedLog, Long> getBaseDao() {
		return wxRedLogDao;
	}

}