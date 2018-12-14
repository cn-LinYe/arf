/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service;

import java.util.List;

import com.arf.core.entity.Area;

/**
 * Service - 地区
 * 
 * @author arf
 * @version 4.0
 */
public interface AreaService extends BaseService<Area, Long> {

	/**
	 * 查找顶级地区
	 * 
	 * @return 顶级地区
	 */
	List<Area> findRoots();

	/**
	 * 查找顶级地区
	 * 
	 * @param count
	 *            数量
	 * @return 顶级地区
	 */
	List<Area> findRoots(Integer count);

	/**
	 * 查找上级地区
	 * 
	 * @param area
	 *            地区
	 * @param recursive
	 *            是否递归
	 * @param count
	 *            数量
	 * @return 上级地区
	 */
	List<Area> findParents(Area area, boolean recursive, Integer count);

	/**
	 * 查找下级地区
	 * 
	 * @param area
	 *            地区
	 * @param recursive
	 *            是否递归
	 * @param count
	 *            数量
	 * @return 下级地区
	 */
	List<Area> findChildren(Area area, boolean recursive, Integer count);
	/**
	 * 根据地区邮编查找地区
	 * @param areaNo  地区邮编
	 * @return
	 */
	Area findByAreaNo(String areaNo);
	
	/**
	 * 查询市级行政地区
	 * @param province 省名 eg.广东省
	 * @param city 市名 eg.深圳市 如果该市为直辖市, 则省市同名,eg.北京市 北京市
	 * @return
	 */
	Area findByProvinceCityName(String province, String city);
}