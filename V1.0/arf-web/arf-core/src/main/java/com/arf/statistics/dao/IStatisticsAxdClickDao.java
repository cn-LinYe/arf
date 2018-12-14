package com.arf.statistics.dao;

import com.arf.core.dao.BaseDao;
import com.arf.statistics.entity.StatisticsAxdClick;

public interface IStatisticsAxdClickDao extends BaseDao<StatisticsAxdClick, Long>{
	/**
	 * 查看同一个人同一个天在一个小区点击数
	 * @param communityNumber
	 * @param day
	 * @param userName
	 * @return
	 */
	StatisticsAxdClick findByUserNameDay(String communityNumber,String day,String userName);
}
