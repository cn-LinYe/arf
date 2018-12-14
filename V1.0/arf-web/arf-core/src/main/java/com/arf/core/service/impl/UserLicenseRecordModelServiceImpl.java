/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;


import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.arf.core.dao.UserLicenseRecordModelDao;
import com.arf.core.dao.BaseDao;
import com.arf.core.entity.UserLicenseRecordModel;
import com.arf.core.service.UserLicenseRecordModelService;


/**
 * Service - 违章查询接口 用户车辆信息Db缓存
 * 
 * @author arf
 * @version 4.0
 */
@Service("userLicenseRecordModelServiceImpl")
public class UserLicenseRecordModelServiceImpl extends BaseServiceImpl<UserLicenseRecordModel, Long> implements UserLicenseRecordModelService {

	@Resource(name = "userLicenseRecordModelDaoImpl")
	private UserLicenseRecordModelDao userLicenseRecordModelDao;

	@Override
	protected BaseDao<UserLicenseRecordModel, Long> getBaseDao() {
		return userLicenseRecordModelDao;
	}

	public List<UserLicenseRecordModel> selectRecords(UserLicenseRecordModel userLicenseRecordModel) {
        return userLicenseRecordModelDao.selectRecords(userLicenseRecordModel);
        
    }

    @Override
    public void initTable() {
        userLicenseRecordModelDao.initTable();
    }

    @Override
    public UserLicenseRecordModel findLicenseRecord(
            String license_plate_number, String his_no) {
        return userLicenseRecordModelDao.findLicenseRecord(license_plate_number, his_no);
    }

    @Override
    public void compareTable() {
        userLicenseRecordModelDao.compareTable();
    }
}