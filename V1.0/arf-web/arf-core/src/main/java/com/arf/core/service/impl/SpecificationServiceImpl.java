/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;

import javax.annotation.Resource;

import com.arf.core.dao.BaseDao;
import com.arf.core.dao.SpecificationDao;
import com.arf.core.entity.Specification;
import com.arf.core.service.SpecificationService;

import org.springframework.stereotype.Service;

/**
 * Service - 规格
 * 
 * @author arf
 * @version 4.0
 */
@Service("specificationServiceImpl")
public class SpecificationServiceImpl extends BaseServiceImpl<Specification, Long> implements SpecificationService {

	@Resource(name = "specificationDaoImpl")
	private SpecificationDao specificationDao;

	@Override
	protected BaseDao<Specification, Long> getBaseDao() {
		return specificationDao;
	}

}