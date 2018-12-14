/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * Interceptor - 令牌
 * 
 * @author arf
 * @version 4.0
 */
public class TokenInterceptor extends HandlerInterceptorAdapter {

	/** "令牌"属性名称 */
	private static final String TOKEN_ATTRIBUTE_NAME = "token";

	/** "令牌"Cookie名称 */
	private static final String TOKEN_COOKIE_NAME = "token";

	/** "令牌"参数名称 */
	private static final String TOKEN_PARAMETER_NAME = "token";

	/** 错误消息 */
	private static final String ERROR_MESSAGE = "Bad or missing token!";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//		String token = WebUtils.getCookie(request, TOKEN_COOKIE_NAME);
//		if (StringUtils.equalsIgnoreCase(request.getMethod(), "POST")) {
//			if (StringUtils.isNotEmpty(token)) {
//				String requestType = request.getHeader("X-Requested-With");
//				if (StringUtils.equalsIgnoreCase(requestType, "XMLHttpRequest")) {
//					if (StringUtils.equals(token, request.getHeader(TOKEN_PARAMETER_NAME))) {
//						return true;
//					} else {
//						response.addHeader("tokenStatus", "accessDenied");
//					}
//				} else {
//					if (StringUtils.equals(token, request.getParameter(TOKEN_PARAMETER_NAME))) {
//						return true;
//					}
//				}
//			} else {
//				WebUtils.addCookie(request, response, TOKEN_COOKIE_NAME, DigestUtils.md5Hex(UUID.randomUUID() + RandomStringUtils.randomAlphabetic(30)));
//			}
//			response.sendError(HttpServletResponse.SC_FORBIDDEN, ERROR_MESSAGE);
//			return false;
//		} else {
//			if (StringUtils.isEmpty(token)) {
//				token = DigestUtils.md5Hex(UUID.randomUUID() + RandomStringUtils.randomAlphabetic(30));
//				WebUtils.addCookie(request, response, TOKEN_COOKIE_NAME, token);
//			}
//			request.setAttribute(TOKEN_ATTRIBUTE_NAME, token);
//			return true;
//		}
		
		return true;
	}

}