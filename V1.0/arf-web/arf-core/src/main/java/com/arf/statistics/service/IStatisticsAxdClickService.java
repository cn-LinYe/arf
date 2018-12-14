package com.arf.statistics.service;

import com.arf.core.service.BaseService;
import com.arf.statistics.entity.StatisticsAxdClick;

public interface IStatisticsAxdClickService extends BaseService<StatisticsAxdClick, Long>{

	/**
	 * 查看同一个人同一个天在一个小区点击数
	 * @param communityNumber
	 * @param day
	 * @param userName
	 * @return
	 */
	StatisticsAxdClick findByUserNameDay(String communityNumber,String day,String userName);
}
