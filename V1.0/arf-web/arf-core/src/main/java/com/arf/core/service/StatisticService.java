/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service;

import java.util.Date;
import java.util.List;

import com.arf.core.entity.Statistic;

/**
 * Service - 统计
 * 
 * @author arf
 * @version 4.0
 */
public interface StatisticService extends BaseService<Statistic, Long> {

	/**
	 * 收集
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @param day
	 *            日
	 * @return 统计
	 */
	Statistic collect(int year, int month, int day);

	/**
	 * 分析
	 * 
	 * @param period
	 *            周期
	 * @param beginDate
	 *            起始日期
	 * @param endDate
	 *            结束日期
	 * @return 统计
	 */
	List<Statistic> analyze(Statistic.Period period, Date beginDate, Date endDate);

}