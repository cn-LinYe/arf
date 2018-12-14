/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.dao.impl;

import com.arf.core.dao.ParameterDao;
import com.arf.core.entity.Parameter;

import org.springframework.stereotype.Repository;

/**
 * Dao - 参数
 * 
 * @author arf
 * @version 4.0
 */
@Repository("parameterDaoImpl")
public class ParameterDaoImpl extends BaseDaoImpl<Parameter, Long> implements ParameterDao {

}