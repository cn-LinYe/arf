/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core;

import com.arf.core.util.FreeMarkerUtils;

import org.springframework.web.servlet.view.AbstractTemplateViewResolver;
import org.springframework.web.servlet.view.AbstractUrlBasedView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

/**
 * FreeMarker视图解析器
 * 
 * @author arf
 * @version 4.0
 */
public class FreeMarkerViewResolver extends AbstractTemplateViewResolver {

	public FreeMarkerViewResolver() {
		setViewClass(requiredViewClass());
	}

	@Override
	protected Class<FreeMarkerView> requiredViewClass() {
		return FreeMarkerView.class;
	}

	@Override
	protected AbstractUrlBasedView buildView(String viewName) throws Exception {
		return super.buildView(FreeMarkerUtils.process(viewName));
	}

}