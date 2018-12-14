/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.dao.impl;

import javax.persistence.NoResultException;

import com.arf.core.dao.DeliveryCenterDao;
import com.arf.core.entity.DeliveryCenter;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

/**
 * Dao - 发货点
 * 
 * @author arf
 * @version 4.0
 */
@Repository("deliveryCenterDaoImpl")
public class DeliveryCenterDaoImpl extends BaseDaoImpl<DeliveryCenter, Long> implements DeliveryCenterDao {

	public DeliveryCenter findDefault() {
		try {
			String jpql = "select deliveryCenter from DeliveryCenter deliveryCenter where deliveryCenter.isDefault = true";
			return entityManager.createQuery(jpql, DeliveryCenter.class).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public void setDefault(DeliveryCenter deliveryCenter) {
		Assert.notNull(deliveryCenter);

		deliveryCenter.setIsDefault(true);
		if (deliveryCenter.isNew()) {
			String jpql = "update DeliveryCenter deliveryCenter set deliveryCenter.isDefault = false where deliveryCenter.isDefault = true";
			entityManager.createQuery(jpql).executeUpdate();
		} else {
			String jpql = "update DeliveryCenter deliveryCenter set deliveryCenter.isDefault = false where deliveryCenter.isDefault = true and deliveryCenter != :deliveryCenter";
			entityManager.createQuery(jpql).setParameter("deliveryCenter", deliveryCenter).executeUpdate();
		}
	}

}