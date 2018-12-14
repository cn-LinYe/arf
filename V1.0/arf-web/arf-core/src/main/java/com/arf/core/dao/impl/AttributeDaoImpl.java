/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.dao.impl;

import com.arf.core.dao.AttributeDao;
import com.arf.core.entity.Attribute;
import com.arf.core.entity.Goods;
import com.arf.core.entity.ProductCategory;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

/**
 * Dao - 属性
 * 
 * @author arf
 * @version 4.0
 */
@Repository("attributeDaoImpl")
public class AttributeDaoImpl extends BaseDaoImpl<Attribute, Long> implements AttributeDao {

	public Integer findUnusedPropertyIndex(ProductCategory productCategory) {
		Assert.notNull(productCategory);

		for (int i = 0; i < Goods.ATTRIBUTE_VALUE_PROPERTY_COUNT; i++) {
			String jpql = "select count(*) from Attribute attribute where attribute.productCategory = :productCategory and attribute.propertyIndex = :propertyIndex";
			Long count = entityManager.createQuery(jpql, Long.class).setParameter("productCategory", productCategory).setParameter("propertyIndex", i).getSingleResult();
			if (count == 0) {
				return i;
			}
		}
		return null;
	}

}