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

import com.arf.core.dao.TemporaryCarOrderDao;
import com.arf.core.dao.BaseDao;
import com.arf.core.entity.TemporaryCarOrder;
import com.arf.core.service.TemporaryCarOrderService;


/**
 * Service - 临时车牌表
 * 
 * @author arf dg
 * @version 4.0
 */
@Service("temporaryCarOrderServiceImpl")
public class TemporaryCarOrderServiceImpl extends BaseServiceImpl<TemporaryCarOrder, Long> implements TemporaryCarOrderService {

	@Resource(name = "temporaryCarOrderDaoImpl")
	private TemporaryCarOrderDao temporaryCarOrderDao;

	@Override
	protected BaseDao<TemporaryCarOrder, Long> getBaseDao() {
		return temporaryCarOrderDao;
	}

	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public TemporaryCarOrder selectByouttradeno(String out_trade_no) {
		return temporaryCarOrderDao.selectByouttradeno(out_trade_no);
	}
	

}