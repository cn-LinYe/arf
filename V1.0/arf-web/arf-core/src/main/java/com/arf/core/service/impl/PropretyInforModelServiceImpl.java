/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;


import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;

import com.arf.core.dao.PropretyInforModelDao;
import com.arf.core.dao.BaseDao;
import com.arf.core.entity.PropretyInforModel;
import com.arf.core.service.PropretyInforModelService;


/**
 * Service - 楼栋信息
 * 
 * @author arf liaotao
 * @version 4.0
 */
@Service("propretyInforModelServiceImpl")
public class PropretyInforModelServiceImpl extends BaseServiceImpl<PropretyInforModel, Long> implements PropretyInforModelService {

	@Resource(name = "propretyInforModelDaoImpl")
	private PropretyInforModelDao propretyInforModelDao;

	@Override
	protected BaseDao<PropretyInforModel, Long> getBaseDao() {
		return propretyInforModelDao;
	}


	@Override
	public List<PropretyInforModel> selectCommunityHouse(String communityNumber) {
		return propretyInforModelDao.selectCommunityHouse(communityNumber);
	}


	@Override
	public PropretyInforModel selectByHouseId(String houseId) {
		return propretyInforModelDao.selectByHouseId(houseId);
	}


	@Override
	public List<PropretyInforModel> selectByComBuild(String communityNumber, String buildingNumber) {
		return propretyInforModelDao.selectByComBuild(communityNumber, buildingNumber);
	}


	@Override
	public List<PropretyInforModel> selectByComBuildFloor(String communityNumber, String buildingNumber,
			String floorNumber) {
		return propretyInforModelDao.selectByComBuildFloor(communityNumber, buildingNumber, floorNumber);
	}


	@Override
	public List<PropretyInforModel> selectByUsernameAndCommunity(String user_name, String communityNumber) {
		return propretyInforModelDao.selectByUsernameAndCommunity(user_name, communityNumber);
	}


	@Override
	public List<PropretyInforModel> selectByUsername(String user_name) {
		return propretyInforModelDao.selectByUsername(user_name);
	}


}