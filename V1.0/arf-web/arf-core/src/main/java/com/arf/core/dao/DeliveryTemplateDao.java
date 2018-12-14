/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.dao;

import com.arf.core.entity.DeliveryTemplate;

/**
 * Dao - 快递单模板
 * 
 * @author arf
 * @version 4.0
 */
public interface DeliveryTemplateDao extends BaseDao<DeliveryTemplate, Long> {

	/**
	 * 查找默认快递单模板
	 * 
	 * @return 默认快递单模板，若不存在则返回null
	 */
	DeliveryTemplate findDefault();

	/**
	 * 设置默认快递单模板
	 * 
	 * @param deliveryTemplate
	 *            快递单模板
	 */
	void setDefault(DeliveryTemplate deliveryTemplate);

}