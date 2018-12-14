/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.axdshopkeeper.entity.BizChannel;
import com.arf.axdshopkeeper.entity.BizChannelRefBranch;
import com.arf.core.dao.AdministrativeModelDao;
import com.arf.core.dao.BaseDao;
import com.arf.core.entity.AdministrativeModel;
import com.arf.core.service.AdministrativeModelService;


/**
 * Service - 管理员基本信息
 * 
 * @author arf
 * @version 4.0
 */
@Service("administrativeModelServiceImpl")
public class AdministrativeModelServiceImpl extends BaseServiceImpl<AdministrativeModel, Long> implements AdministrativeModelService {

	@Resource(name = "administrativeModelDaoImpl")
	private AdministrativeModelDao administrativeModelDao;

	@Override
	protected BaseDao<AdministrativeModel, Long> getBaseDao() {
		return administrativeModelDao;
	}

	@Override
	public List<AdministrativeModel> sellectByLevelCityNo(Integer cityNo) {
		return administrativeModelDao.sellectByLevelCityNo(cityNo);
	}

	@Override
	public AdministrativeModel sellectByPropretyNumber(Integer property_number) {
		return administrativeModelDao.sellectByPropretyNumber(property_number);
	}

	@Override
	public AdministrativeModel sellectByBranchId(Integer branch_id) {
		return administrativeModelDao.sellectByBranchId(branch_id);
	}

	@Override
	public BizChannelRefBranch findChannelRefByBranchId(String branchId) {
		return administrativeModelDao.findChannelRefByBranchId(branchId);
	}

	@Override
	public BizChannel findChannelByChannelnum(String bizChannelNumber) {
		return administrativeModelDao.findChannelByChannelnum(bizChannelNumber);
	}
}