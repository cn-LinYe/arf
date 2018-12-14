/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;

import javax.annotation.Resource;

import com.arf.core.dao.BaseDao;
import com.arf.core.dao.PaymentMethodDao;
import com.arf.core.entity.PaymentMethod;
import com.arf.core.service.PaymentMethodService;

import org.springframework.stereotype.Service;

/**
 * Service - 支付方式
 * 
 * @author arf
 * @version 4.0
 */
@Service("paymentMethodServiceImpl")
public class PaymentMethodServiceImpl extends BaseServiceImpl<PaymentMethod, Long> implements PaymentMethodService {

	@Resource(name = "paymentMethodDaoImpl")
	private PaymentMethodDao paymentMethodDao;

	@Override
	protected BaseDao<PaymentMethod, Long> getBaseDao() {
		return paymentMethodDao;
	}

}