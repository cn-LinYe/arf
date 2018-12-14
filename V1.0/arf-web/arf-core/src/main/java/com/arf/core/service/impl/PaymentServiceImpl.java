/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;

import javax.annotation.Resource;

import com.arf.core.dao.BaseDao;
import com.arf.core.dao.PaymentDao;
import com.arf.core.dao.SnDao;
import com.arf.core.entity.Payment;
import com.arf.core.entity.Sn;
import com.arf.core.service.PaymentService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * Service - 收款单
 * 
 * @author arf
 * @version 4.0
 */
@Service("paymentServiceImpl")
public class PaymentServiceImpl extends BaseServiceImpl<Payment, Long> implements PaymentService {

	@Resource(name = "paymentDaoImpl")
	private PaymentDao paymentDao;
	@Resource(name = "snDaoImpl")
	private SnDao snDao;

	@Override
	protected BaseDao<Payment, Long> getBaseDao() {
		return paymentDao;
	}

	@Transactional(readOnly = true)
	public Payment findBySn(String sn) {
		return paymentDao.findBySn(sn);
	}

	@Override
	@Transactional
	public Payment save(Payment payment) {
		Assert.notNull(payment);

		payment.setSn(snDao.generate(Sn.Type.payment));

		return super.save(payment);
	}

}