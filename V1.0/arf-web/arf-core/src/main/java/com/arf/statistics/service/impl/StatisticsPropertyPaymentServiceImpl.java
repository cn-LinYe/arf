package com.arf.statistics.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.statistics.dao.IStatisticsPropertyPaymentDao;
import com.arf.statistics.entity.StatisticsPropertyPayment;
import com.arf.statistics.service.IStatisticsPropertyPaymentService;

@Service("statisticsPropertyPaymentService")
public class StatisticsPropertyPaymentServiceImpl extends BaseServiceImpl<StatisticsPropertyPayment, Long> implements IStatisticsPropertyPaymentService{

	@Resource(name="statisticsPropertyPaymentDao")
	IStatisticsPropertyPaymentDao statisticsPropertyPaymentDao;
	
	@Override
	protected BaseDao<StatisticsPropertyPayment, Long> getBaseDao() {
		return statisticsPropertyPaymentDao;
	}

}
