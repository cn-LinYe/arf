package com.arf.statistics.dao.impl;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.statistics.dao.IStatisticsParkratePaymentDao;
import com.arf.statistics.entity.StatisticsParkratePayment;

@Repository("statisticsParkratePaymentDao")
public class StatisticsParkratePaymentDaoImpl extends BaseDaoImpl<StatisticsParkratePayment, Long> implements IStatisticsParkratePaymentDao{

}
