/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;

import java.io.File;
import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import com.arf.core.TemplateConfig;
import com.arf.core.service.TemplateConfigService;
import com.arf.core.service.TemplateService;
import com.arf.core.util.FreeMarkerUtils;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.context.ServletContextAware;

import freemarker.template.TemplateException;

/**
 * Service - 模板
 * 
 * @author arf
 * @version 4.0
 */
@Service("templateServiceImpl")
public class TemplateServiceImpl implements TemplateService, ServletContextAware {

	/** servletContext */
	private ServletContext servletContext;

	@Value("${template.loader_path}")
	private String templateLoaderPath;

	@Resource(name = "templateConfigServiceImpl")
	private TemplateConfigService templateConfigService;

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	public String read(String templateConfigId) {
		Assert.hasText(templateConfigId);

		TemplateConfig templateConfig = templateConfigService.get(templateConfigId);
		return read(templateConfig);
	}

	public String read(TemplateConfig templateConfig) {
		Assert.notNull(templateConfig);

		try {
			String templatePath = servletContext.getRealPath(templateLoaderPath + FreeMarkerUtils.process(templateConfig.getRealTemplatePath()));
			File templateFile = new File(templatePath);
			return FileUtils.readFileToString(templateFile, "UTF-8");
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (TemplateException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public void write(String templateConfigId, String content) {
		Assert.hasText(templateConfigId);

		TemplateConfig templateConfig = templateConfigService.get(templateConfigId);
		write(templateConfig, content);
	}

	public void write(TemplateConfig templateConfig, String content) {
		Assert.notNull(templateConfig);

		try {
			String templatePath = servletContext.getRealPath(templateLoaderPath + FreeMarkerUtils.process(templateConfig.getRealTemplatePath()));
			File templateFile = new File(templatePath);
			FileUtils.writeStringToFile(templateFile, content, "UTF-8");
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (TemplateException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

}