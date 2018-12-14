/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;


import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import com.arf.core.dao.RParkingrecordDao;
import com.arf.core.dao.BaseDao;
import com.arf.core.entity.RParkingrecord;
import com.arf.core.service.RParkingrecordService;


/**
 * Service - RParkingrecord表
 * 
 * @author arf dg
 * @version 4.0
 */
@Service("RParkingrecordServiceImpl")
public class RParkingrecordServiceImpl extends BaseServiceImpl<RParkingrecord, Long> implements RParkingrecordService {

	@Resource(name = "RParkingrecordDaoImpl")
	private RParkingrecordDao RParkingrecordDao;

	@Override
	protected BaseDao<RParkingrecord, Long> getBaseDao() {
		return RParkingrecordDao;
	}

}