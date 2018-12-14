/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;


import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import com.arf.core.dao.RodeRescueModelDao;
import com.arf.core.dao.BaseDao;
import com.arf.core.entity.RodeRescueModel;
import com.arf.core.service.RodeRescueModelService;


/**
 * Service - 道路救援信息表
 * 
 * @author arf
 * @version 4.0
 */
@Service("rodeRescueModelServiceImpl")
public class RodeRescueModelServiceImpl extends BaseServiceImpl<RodeRescueModel, Long> implements RodeRescueModelService {

	@Resource(name = "rodeRescueModelDaoImpl")
	private RodeRescueModelDao rodeRescueModelDao;

	@Override
	protected BaseDao<RodeRescueModel, Long> getBaseDao() {
		return rodeRescueModelDao;
	}

}