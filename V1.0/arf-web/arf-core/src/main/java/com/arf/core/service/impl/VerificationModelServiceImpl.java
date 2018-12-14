/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.annotation.Resource;

import com.arf.core.dao.VerificationModelDao;
import com.arf.core.dao.BaseDao;
import com.arf.core.entity.VerificationModel;
import com.arf.core.service.VerificationModelService;


/**
 * Service - 验证码存储表
 * 
 * @author arf
 * @version 4.0
 */
@Service("verificationModelServiceImpl")
public class VerificationModelServiceImpl extends BaseServiceImpl<VerificationModel, Long> implements VerificationModelService {

	@Resource(name = "verificationModelDaoImpl")
	private VerificationModelDao verificationModelDao;

	@Override
	protected BaseDao<VerificationModel, Long> getBaseDao() {
		return verificationModelDao;
	}
	@Transactional(readOnly = true)
	public List<VerificationModel> checkByPhone(String phone){
		return verificationModelDao.checkByPhone(phone);
	}
	
	

}