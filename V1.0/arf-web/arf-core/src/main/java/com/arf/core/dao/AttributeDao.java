/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.dao;

import com.arf.core.entity.Attribute;
import com.arf.core.entity.ProductCategory;

/**
 * Dao - 属性
 * 
 * @author arf
 * @version 4.0
 */
public interface AttributeDao extends BaseDao<Attribute, Long> {

	/**
	 * 查找未使用的属性序号
	 * 
	 * @param productCategory
	 *            商品分类
	 * @return 未使用的属性序号，若不存在则返回null
	 */
	Integer findUnusedPropertyIndex(ProductCategory productCategory);

}