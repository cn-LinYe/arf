package com.arf.core.service;


import java.util.List;

import com.arf.core.entity.AreaStatistic;

/**
 * Service - 地区统计
 * 
 * @author arf
 * @version 4.0
 */
public interface AreaStatisticService  {
	/**
	 * 获取地区统计数据：会员总数
	 * @param 
	 * @return 会员总数
	 */
	List<AreaStatistic> findMemcount();
	/**
	 * 获取地区统计数据：订单总数
	 * @param 
	 * @return 订单总数
	 */
	List<AreaStatistic> findArdcount();
	/**
	 * 获取地区统计数据：销量最高数
	 * @param 
	 * @return 销量最高数
	 */
	List<AreaStatistic> findSalesvolume();
}
