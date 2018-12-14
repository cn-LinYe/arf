package com.arf.statistics.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.statistics.dao.IStatisticsParkratePaymentDao;
import com.arf.statistics.entity.StatisticsParkratePayment;
import com.arf.statistics.service.IStatisticsParkratePaymentService;

@Service("statisticsParkratePaymentService")
public class StatisticsParkratePaymentServiceImpl extends BaseServiceImpl<StatisticsParkratePayment, Long> implements IStatisticsParkratePaymentService{

	@Resource(name="statisticsParkratePaymentDao")
	IStatisticsParkratePaymentDao statisticsParkratePaymentDao;
	
	@Override
	protected BaseDao<StatisticsParkratePayment, Long> getBaseDao() {
		return statisticsParkratePaymentDao;
	}

}
