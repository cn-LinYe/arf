/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;

import javax.annotation.Resource;

import com.arf.core.dao.BaseDao;
import com.arf.core.dao.OrderItemDao;
import com.arf.core.entity.OrderItem;
import com.arf.core.service.OrderItemService;

import org.springframework.stereotype.Service;

/**
 * Service - 订单项
 * 
 * @author arf
 * @version 4.0
 */
@Service("orderItemServiceImpl")
public class OrderItemServiceImpl extends BaseServiceImpl<OrderItem, Long> implements OrderItemService {

	@Resource(name = "orderItemDaoImpl")
	private OrderItemDao orderItemDao;

	@Override
	protected BaseDao<OrderItem, Long> getBaseDao() {
		return orderItemDao;
	}

}