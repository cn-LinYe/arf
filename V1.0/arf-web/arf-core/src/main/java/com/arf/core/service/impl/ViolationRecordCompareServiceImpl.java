/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;


import java.util.List;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import com.arf.core.dao.ViolationRecordCompareDao;
import com.arf.core.dao.BaseDao;
import com.arf.core.entity.ViolationRecordCompare;
import com.arf.core.service.ViolationRecordCompareService;


/**
 * Service - 用于比较的违章记录类
 * 
 * @author arf
 * @version 4.0
 */
@Service("violationRecordCompareServiceImpl")
public class ViolationRecordCompareServiceImpl extends BaseServiceImpl<ViolationRecordCompare, Long> implements ViolationRecordCompareService {

	@Resource(name = "violationRecordCompareDaoImpl")
	private ViolationRecordCompareDao violationRecordCompareDao;

	@Override
	protected BaseDao<ViolationRecordCompare, Long> getBaseDao() {
		return violationRecordCompareDao;
	}
	/**
     * 根据用户名查询违章记录比较信息
     * @param username
     * @return
     */
    public List<ViolationRecordCompare> findListByUserName(String username){
        return violationRecordCompareDao.findListByUserName(username);
    }
}