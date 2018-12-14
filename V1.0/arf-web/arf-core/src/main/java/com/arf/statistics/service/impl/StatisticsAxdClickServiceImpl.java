package com.arf.statistics.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.statistics.dao.IStatisticsAxdClickDao;
import com.arf.statistics.entity.StatisticsAxdClick;
import com.arf.statistics.service.IStatisticsAxdClickService;

@Service("statisticsAxdClickServiceImpl")
public class StatisticsAxdClickServiceImpl extends BaseServiceImpl<StatisticsAxdClick, Long> implements IStatisticsAxdClickService{

	@Resource(name="statisticsAxdClickDaoImpl")
	IStatisticsAxdClickDao statisticsAxdClickDao;
	
	@Override
	protected BaseDao<StatisticsAxdClick, Long> getBaseDao() {
		return statisticsAxdClickDao;
	}

	@Override
	public StatisticsAxdClick findByUserNameDay(String communityNumber, String day, String userName) {
		return statisticsAxdClickDao.findByUserNameDay(communityNumber, day, userName);
	}

}
