/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service;

import com.arf.core.Page;
import com.arf.core.Pageable;
import com.arf.core.entity.Area;
import com.arf.core.entity.FreightConfig;
import com.arf.core.entity.ShippingMethod;

/**
 * Service - 运费配置
 * 
 * @author arf
 * @version 4.0
 */
public interface FreightConfigService extends BaseService<FreightConfig, Long> {

	/**
	 * 判断运费配置是否存在
	 * 
	 * @param shippingMethod
	 *            配送方式
	 * @param area
	 *            地区
	 * @return 运费配置是否存在
	 */
	boolean exists(ShippingMethod shippingMethod, Area area);

	/**
	 * 判断运费配置是否唯一
	 * 
	 * @param shippingMethod
	 *            配送方式
	 * @param previousArea
	 *            修改前地区
	 * @param currentArea
	 *            当前地区
	 * @return 运费配置是否唯一
	 */
	boolean unique(ShippingMethod shippingMethod, Area previousArea, Area currentArea);

	/**
	 * 查找运费配置分页
	 * 
	 * @param shippingMethod
	 *            配送方式
	 * @param pageable
	 *            分页信息
	 * @return 运费配置分页
	 */
	Page<FreightConfig> findPage(ShippingMethod shippingMethod, Pageable pageable);

}