/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.template.directive;

import java.io.IOException;
import java.io.StringReader;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.arf.core.entity.AdPosition;
import com.arf.core.service.AdPositionService;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.core.Environment;
import freemarker.template.Template;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 模板指令 - 广告位
 * 
 * @author arf
 * @version 4.0
 */
@Component("adPositionDirective")
public class AdPositionDirective extends BaseDirective {

	/** 变量名称 */
	private static final String VARIABLE_NAME = "adPosition";

	@Resource(name = "freeMarkerConfigurer")
	private FreeMarkerConfigurer freeMarkerConfigurer;
	@Resource(name = "adPositionServiceImpl")
	private AdPositionService adPositionService;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		Long id = getId(params);
		boolean useCache = useCache(env, params);
		AdPosition adPosition = adPositionService.find(id, useCache);
		if (body != null) {
			setLocalVariable(VARIABLE_NAME, adPosition, env, body);
		} else {
			if (adPosition != null && adPosition.getTemplate() != null) {
				try {
					Map<String, Object> model = new HashMap<String, Object>();
					model.put(VARIABLE_NAME, adPosition);
					Writer out = env.getOut();
					new Template("adTemplate", new StringReader(adPosition.getTemplate()), freeMarkerConfigurer.getConfiguration()).process(model, out);
				} catch (TemplateException e) {
					throw new RuntimeException(e.getMessage(), e);
				} catch (IOException e) {
					throw new RuntimeException(e.getMessage(), e);
				}
			}
		}
	}

}