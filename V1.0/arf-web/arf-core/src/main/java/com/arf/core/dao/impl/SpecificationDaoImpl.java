/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.dao.impl;

import com.arf.core.dao.SpecificationDao;
import com.arf.core.entity.Specification;

import org.springframework.stereotype.Repository;

/**
 * Dao - 规格
 * 
 * @author arf
 * @version 4.0
 */
@Repository("specificationDaoImpl")
public class SpecificationDaoImpl extends BaseDaoImpl<Specification, Long> implements SpecificationDao {

}