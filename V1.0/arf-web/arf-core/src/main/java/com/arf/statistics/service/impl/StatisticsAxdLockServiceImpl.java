package com.arf.statistics.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.statistics.dao.IStatisticsAxdLockDao;
import com.arf.statistics.entity.StatisticsAxdLock;
import com.arf.statistics.service.IStatisticsAxdLockService;

@Service("statisticsAxdLockService")
public class StatisticsAxdLockServiceImpl extends BaseServiceImpl<StatisticsAxdLock, Long> implements IStatisticsAxdLockService{

	@Resource(name="statisticsAxdLockDao")
	IStatisticsAxdLockDao statisticsAxdLockDao;
	
	@Override
	protected BaseDao<StatisticsAxdLock, Long> getBaseDao() {
		return statisticsAxdLockDao;
	}

}
