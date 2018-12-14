/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.dao.impl;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.arf.core.Page;
import com.arf.core.Pageable;
import com.arf.core.dao.FreightConfigDao;
import com.arf.core.entity.Area;
import com.arf.core.entity.FreightConfig;
import com.arf.core.entity.ShippingMethod;

import org.springframework.stereotype.Repository;

/**
 * Dao - 运费配置
 * 
 * @author arf
 * @version 4.0
 */
@Repository("freightConfigDaoImpl")
public class FreightConfigDaoImpl extends BaseDaoImpl<FreightConfig, Long> implements FreightConfigDao {

	public boolean exists(ShippingMethod shippingMethod, Area area) {
		if (shippingMethod == null || area == null) {
			return false;
		}
		String jpql = "select count(*) from FreightConfig freightConfig where freightConfig.shippingMethod = :shippingMethod and freightConfig.area = :area";
		Long count = entityManager.createQuery(jpql, Long.class).setParameter("shippingMethod", shippingMethod).setParameter("area", area).getSingleResult();
		return count > 0;
	}

	public Page<FreightConfig> findPage(ShippingMethod shippingMethod, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<FreightConfig> criteriaQuery = criteriaBuilder.createQuery(FreightConfig.class);
		Root<FreightConfig> root = criteriaQuery.from(FreightConfig.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (shippingMethod != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("shippingMethod"), shippingMethod));
		}
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}

}