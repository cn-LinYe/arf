/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;


import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import com.arf.core.dao.ParkRateTimeDao;
import com.arf.core.dao.BaseDao;
import com.arf.core.entity.ParkRateTime;
import com.arf.core.service.ParkRateTimeService;


/**
 * Service - 月租车
 * 
 * @author arf dg
 * @version 4.0
 */
@Service("parkRateTimeServiceImpl")
public class ParkRateTimeServiceImpl extends BaseServiceImpl<ParkRateTime, Long> implements ParkRateTimeService {

	@Resource(name = "parkRateTimeDaoImpl")
	private ParkRateTimeDao parkRateTimeDao;

	@Override
	protected BaseDao<ParkRateTime, Long> getBaseDao() {
		return parkRateTimeDao;
	}
	
	

}