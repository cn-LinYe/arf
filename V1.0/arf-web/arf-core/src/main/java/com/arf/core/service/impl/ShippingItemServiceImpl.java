/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;

import javax.annotation.Resource;

import com.arf.core.dao.BaseDao;
import com.arf.core.dao.ShippingItemDao;
import com.arf.core.entity.ShippingItem;
import com.arf.core.service.ShippingItemService;

import org.springframework.stereotype.Service;

/**
 * Service - 发货项
 * 
 * @author arf
 * @version 4.0
 */
@Service("shippingItemServiceImpl")
public class ShippingItemServiceImpl extends BaseServiceImpl<ShippingItem, Long> implements ShippingItemService {

	@Resource(name = "shippingItemDaoImpl")
	private ShippingItemDao shippingItemDao;

	@Override
	protected BaseDao<ShippingItem, Long> getBaseDao() {
		return shippingItemDao;
	}

}