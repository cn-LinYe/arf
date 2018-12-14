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
import com.arf.core.LogConfig;
import com.arf.core.service.LogConfigService;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

/**
 * Service - 日志配置
 * 
 * @author arf
 * @version 4.0
 */
@Service("logConfigServiceImpl")
public class LogConfigServiceImpl implements LogConfigService {

	@SuppressWarnings("unchecked")
	@Cacheable("logConfig")
	public List<LogConfig> getAll() {
		try {
			File shopxxXmlFile = new ClassPathResource(CommonAttributes.SHOPXX_XML_PATH).getFile();
			Document document = new SAXReader().read(shopxxXmlFile);
			List<org.dom4j.Element> elements = document.selectNodes("/shopxx/logConfig");
			List<LogConfig> logConfigs = new ArrayList<LogConfig>();
			for (org.dom4j.Element element : elements) {
				String operation = element.attributeValue("operation");
				String urlPattern = element.attributeValue("urlPattern");
				LogConfig logConfig = new LogConfig();
				logConfig.setOperation(operation);
				logConfig.setUrlPattern(urlPattern);
				logConfigs.add(logConfig);
			}
			return logConfigs;
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (DocumentException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

}