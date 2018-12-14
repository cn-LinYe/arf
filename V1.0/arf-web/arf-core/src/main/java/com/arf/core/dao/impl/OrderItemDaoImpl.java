/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.dao.impl;

import com.arf.core.dao.OrderItemDao;
import com.arf.core.entity.OrderItem;

import org.springframework.stereotype.Repository;

/**
 * Dao - 订单项
 * 
 * @author arf
 * @version 4.0
 */
@Repository("orderItemDaoImpl")
public class OrderItemDaoImpl extends BaseDaoImpl<OrderItem, Long> implements OrderItemDao {

}