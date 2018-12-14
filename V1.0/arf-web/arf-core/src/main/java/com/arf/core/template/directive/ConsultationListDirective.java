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
import com.arf.core.entity.Consultation;
import com.arf.core.service.ConsultationService;
import com.arf.core.util.FreeMarkerUtils;

import org.springframework.stereotype.Component;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 模板指令 - 咨询
 * 
 * @author arf
 * @version 4.0
 */
@Component("consultationListDirective")
public class ConsultationListDirective extends BaseDirective {

	/** "会员ID"参数名称 */
	private static final String MEMBER_ID_PARAMETER_NAME = "memberId";

	/** "货品ID"参数名称 */
	private static final String GOODS_ID_PARAMETER_NAME = "goodsId";

	/** 变量名称 */
	private static final String VARIABLE_NAME = "consultations";

	@Resource(name = "consultationServiceImpl")
	private ConsultationService consultationService;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		Long memberId = FreeMarkerUtils.getParameter(MEMBER_ID_PARAMETER_NAME, Long.class, params);
		Long goodsId = FreeMarkerUtils.getParameter(GOODS_ID_PARAMETER_NAME, Long.class, params);
		Integer count = getCount(params);
		List<Filter> filters = getFilters(params, Consultation.class);
		List<Order> orders = getOrders(params);
		boolean useCache = useCache(env, params);
		List<Consultation> consultations = consultationService.findList(memberId, goodsId, true, count, filters, orders, useCache);
		setLocalVariable(VARIABLE_NAME, consultations, env, body);
	}

}