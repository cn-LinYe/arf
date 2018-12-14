package com.arf.statistics.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.statistics.dao.IStatisticsAccessUnlockLogDao;
import com.arf.statistics.entity.StatisticsAccessUnlockLog;
import com.arf.statistics.service.IStatisticsAccessUnlockLogService;

@Service("statisticsAccessUnlockLogServiceImpl")
public class StatisticsAccessUnlockLogServiceImpl extends
		BaseServiceImpl<StatisticsAccessUnlockLog, Long> implements
		IStatisticsAccessUnlockLogService {

	@Resource(name = "statisticsAccessUnlockLogDaoImpl")
	private IStatisticsAccessUnlockLogDao statisticsAccessUnlockLogDaoImpl;
	
	@Override
	protected BaseDao<StatisticsAccessUnlockLog, Long> getBaseDao() {
		return statisticsAccessUnlockLogDaoImpl;
	}

	@Override
	public void batchSave(
			List<StatisticsAccessUnlockLog> statisticsAccessUnlockLogList) {
		if(CollectionUtils.isNotEmpty(statisticsAccessUnlockLogList)){
			Date accessUnlockDate = statisticsAccessUnlockLogList.get(0).getAccessUnlockDate();
			String accessUnlockDateStr = DateFormatUtils.format(accessUnlockDate.getTime(), "yyyy-MM-dd");
			statisticsAccessUnlockLogDaoImpl.detachByAccessUnlockDate(accessUnlockDateStr);
			for (StatisticsAccessUnlockLog statisticsAccessUnlockLog : statisticsAccessUnlockLogList) {
				save(statisticsAccessUnlockLog);
			}
		}
	}

}
