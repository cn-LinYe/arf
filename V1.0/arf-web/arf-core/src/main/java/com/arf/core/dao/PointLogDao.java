/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.dao;

import com.arf.core.Page;
import com.arf.core.Pageable;
import com.arf.core.entity.Admin;
import com.arf.core.entity.Member;
import com.arf.core.entity.PointLog;

/**
 * Dao - 积分记录
 * 
 * @author arf
 * @version 4.0
 */
public interface PointLogDao extends BaseDao<PointLog, Long> {

	/**
	 * 查找积分记录分页
	 * 
	 * @param member
	 *            会员
	 * @param pageable
	 *            分页信息
	 * @return 积分记录分页
	 */
	Page<PointLog> findPage(Member member, Pageable pageable);
	/**
	 * 根据子公司获取所属商户的积分记录
	 * @param admin
	 * 			子公司
	 * @param member
	 * 			商户
	 * @param pageable
	 * 			分页信息
	 * @return
	 */
	Page<PointLog> findPage(Admin admin, Pageable pageable);

}