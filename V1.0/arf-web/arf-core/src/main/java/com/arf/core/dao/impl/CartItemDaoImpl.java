/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.dao.impl;

import com.arf.core.dao.CartItemDao;
import com.arf.core.entity.CartItem;

import org.springframework.stereotype.Repository;

/**
 * Dao - 购物车项
 * 
 * @author arf
 * @version 4.0
 */
@Repository("cartItemDaoImpl")
public class CartItemDaoImpl extends BaseDaoImpl<CartItem, Long> implements CartItemDao {

}