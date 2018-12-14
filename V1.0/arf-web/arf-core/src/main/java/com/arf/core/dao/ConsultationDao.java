/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.dao;

import java.util.List;

import com.arf.core.Filter;
import com.arf.core.Order;
import com.arf.core.Page;
import com.arf.core.Pageable;
import com.arf.core.entity.Admin;
import com.arf.core.entity.Consultation;
import com.arf.core.entity.Goods;
import com.arf.core.entity.Member;

/**
 * Dao - 咨询
 * 
 * @author arf
 * @version 4.0
 */
public interface ConsultationDao extends BaseDao<Consultation, Long> {

	/**
	 * 查找咨询
	 * 
	 * @param member
	 *            会员
	 * @param goods
	 *            货品
	 * @param isShow
	 *            是否显示
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @return 咨询,不包含咨询回复
	 */
	List<Consultation> findList(Member member, Goods goods, Boolean isShow, Integer count, List<Filter> filters, List<Order> orders);

	/**
	 * 查找咨询分页
	 * 
	 * @param member
	 *            会员
	 * @param goods
	 *            货品
	 * @param isShow
	 *            是否显示
	 * @param pageable
	 *            分页信息
	 * @return 咨询分页,不包含咨询回复
	 */
	Page<Consultation> findPage(Member member, Goods goods, Boolean isShow, Pageable pageable);

	/**
	 * 查找咨询数量
	 * 
	 * @param member
	 *            会员
	 * @param goods
	 *            货品
	 * @param isShow
	 *            是否显示
	 * @return 咨询数量,不包含咨询回复
	 */
	Long count(Member member, Goods goods, Boolean isShow);
	/**
	 * 根据子公司查询出所属咨询
	 * @param admin 
	 * 			子公司
	 * @param pageable
	 * 			分页信息
	 * @return 所属咨询
	 */
	Page<Consultation> findPage(Admin admin,Pageable pageable);

}