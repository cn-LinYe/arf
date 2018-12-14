/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.dao.impl;

import com.arf.core.dao.RoleDao;
import com.arf.core.entity.Role;

import org.springframework.stereotype.Repository;

/**
 * Dao - 角色
 * 
 * @author arf
 * @version 4.0
 */
@Repository("roleDaoImpl")
public class RoleDaoImpl extends BaseDaoImpl<Role, Long> implements RoleDao {

}