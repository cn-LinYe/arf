/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import com.arf.core.dao.PaymentSetingModelDao;
import com.arf.core.dao.BaseDao;
import com.arf.core.entity.PaymentSetingModel;
import com.arf.core.entity.PaymentSetingModel.Type;
import com.arf.core.service.PaymentSetingModelService;


/**
 * Service - 月租车
 * 
 * @author arf dg
 * @version 4.0
 */
@Service("paymentSetingModelServiceImpl")
public class PaymentSetingModelServiceImpl extends BaseServiceImpl<PaymentSetingModel, Long> implements PaymentSetingModelService {

	@Resource(name = "paymentSetingModelDaoImpl")
	private PaymentSetingModelDao paymentSetingModelDao;

	@Override
	protected BaseDao<PaymentSetingModel, Long> getBaseDao() {
		return paymentSetingModelDao;
	}

	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public PaymentSetingModel findByCommunityNumber(String CommunityNumber) {
		return paymentSetingModelDao.findByCommunityNumber(CommunityNumber);
	}

	@Override
	public PaymentSetingModel findByObjectTypeNumber(Type type, String number) {
		return paymentSetingModelDao.findByObjectTypeNumber(type,number);
	}

}