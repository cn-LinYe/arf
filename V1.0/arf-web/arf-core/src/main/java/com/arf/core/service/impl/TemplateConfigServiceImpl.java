/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.arf.core.CommonAttributes;
import com.arf.core.TemplateConfig;
import com.arf.core.TemplateConfig.Type;
import com.arf.core.service.TemplateConfigService;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * Service - 模板配置
 * 
 * @author arf
 * @version 4.0
 */
@Service("templateConfigServiceImpl")
public class TemplateConfigServiceImpl implements TemplateConfigService {

	@Value("${template.loader_path}")
	private String templateLoaderPath;

	@SuppressWarnings("unchecked")
	@Cacheable("templateConfig")
	public List<TemplateConfig> getAll() {
		try {
			File shopxxXmlFile = new ClassPathResource(CommonAttributes.SHOPXX_XML_PATH).getFile();
			Document document = new SAXReader().read(shopxxXmlFile);
			List<TemplateConfig> templateConfigs = new ArrayList<TemplateConfig>();
			List<Element> elements = document.selectNodes("/shopxx/templateConfig");
			for (Element element : elements) {
				templateConfigs.add(getTemplateConfig(element));
			}
			return templateConfigs;
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (DocumentException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@SuppressWarnings("unchecked")
	@Cacheable("templateConfig")
	public List<TemplateConfig> getList(Type type) {
		if (type != null) {
			try {
				File shopxxXmlFile = new ClassPathResource(CommonAttributes.SHOPXX_XML_PATH).getFile();
				Document document = new SAXReader().read(shopxxXmlFile);
				List<TemplateConfig> templateConfigs = new ArrayList<TemplateConfig>();
				List<Element> elements = document.selectNodes("/shopxx/templateConfig[@type='" + type + "']");
				for (Element element : elements) {
					templateConfigs.add(getTemplateConfig(element));
				}
				return templateConfigs;
			} catch (IOException e) {
				throw new RuntimeException(e.getMessage(), e);
			} catch (DocumentException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		} else {
			return getAll();
		}
	}

	@Cacheable("templateConfig")
	public TemplateConfig get(String id) {
		try {
			File shopxxXmlFile = new ClassPathResource(CommonAttributes.SHOPXX_XML_PATH).getFile();
			Document document = new SAXReader().read(shopxxXmlFile);
			Element element = (Element) document.selectSingleNode("/shopxx/templateConfig[@id='" + id + "']");
			if (element != null) {
				return getTemplateConfig(element);
			}
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (DocumentException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 获取模板配置
	 * 
	 * @param element
	 *            元素
	 */
	private TemplateConfig getTemplateConfig(Element element) {
		Assert.notNull(element);

		String id = element.attributeValue("id");
		String type = element.attributeValue("type");
		String name = element.attributeValue("name");
		String templatePath = element.attributeValue("templatePath");
		String staticPath = element.attributeValue("staticPath");
		String description = element.attributeValue("description");

		TemplateConfig templateConfig = new TemplateConfig();
		templateConfig.setId(id);
		templateConfig.setType(Type.valueOf(type));
		templateConfig.setName(name);
		templateConfig.setTemplatePath(templatePath);
		templateConfig.setStaticPath(staticPath);
		templateConfig.setDescription(description);
		return templateConfig;
	}

}