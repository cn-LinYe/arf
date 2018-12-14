/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.dao.impl;

import com.arf.core.dao.PaymentMethodDao;
import com.arf.core.entity.PaymentMethod;

import org.springframework.stereotype.Repository;

/**
 * Dao - 支付方式
 * 
 * @author arf
 * @version 4.0
 */
@Repository("paymentMethodDaoImpl")
public class PaymentMethodDaoImpl extends BaseDaoImpl<PaymentMethod, Long> implements PaymentMethodDao {

}