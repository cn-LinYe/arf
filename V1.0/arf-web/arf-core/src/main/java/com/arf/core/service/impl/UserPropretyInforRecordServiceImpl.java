/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;


import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;

import com.arf.core.dao.UserPropretyInforRecordDao;
import com.arf.core.dao.BaseDao;
import com.arf.core.entity.UserPropretyInforRecord;
import com.arf.core.service.UserPropretyInforRecordService;


/**
 * Service - 用户物业信息记录
 * 
 * @author arf liaotao
 * @version 4.0
 */
@Service("userPropretyInforRecordServiceImpl")
public class UserPropretyInforRecordServiceImpl extends BaseServiceImpl<UserPropretyInforRecord, Long> implements UserPropretyInforRecordService {

	@Resource(name = "userPropretyInforRecordDaoImpl")
	private UserPropretyInforRecordDao userPropretyInforRecordDao;

	@Override
	protected BaseDao<UserPropretyInforRecord, Long> getBaseDao() {
		return userPropretyInforRecordDao;
	}

	@Override
	public UserPropretyInforRecord selectByHouseId(String user_name,String houseId) {
		return userPropretyInforRecordDao.selectByHouseId(user_name,houseId);
	}

	@Override
	public List<UserPropretyInforRecord> selectByUsername(String user_name) {
		return userPropretyInforRecordDao.selectByUsername(user_name);
	}

}