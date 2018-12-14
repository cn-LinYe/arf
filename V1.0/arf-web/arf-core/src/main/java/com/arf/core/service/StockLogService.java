/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service;

import com.arf.core.Page;
import com.arf.core.Pageable;
import com.arf.core.entity.Admin;
import com.arf.core.entity.StockLog;

/**
 * Service - 库存记录
 * 
 * @author arf
 * @version 4.0
 */
public interface StockLogService extends BaseService<StockLog, Long> {
	/**
	 * 根据Admin （总公司或者子公司）查询库存记录分页
	 * @param admin
	 * 			子公司
	 * @param pageable
	 * 			分页信息
	 * @return
	 * 		库存记录分页集合
	 */
	Page<StockLog> findPage(Admin admin,Pageable pageable);

}