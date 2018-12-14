/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;

import javax.annotation.Resource;

import com.arf.core.dao.BaseDao;
import com.arf.core.dao.DeliveryCorpDao;
import com.arf.core.entity.DeliveryCorp;
import com.arf.core.service.DeliveryCorpService;

import org.springframework.stereotype.Service;

/**
 * Service - 物流公司
 * 
 * @author arf
 * @version 4.0
 */
@Service("deliveryCorpServiceImpl")
public class DeliveryCorpServiceImpl extends BaseServiceImpl<DeliveryCorp, Long> implements DeliveryCorpService {

	@Resource(name = "deliveryCorpDaoImpl")
	private DeliveryCorpDao deliveryCorpDao;

	@Override
	protected BaseDao<DeliveryCorp, Long> getBaseDao() {
		return deliveryCorpDao;
	}

}