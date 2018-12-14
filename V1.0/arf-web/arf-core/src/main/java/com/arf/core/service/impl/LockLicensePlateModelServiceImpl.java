/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;


import java.util.List;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import com.arf.core.dao.LockLicensePlateModelDao;
import com.arf.core.dao.BaseDao;
import com.arf.core.entity.LockLicensePlateModel;
import com.arf.core.service.LockLicensePlateModelService;


/**
 * Service - 车牌控制表(用来锁定车牌)
 * 
 * @author arf
 * @version 4.0
 */
@Service("lockLicensePlateModelServiceImpl")
public class LockLicensePlateModelServiceImpl extends BaseServiceImpl<LockLicensePlateModel, Long> implements LockLicensePlateModelService {

	@Resource(name = "lockLicensePlateModelDaoImpl")
	private LockLicensePlateModelDao lockLicensePlateModelDao;

	@Override
	protected BaseDao<LockLicensePlateModel, Long> getBaseDao() {
		return lockLicensePlateModelDao;
	}

	@Override
	public LockLicensePlateModel selectLicense(String license) {
		return lockLicensePlateModelDao.selectLicense(license);
	}
	/**
     * 根据小区编号和车牌号码获取锁车对象
     * @return
     */
    public List<LockLicensePlateModel> selectLicenseByParm(String communityNumber,String licensePlateNumber){
        return lockLicensePlateModelDao.selectLicenseByParm(communityNumber, licensePlateNumber);
    }

    /**
     * 根据小区编号和车牌号码获取锁车对象
     * @return
     */
    public List<LockLicensePlateModel> selectLicenseByParm(String communityNumber,String licensePlateNumber,Integer license_plate_command){
        return lockLicensePlateModelDao.selectLicenseByParm(communityNumber, licensePlateNumber, license_plate_command);
    }
	@Override
	public List<LockLicensePlateModel> selectLicenseAxd(String license) {
		return lockLicensePlateModelDao.selectLicenseAxd(license);
	}

	@Override
	public LockLicensePlateModel selectLicenseByLock(String communityNumber, String licensePlateNumber,
			Integer license_plate_command) {
		// TODO Auto-generated method stub
		return lockLicensePlateModelDao.selectLicenseByLock(communityNumber, licensePlateNumber, license_plate_command);
	}

}
















