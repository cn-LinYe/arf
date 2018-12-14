/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import com.arf.core.dao.PayMoneyWayModelDao;
import com.arf.core.dao.BaseDao;
import com.arf.core.entity.PayMoneyWayModel;
import com.arf.core.service.PayMoneyWayModelService;


/**
 * Service - 根据支付方式获取支付金额表
 * 
 * @author arf
 * @version 4.0
 */
@Service("payMoneyWayModelServiceImpl")
public class PayMoneyWayModelServiceImpl extends BaseServiceImpl<PayMoneyWayModel, Long> implements PayMoneyWayModelService {

	@Resource(name = "payMoneyWayModelDaoImpl")
	private PayMoneyWayModelDao payMoneyWayModelDao;

	@Override
	protected BaseDao<PayMoneyWayModel, Long> getBaseDao() {
		return payMoneyWayModelDao;
	}
	
	@Transactional(readOnly = true)
	public PayMoneyWayModel selectByLevel(int level){
		return payMoneyWayModelDao.selectByLevel(level);
	}

}