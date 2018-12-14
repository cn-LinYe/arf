/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.dao.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.arf.core.Filter;
import com.arf.core.Order;
import com.arf.core.dao.BrandDao;
import com.arf.core.entity.Brand;
import com.arf.core.entity.ProductCategory;

import org.springframework.stereotype.Repository;

/**
 * Dao - 品牌
 * 
 * @author arf
 * @version 4.0
 */
@Repository("brandDaoImpl")
public class BrandDaoImpl extends BaseDaoImpl<Brand, Long> implements BrandDao {
	public List<Brand> findList(ProductCategory productCategory, Integer count, List<Filter> filters, List<Order> orders) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Brand> criteriaQuery = criteriaBuilder.createQuery(Brand.class);
		Root<Brand> root = criteriaQuery.from(Brand.class);
		criteriaQuery.select(root);
		if (productCategory != null) {
			criteriaQuery.where(criteriaBuilder.equal(root.join("productCategories"), productCategory));
		}
		return super.findList(criteriaQuery, null, count, filters, orders);
	}
}