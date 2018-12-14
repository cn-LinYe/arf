/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.dao;

import java.util.List;

import com.arf.core.entity.ProductCategory;

/**
 * Dao - 商品分类
 * 
 * @author arf
 * @version 4.0
 */
public interface ProductCategoryDao extends BaseDao<ProductCategory, Long> {

	/**
	 * 查找顶级商品分类
	 * 
	 * @param count
	 *            数量
	 * @return 顶级商品分类
	 */
	List<ProductCategory> findRoots(Integer count);

	/**
	 * 查找上级商品分类
	 * 
	 * @param productCategory
	 *            商品分类
	 * @param recursive
	 *            是否递归
	 * @param count
	 *            数量
	 * @return 上级商品分类
	 */
	List<ProductCategory> findParents(ProductCategory productCategory, boolean recursive, Integer count);

	/**
	 * 查找下级商品分类
	 * 
	 * @param productCategory
	 *            商品分类
	 * @param recursive
	 *            是否递归
	 * @param count
	 *            数量
	 * @return 下级商品分类
	 */
	List<ProductCategory> findChildren(ProductCategory productCategory, boolean recursive, Integer count);

}