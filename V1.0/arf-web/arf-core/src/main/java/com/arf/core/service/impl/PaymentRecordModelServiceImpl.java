/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.annotation.Resource;
import com.arf.core.dao.PaymentRecordModelDao;
import com.arf.core.dao.BaseDao;
import com.arf.core.entity.PaymentRecordModel;
import com.arf.core.service.PaymentRecordModelService;


/**
 * Service - 支付纪录表
 * 
 * @author arf
 * @version 4.0
 */
@Service("paymentRecordModelServiceImpl")
public class PaymentRecordModelServiceImpl extends BaseServiceImpl<PaymentRecordModel, Long> implements PaymentRecordModelService {

	@Resource(name = "paymentRecordModelDaoImpl")
	private PaymentRecordModelDao paymentRecordModelDao;

	@Override
	protected BaseDao<PaymentRecordModel, Long> getBaseDao() {
		return paymentRecordModelDao;
	}

	@Transactional(readOnly = true)
	public List<PaymentRecordModel> select(String user_name) {
		return paymentRecordModelDao.select(user_name);
	}
}