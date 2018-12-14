/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;


import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;

import com.arf.core.dao.CameraCodeModelDao;
import com.arf.core.dao.BaseDao;
import com.arf.core.entity.CameraCodeModel;
import com.arf.core.service.CameraCodeModelService;


/**
 * Service - 摄像机管理
 * 
 * @author arf
 * @version 4.0
 */
@Service("cameraCodeModelServiceImpl")
public class CameraCodeModelServiceImpl extends BaseServiceImpl<CameraCodeModel, Long> implements CameraCodeModelService {

	@Resource(name = "cameraCodeModelDaoImpl")
	private CameraCodeModelDao cameraCodeModelDao;

	@Override
	protected BaseDao<CameraCodeModel, Long> getBaseDao() {
		return cameraCodeModelDao;
	}

	@Override
	public List<CameraCodeModel> selectbycode(String cameraCode) {
		return cameraCodeModelDao.selectbycode(cameraCode);
	}
}