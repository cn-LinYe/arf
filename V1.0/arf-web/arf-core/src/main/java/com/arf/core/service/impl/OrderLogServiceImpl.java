/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;

import javax.annotation.Resource;

import com.arf.core.dao.BaseDao;
import com.arf.core.dao.OrderLogDao;
import com.arf.core.entity.OrderLog;
import com.arf.core.service.OrderLogService;

import org.springframework.stereotype.Service;

/**
 * Service - 订单记录
 * 
 * @author arf
 * @version 4.0
 */
@Service("orderLogServiceImpl")
public class OrderLogServiceImpl extends BaseServiceImpl<OrderLog, Long> implements OrderLogService {

	@Resource(name = "orderLogDaoImpl")
	private OrderLogDao orderLogDao;

	@Override
	protected BaseDao<OrderLog, Long> getBaseDao() {
		return orderLogDao;
	}

}