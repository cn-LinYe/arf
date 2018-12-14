/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;


import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.arf.core.Filter;
import com.arf.core.Filter.Operator;
import com.arf.core.Order;
import com.arf.core.dao.BaseDao;
import com.arf.core.dao.ParkRateRecordModelDao;
import com.arf.core.entity.ParkRateRecordModel;
import com.arf.core.service.ParkRateRecordModelService;


/**
 * Service - 月租车
 * 
 * @author arf dg
 * @version 4.0
 */
@Service("parkRateRecordModelServiceImpl")
public class ParkRateRecordModelServiceImpl extends BaseServiceImpl<ParkRateRecordModel, Long> implements ParkRateRecordModelService {

	@Resource(name = "parkRateRecordModelDaoImpl")
	private ParkRateRecordModelDao parkRateRecordModelDao;

	@Override
	protected BaseDao<ParkRateRecordModel, Long> getBaseDao() {
		return parkRateRecordModelDao;
	}

	@Override
	public ParkRateRecordModel selectByouttradeno(String out_trade_no) {
		
		return parkRateRecordModelDao.selectByouttradeno(out_trade_no);
	}

	@Override
	public List<ParkRateRecordModel> findByUserName(String userName) {
		return parkRateRecordModelDao.findByUserName(userName);
	}

	@Override
	public List<ParkRateRecordModel> findTodayRecord(Date startDate, Date endDate,List<String> communityList) {
		return parkRateRecordModelDao.findTodayRecord(startDate, endDate,communityList);
	}

	@Override
	public ParkRateRecordModel findLatestRecord(String licensePlateNumber, String communityNumber,
			int secondsInterval) {
		Date now = new Date();
		Date latest = new Date(now.getTime() - secondsInterval * 1000L);
		List<ParkRateRecordModel> list = this.findList(1, (List<Order>)null, 
					new Filter("licensePlateNumber",Operator.eq,licensePlateNumber),
					new Filter("communityNumber",Operator.eq,communityNumber),
					new Filter("createDate",Operator.ge,latest)
				);
		
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}

}