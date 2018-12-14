/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.dao.impl;

import java.util.Date;

import javax.persistence.NoResultException;

import com.arf.core.dao.CartDao;
import com.arf.core.entity.Cart;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Repository;

/**
 * Dao - 购物车
 * 
 * @author arf
 * @version 4.0
 */
@Repository("cartDaoImpl")
public class CartDaoImpl extends BaseDaoImpl<Cart, Long> implements CartDao {

	public Cart findByKey(String key) {
		if (StringUtils.isEmpty(key)) {
			return null;
		}

		try {
			String jpql = "select cart from Cart cart where cart.key = :key";
			return entityManager.createQuery(jpql, Cart.class).setParameter("key", key).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public void evictExpired() {
		String jpql = "delete from Cart cart where cart.modifyDate <= :expire";
		entityManager.createQuery(jpql).setParameter("expire", DateUtils.addSeconds(new Date(), -Cart.TIMEOUT)).executeUpdate();
	}

}