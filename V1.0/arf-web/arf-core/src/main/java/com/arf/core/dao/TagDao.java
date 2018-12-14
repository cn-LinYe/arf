/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.dao;

import java.util.List;

import com.arf.core.entity.Tag;
import com.arf.core.entity.Tag.Type;

/**
 * Dao - 标签
 * 
 * @author arf
 * @version 4.0
 */
public interface TagDao extends BaseDao<Tag, Long> {

	/**
	 * 查找标签
	 * 
	 * @param type
	 *            类型
	 * @return 标签
	 */
	List<Tag> findList(Type type);

}