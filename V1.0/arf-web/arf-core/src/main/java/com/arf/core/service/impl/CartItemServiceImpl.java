/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;

import javax.annotation.Resource;

import com.arf.core.dao.BaseDao;
import com.arf.core.dao.CartItemDao;
import com.arf.core.entity.CartItem;
import com.arf.core.service.CartItemService;

import org.springframework.stereotype.Service;

/**
 * Service - 购物车项
 * 
 * @author arf
 * @version 4.0
 */
@Service("cartItemServiceImpl")
public class CartItemServiceImpl extends BaseServiceImpl<CartItem, Long> implements CartItemService {

	@Resource(name = "cartItemDaoImpl")
	private CartItemDao cartItemDao;

	@Override
	protected BaseDao<CartItem, Long> getBaseDao() {
		return cartItemDao;
	}

}