/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.dao.impl;

import javax.persistence.NoResultException;

import com.arf.core.dao.ShippingDao;
import com.arf.core.entity.Shipping;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

/**
 * Dao - 发货单
 * 
 * @author arf
 * @version 4.0
 */
@Repository("shippingDaoImpl")
public class ShippingDaoImpl extends BaseDaoImpl<Shipping, Long> implements ShippingDao {

	public Shipping findBySn(String sn) {
		if (StringUtils.isEmpty(sn)) {
			return null;
		}
		String jpql = "select shipping from Shipping shipping where lower(shipping.sn) = lower(:sn)";
		try {
			return entityManager.createQuery(jpql, Shipping.class).setParameter("sn", sn).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}