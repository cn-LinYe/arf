/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;


import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import com.arf.core.dao.HungFundModelDao;
import com.arf.core.dao.BaseDao;
import com.arf.core.entity.HungFundModel;
import com.arf.core.service.HungFundModelService;


/**
 * Service - 弘基金表
 * 
 * @author arf
 * @version 4.0
 */
@Service("hungFundModelServiceImpl")
public class HungFundModelServiceImpl extends BaseServiceImpl<HungFundModel, Long> implements HungFundModelService {

	@Resource(name = "hungFundModelDaoImpl")
	private HungFundModelDao hungFundModelDao;

	@Override
	protected BaseDao<HungFundModel, Long> getBaseDao() {
		return hungFundModelDao;
	}


}