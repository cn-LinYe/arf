/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service;

import java.util.List;

import com.arf.core.TemplateConfig;
import com.arf.core.TemplateConfig.Type;

/**
 * Service - 模板配置
 * 
 * @author arf
 * @version 4.0
 */
public interface TemplateConfigService {

	/**
	 * 获取所有模板配置
	 * 
	 * @return 所有模板配置
	 */
	List<TemplateConfig> getAll();

	/**
	 * 获取模板配置
	 * 
	 * @param type
	 *            类型
	 * @return 模板配置
	 */
	List<TemplateConfig> getList(Type type);

	/**
	 * 获取模板配置
	 * 
	 * @param id
	 *            ID
	 * @return 模板配置
	 */
	TemplateConfig get(String id);

}