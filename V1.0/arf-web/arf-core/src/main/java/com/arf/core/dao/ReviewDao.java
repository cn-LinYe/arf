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
import com.arf.core.entity.Goods;
import com.arf.core.entity.Member;
import com.arf.core.entity.Review;
import com.arf.core.entity.Review.Type;

/**
 * Dao - 评论
 * 
 * @author arf
 * @version 4.0
 */
public interface ReviewDao extends BaseDao<Review, Long> {

	/**
	 * 查找评论
	 * 
	 * @param member
	 *            会员
	 * @param goods
	 *            货品
	 * @param type
	 *            类型
	 * @param isShow
	 *            是否显示
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @return 评论
	 */
	List<Review> findList(Member member, Goods goods, Type type, Boolean isShow, Integer count, List<Filter> filters, List<Order> orders);

	/**
	 * 查找评论分页
	 * 
	 * @param member
	 *            会员
	 * @param goods
	 *            货品
	 * @param type
	 *            类型
	 * @param isShow
	 *            是否显示
	 * @param pageable
	 *            分页信息
	 * @return 评论分页
	 */
	Page<Review> findPage(Member member, Goods goods, Type type, Boolean isShow, Pageable pageable);
	

	/**
	 * 查找评论数量
	 * 
	 * @param member
	 *            会员
	 * @param goods
	 *            货品
	 * @param type
	 *            类型
	 * @param isShow
	 *            是否显示
	 * @return 评论数量
	 */
	Long count(Member member, Goods goods, Type type, Boolean isShow);

	/**
	 * 计算货品总评分
	 * 
	 * @param goods
	 *            货品
	 * @return 货品总评分，仅计算显示评论
	 */
	long calculateTotalScore(Goods goods);

	/**
	 * 计算货品评分次数
	 * 
	 * @param goods
	 *            货品
	 * @return 货品评分次数，仅计算显示评论
	 */
	long calculateScoreCount(Goods goods);
	
	/**
	 * 根据子公司 查询说所属的评论
	 * @param admin
	 * 			子公司
	 * @param type
	 * 			类型
	 * @param pageable
	 * 			分页信息
	 * @return 评论集合
	 */
	Page<Review> findPage( Admin admin,Type type,  Pageable pageable);

}