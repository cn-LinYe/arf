/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;

import javax.annotation.Resource;

import com.arf.core.dao.BaseDao;
import com.arf.core.dao.ReturnsDao;
import com.arf.core.dao.SnDao;
import com.arf.core.entity.Returns;
import com.arf.core.entity.Sn;
import com.arf.core.service.ReturnsService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * Service - 退货单
 * 
 * @author arf
 * @version 4.0
 */
@Service("returnsServiceImpl")
public class ReturnsServiceImpl extends BaseServiceImpl<Returns, Long> implements ReturnsService {

	@Resource(name = "returnsDaoImpl")
	private ReturnsDao returnsDao;
	@Resource(name = "snDaoImpl")
	private SnDao snDao;

	@Override
	protected BaseDao<Returns, Long> getBaseDao() {
		return returnsDao;
	}

	@Override
	@Transactional
	public Returns save(Returns returns) {
		Assert.notNull(returns);

		returns.setSn(snDao.generate(Sn.Type.returns));

		return super.save(returns);
	}

}