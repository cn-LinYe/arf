/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;


import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import com.arf.core.dao.IllegalInformationModelDao;
import com.arf.core.dao.BaseDao;
import com.arf.core.entity.IllegalInformationModel;
import com.arf.core.service.IllegalInformationModelService;


/**
 * Service - 违章信息表
 * 
 * @author arf
 * @version 4.0
 */
@Service("illegalInformationModelServiceImpl")
public class IllegalInformationModelServiceImpl extends BaseServiceImpl<IllegalInformationModel, Long> implements IllegalInformationModelService {

	@Resource(name = "illegalInformationModelDaoImpl")
	private IllegalInformationModelDao illegalInformationModelDao;

	@Override
	protected BaseDao<IllegalInformationModel, Long> getBaseDao() {
		return illegalInformationModelDao;
	}

}