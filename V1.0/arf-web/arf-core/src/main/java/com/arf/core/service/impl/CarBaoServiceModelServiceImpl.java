/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;


import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;

import com.arf.core.dao.CarBaoServiceModelDao;
import com.arf.core.dao.BaseDao;
import com.arf.core.entity.CarBaoServiceModel;
import com.arf.core.service.CarBaoServiceModelService;


/**
 * Service - 车保服务类型表
 * 
 * @author arf
 * @version 4.0
 */
@Service("carBaoServiceModelServiceImpl")
public class CarBaoServiceModelServiceImpl extends BaseServiceImpl<CarBaoServiceModel, Long> implements CarBaoServiceModelService {

	@Resource(name = "carBaoServiceModelDaoImpl")
	private CarBaoServiceModelDao carBaoServiceModelDao;

	@Override
	protected BaseDao<CarBaoServiceModel, Long> getBaseDao() {
		return carBaoServiceModelDao;
	}

	@Override
	public List<CarBaoServiceModel> select(int level) {
		return carBaoServiceModelDao.select(level);
	}

}