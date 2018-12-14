/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.dao.impl;

import com.arf.core.dao.AdDao;
import com.arf.core.entity.Ad;

import org.springframework.stereotype.Repository;

/**
 * Dao - 广告
 * 
 * @author arf
 * @version 4.0
 */
@Repository("adDaoImpl")
public class AdDaoImpl extends BaseDaoImpl<Ad, Long> implements AdDao {

}