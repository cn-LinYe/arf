/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.dao;

import com.arf.core.entity.Log;

/**
 * Dao - 日志
 * 
 * @author arf
 * @version 4.0
 */
public interface LogDao extends BaseDao<Log, Long> {

	/**
	 * 删除所有日志
	 */
	void removeAll();

}