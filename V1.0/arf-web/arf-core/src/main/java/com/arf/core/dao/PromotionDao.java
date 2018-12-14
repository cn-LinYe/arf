/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.dao;

import java.util.List;

import com.arf.core.Filter;
import com.arf.core.Order;
import com.arf.core.entity.MemberRank;
import com.arf.core.entity.ProductCategory;
import com.arf.core.entity.Promotion;

/**
 * Dao - 促销
 * 
 * @author arf
 * @version 4.0
 */
public interface PromotionDao extends BaseDao<Promotion, Long> {

	/**
	 * 查找促销
	 * 
	 * @param memberRank
	 *            会员等级
	 * @param productCategory
	 *            商品分类
	 * @param hasBegun
	 *            是否已开始
	 * @param hasEnded
	 *            是否已结束
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @return 促销
	 */
	List<Promotion> findList(MemberRank memberRank, ProductCategory productCategory, Boolean hasBegun, Boolean hasEnded, Integer count, List<Filter> filters, List<Order> orders);

}