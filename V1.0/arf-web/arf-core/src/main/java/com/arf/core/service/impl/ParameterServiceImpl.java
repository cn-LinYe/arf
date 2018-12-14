/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;

import javax.annotation.Resource;

import com.arf.core.dao.BaseDao;
import com.arf.core.dao.ParameterDao;
import com.arf.core.entity.Parameter;
import com.arf.core.service.ParameterService;

import org.springframework.stereotype.Service;

/**
 * Service - 参数
 * 
 * @author arf
 * @version 4.0
 */
@Service("parameterServiceImpl")
public class ParameterServiceImpl extends BaseServiceImpl<Parameter, Long> implements ParameterService {

	@Resource(name = "parameterDaoImpl")
	private ParameterDao parameterDao;

	@Override
	protected BaseDao<Parameter, Long> getBaseDao() {
		return parameterDao;
	}

}