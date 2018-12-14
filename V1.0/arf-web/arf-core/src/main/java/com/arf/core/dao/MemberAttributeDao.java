/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.dao;

import java.util.List;

import com.arf.core.Filter;
import com.arf.core.Order;
import com.arf.core.entity.MemberAttribute;

/**
 * Dao - 会员注册项
 * 
 * @author arf
 * @version 4.0
 */
public interface MemberAttributeDao extends BaseDao<MemberAttribute, Long> {

	/**
	 * 查找未使用的属性序号
	 * 
	 * @return 未使用的属性序号，若不存在则返回null
	 */
	Integer findUnusedPropertyIndex();

	/**
	 * 查找会员注册项
	 * 
	 * @param isEnabled
	 *            是否启用
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @return 会员注册项
	 */
	List<MemberAttribute> findList(Boolean isEnabled, Integer count, List<Filter> filters, List<Order> orders);

}