package com.arf.statistics.dao.impl;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.statistics.dao.IStatisticsAxdLockDao;
import com.arf.statistics.entity.StatisticsAxdLock;

@Repository("statisticsAxdLockDao")
public class StatisticsAxdLockDaoImpl extends BaseDaoImpl<StatisticsAxdLock, Long> implements IStatisticsAxdLockDao{

}
