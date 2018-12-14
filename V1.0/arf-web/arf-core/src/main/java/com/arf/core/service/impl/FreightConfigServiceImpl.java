/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;

import javax.annotation.Resource;

import com.arf.core.Page;
import com.arf.core.Pageable;
import com.arf.core.dao.BaseDao;
import com.arf.core.dao.FreightConfigDao;
import com.arf.core.entity.Area;
import com.arf.core.entity.FreightConfig;
import com.arf.core.entity.ShippingMethod;
import com.arf.core.service.FreightConfigService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service - 运费配置
 * 
 * @author arf
 * @version 4.0
 */
@Service("freightConfigServiceImpl")
public class FreightConfigServiceImpl extends BaseServiceImpl<FreightConfig, Long> implements FreightConfigService {

	@Resource(name = "freightConfigDaoImpl")
	private FreightConfigDao freightConfigDao;

	@Override
	protected BaseDao<FreightConfig, Long> getBaseDao() {
		return freightConfigDao;
	}

	@Transactional(readOnly = true)
	public boolean exists(ShippingMethod shippingMethod, Area area) {
		return freightConfigDao.exists(shippingMethod, area);
	}

	@Transactional(readOnly = true)
	public boolean unique(ShippingMethod shippingMethod, Area previousArea, Area currentArea) {
		if (previousArea != null && previousArea.equals(currentArea)) {
			return true;
		}
		return !freightConfigDao.exists(shippingMethod, currentArea);
	}

	@Transactional(readOnly = true)
	public Page<FreightConfig> findPage(ShippingMethod shippingMethod, Pageable pageable) {
		return freightConfigDao.findPage(shippingMethod, pageable);
	}

}