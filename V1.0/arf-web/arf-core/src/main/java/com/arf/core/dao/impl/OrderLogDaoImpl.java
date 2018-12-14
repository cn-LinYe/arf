/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.dao.impl;

import com.arf.core.dao.OrderLogDao;
import com.arf.core.entity.OrderLog;

import org.springframework.stereotype.Repository;

/**
 * Dao - 订单记录
 * 
 * @author arf
 * @version 4.0
 */
@Repository("orderLogDaoImpl")
public class OrderLogDaoImpl extends BaseDaoImpl<OrderLog, Long> implements OrderLogDao {

}