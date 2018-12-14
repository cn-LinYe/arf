/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.arf.core.entity.Cart;
import com.arf.core.util.WebUtils;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * Interceptor - 购物车数量
 * 
 * @author arf
 * @version 4.0
 */
public class CartQuantityInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		WebUtils.removeCookie(request, response, Cart.QUANTITY_COOKIE_NAME);
		return super.preHandle(request, response, handler);
	}

}