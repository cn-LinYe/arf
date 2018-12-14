package com.arf.statistics.dao;

import com.arf.core.dao.BaseDao;
import com.arf.statistics.entity.StatisticsAccessUnlockLog;

public interface IStatisticsAccessUnlockLogDao extends
		BaseDao<StatisticsAccessUnlockLog, Long> {

	/**
	 * 
	 * @param accessUnlockDateStr "yyyy-MM-dd"
	 */
	void detachByAccessUnlockDate(String accessUnlockDateStr);

}
