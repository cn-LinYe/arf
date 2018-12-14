package com.arf.statistics.service;

import java.util.List;

import com.arf.core.service.BaseService;
import com.arf.statistics.entity.StatisticsAccessUnlockLog;

public interface IStatisticsAccessUnlockLogService extends
		BaseService<StatisticsAccessUnlockLog, Long> {

	void batchSave(List<StatisticsAccessUnlockLog> statisticsAccessUnlockLogList);

}
