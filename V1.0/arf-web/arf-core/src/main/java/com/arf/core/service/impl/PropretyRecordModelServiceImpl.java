/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;


import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import com.arf.core.dao.PropretyRecordModelDao;
import com.arf.core.dao.BaseDao;
import com.arf.core.entity.PropretyRecordModel;
import com.arf.core.service.PropretyRecordModelService;


/**
 * Service - 物业费订单
 * 
 * @author arf dg
 * @version 4.0
 */
@Service("propretyRecordModelServiceImpl")
public class PropretyRecordModelServiceImpl extends BaseServiceImpl<PropretyRecordModel, Long> implements PropretyRecordModelService {

	@Resource(name = "propretyRecordModelDaoImpl")
	private PropretyRecordModelDao propretyRecordModelDao;

	@Override
	protected BaseDao<PropretyRecordModel, Long> getBaseDao() {
		return propretyRecordModelDao;
	}

	@Override
	public PropretyRecordModel selectByouttradeno(String out_trade_no) {
		return propretyRecordModelDao.selectByouttradeno(out_trade_no);
	}

	@Override
	public PropretyRecordModel selectByHouseId(String HouseId) {
		// TODO Auto-generated method stub
		return propretyRecordModelDao.selectByHouseId(HouseId);
	}
	
	

}