/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.dao;

import com.arf.core.entity.DeliveryCenter;

/**
 * Dao - 发货点
 * 
 * @author arf
 * @version 4.0
 */
public interface DeliveryCenterDao extends BaseDao<DeliveryCenter, Long> {

	/**
	 * 查找默认发货点
	 * 
	 * @return 默认发货点，若不存在则返回null
	 */
	DeliveryCenter findDefault();

	/**
	 * 设置默认发货点
	 * 
	 * @param deliveryCenter
	 *            发货点
	 */
	void setDefault(DeliveryCenter deliveryCenter);

}