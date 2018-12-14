/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.template.directive;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.arf.core.Filter;
import com.arf.core.Order;
import com.arf.core.entity.MemberAttribute;
import com.arf.core.service.MemberAttributeService;

import org.springframework.stereotype.Component;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 模板指令 - 会员注册项列表
 * 
 * @author arf
 * @version 4.0
 */
@Component("memberAttributeListDirective")
public class MemberAttributeListDirective extends BaseDirective {

	/** 变量名称 */
	private static final String VARIABLE_NAME = "memberAttributes";

	@Resource(name = "memberAttributeServiceImpl")
	private MemberAttributeService memberAttributeService;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		Integer count = getCount(params);
		List<Filter> filters = getFilters(params, MemberAttribute.class);
		List<Order> orders = getOrders(params);
		boolean useCache = useCache(env, params);
		List<MemberAttribute> memberAttributes = memberAttributeService.findList(true, count, filters, orders, useCache);
		setLocalVariable(VARIABLE_NAME, memberAttributes, env, body);
	}

}