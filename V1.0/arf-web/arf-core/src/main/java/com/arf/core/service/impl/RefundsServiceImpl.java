/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;

import javax.annotation.Resource;

import com.arf.core.dao.BaseDao;
import com.arf.core.dao.RefundsDao;
import com.arf.core.dao.SnDao;
import com.arf.core.entity.Refunds;
import com.arf.core.entity.Sn;
import com.arf.core.service.RefundsService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * Service - 退款单
 * 
 * @author arf
 * @version 4.0
 */
@Service("refundsServiceImpl")
public class RefundsServiceImpl extends BaseServiceImpl<Refunds, Long> implements RefundsService {

	@Resource(name = "refundsDaoImpl")
	private RefundsDao refundsDao;
	@Resource(name = "snDaoImpl")
	private SnDao snDao;

	@Override
	protected BaseDao<Refunds, Long> getBaseDao() {
		return refundsDao;
	}

	@Override
	@Transactional
	public Refunds save(Refunds refunds) {
		Assert.notNull(refunds);

		refunds.setSn(snDao.generate(Sn.Type.refunds));

		return super.save(refunds);
	}

}