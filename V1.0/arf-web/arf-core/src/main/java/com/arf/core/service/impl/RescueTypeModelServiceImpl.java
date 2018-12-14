/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.annotation.Resource;

import com.arf.core.dao.RescueTypeModelDao;
import com.arf.core.dao.BaseDao;
import com.arf.core.entity.RescueTypeModel;
import com.arf.core.service.RescueTypeModelService;


/**
 * Service - 救援类型表
 * 
 * @author arf
 * @version 4.0
 */
@Service("rescueTypeModelServiceImpl")
public class RescueTypeModelServiceImpl extends BaseServiceImpl<RescueTypeModel, Long> implements RescueTypeModelService {

	@Resource(name = "rescueTypeModelDaoImpl")
	private RescueTypeModelDao rescueTypeModelDao;

	@Override
	protected BaseDao<RescueTypeModel, Long> getBaseDao() {
		return rescueTypeModelDao;
	}

	@Override
	@Transactional(readOnly = true)
	public List<RescueTypeModel> sellectAll() {
		return rescueTypeModelDao.sellectAll();
	}

}