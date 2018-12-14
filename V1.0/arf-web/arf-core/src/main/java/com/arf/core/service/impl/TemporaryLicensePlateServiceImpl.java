/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.annotation.Resource;

import com.arf.core.dao.TemporaryLicensePlateDao;
import com.arf.core.dao.BaseDao;
import com.arf.core.entity.TemporaryLicensePlate;
import com.arf.core.service.TemporaryLicensePlateService;

/**
 * Service - 临时车牌表
 * 
 * @author arf dg
 * @version 4.0
 */
@Service("temporaryLicensePlateServiceImpl")
public class TemporaryLicensePlateServiceImpl extends BaseServiceImpl<TemporaryLicensePlate, Long> implements TemporaryLicensePlateService {

	@Resource(name = "temporaryLicensePlateDaoImpl")
	private TemporaryLicensePlateDao temporaryLicensePlateDao;

	@Override
	protected BaseDao<TemporaryLicensePlate, Long> getBaseDao() {
		return temporaryLicensePlateDao;
	}

	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public TemporaryLicensePlate selectyByLicensePlate(String licensePlateNumber) {
		return temporaryLicensePlateDao.selectyByLicensePlate(licensePlateNumber);
	}

	@Override
	public List<TemporaryLicensePlate> selectyByState() {
		return temporaryLicensePlateDao.selectyByState();
	}
	/**
     * 获取用户的所有入场车辆
     * @param userName
     * @return
     */
    public List<TemporaryLicensePlate> selectByUserName(String userName){
        return temporaryLicensePlateDao.selectByUserName(userName);
    }

	@Override
	public TemporaryLicensePlate selectByLicensePlateAndCommunityNumber(
			String licensePlate, String communityNumber) {
		return temporaryLicensePlateDao.selectByLicensePlateAndCommunityNumber(licensePlate,communityNumber);
	}
}