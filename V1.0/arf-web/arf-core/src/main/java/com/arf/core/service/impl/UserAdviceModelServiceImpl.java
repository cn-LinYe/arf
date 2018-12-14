/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;


import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;

import com.arf.core.dao.UserAdviceModelDao;
import com.arf.core.dao.BaseDao;
import com.arf.core.entity.UserAdviceModel;
import com.arf.core.service.UserAdviceModelService;


/**
 * Service - 用户意见反馈
 *
 * @author arf
 * @version 4.0
 */
@Service("userAdviceModelServiceImpl")
public class UserAdviceModelServiceImpl extends BaseServiceImpl<UserAdviceModel, Long> implements UserAdviceModelService {

	@Resource(name = "userAdviceModelDaoImpl")
	private UserAdviceModelDao userAdviceModelDao;

	@Override
	protected BaseDao<UserAdviceModel, Long> getBaseDao() {
		return userAdviceModelDao;
	}



}