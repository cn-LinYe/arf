package com.arf.statistics.dao.impl;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.statistics.dao.IStatisticsAccessUnlockLogDao;
import com.arf.statistics.entity.StatisticsAccessUnlockLog;

@Repository("statisticsAccessUnlockLogDaoImpl")
public class StatisticsAccessUnlockLogDaoImpl extends
		BaseDaoImpl<StatisticsAccessUnlockLog, Long> implements
		IStatisticsAccessUnlockLogDao {

	@Override
	public void detachByAccessUnlockDate(String accessUnlockDateStr) {
		StringBuffer hql = new StringBuffer("delete from access_unlock_log_statistic");
		hql.append(" where 1 = 1");
		hql.append(" and access_unlock_date >= :accessUnlockDateStart");
		hql.append(" and access_unlock_date <= :accessUnlockDateEnd");
		Query query = entityManager.createNativeQuery(hql.toString());
		query.setParameter("accessUnlockDateStart", accessUnlockDateStr + " 00:00:00");
		query.setParameter("accessUnlockDateEnd", accessUnlockDateStr + " 23:59:59");
		query.executeUpdate();
	}


}
