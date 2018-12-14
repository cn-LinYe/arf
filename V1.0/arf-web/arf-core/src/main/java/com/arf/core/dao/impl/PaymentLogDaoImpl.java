/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.dao.impl;

import javax.persistence.NoResultException;

import com.arf.core.dao.PaymentLogDao;
import com.arf.core.entity.PaymentLog;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

/**
 * Dao - 支付记录
 * 
 * @author arf
 * @version 4.0
 */
@Repository("paymentLogDaoImpl")
public class PaymentLogDaoImpl extends BaseDaoImpl<PaymentLog, Long> implements PaymentLogDao {

	public PaymentLog findBySn(String sn) {
		if (StringUtils.isEmpty(sn)) {
			return null;
		}
		String jpql = "select paymentLog from PaymentLog paymentLog where lower(paymentLog.sn) = lower(:sn)";
		try {
			return entityManager.createQuery(jpql, PaymentLog.class).setParameter("sn", sn).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}