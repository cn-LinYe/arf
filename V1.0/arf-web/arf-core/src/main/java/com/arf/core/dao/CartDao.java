/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.dao;

import com.arf.core.entity.Cart;

/**
 * Dao - 购物车
 * 
 * @author arf
 * @version 4.0
 */
public interface CartDao extends BaseDao<Cart, Long> {

	/**
	 * 根据密钥查找购物车
	 * 
	 * @param key
	 *            密钥
	 * @return 购物车，若不存在则返回null
	 */
	Cart findByKey(String key);

	/**
	 * 清除过期购物车
	 */
	void evictExpired();

}