/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;


import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.arf.core.dao.VersionCodeModelDao;
import com.arf.core.dao.BaseDao;
import com.arf.core.entity.VersionCodeModel;
import com.arf.core.service.VersionCodeModelService;


/**
 * Service - 版本号表
 * 
 * @author arf
 * @version 4.0
 */
@Service("versionCodeModelServiceImpl")
public class VersionCodeModelServiceImpl extends BaseServiceImpl<VersionCodeModel, Long> implements VersionCodeModelService {

	@Resource(name = "versionCodeModelDaoImpl")
	private VersionCodeModelDao versionCodeModelDao;

	@Override
	protected BaseDao<VersionCodeModel, Long> getBaseDao() {
		return versionCodeModelDao;
	}

	@Override
	public List<VersionCodeModel> findVersion() {
		List<VersionCodeModel> versionCodeModels = versionCodeModelDao.findVersion();
			
		return versionCodeModels;
	}

}