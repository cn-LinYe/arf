package com.arf.core.dao;

import java.util.List;

import com.arf.core.entity.Area;
import com.arf.core.entity.AreaStatistic;
/**
 * Dao - 地区统计 
 * 
 * @author arf
 * @version 4.0
 */
public interface AreaStatisticDao extends BaseDao<Area, Long> {
	
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
