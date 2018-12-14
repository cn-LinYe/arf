/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.dao.impl;

import javax.persistence.NoResultException;

import com.arf.core.dao.PaymentDao;
import com.arf.core.entity.Payment;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

/**
 * Dao - 收款单
 * 
 * @author arf
 * @version 4.0
 */
@Repository("paymentDaoImpl")
public class PaymentDaoImpl extends BaseDaoImpl<Payment, Long> implements PaymentDao {

	public Payment findBySn(String sn) {
		if (StringUtils.isEmpty(sn)) {
			return null;
		}
		String jpql = "select payment from Payment payment where lower(payment.sn) = lower(:sn)";
		try {
			return entityManager.createQuery(jpql, Payment.class).setParameter("sn", sn).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}