package com.arf.statistics.dao.impl;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.statistics.dao.IStatisticsPropertyPaymentDao;
import com.arf.statistics.entity.StatisticsPropertyPayment;

@Repository("statisticsPropertyPaymentDao")
public class StatisticsPropertyPaymentDaoImpl extends BaseDaoImpl<StatisticsPropertyPayment, Long> implements IStatisticsPropertyPaymentDao{

}
