/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.arf.core.Filter;
import com.arf.core.Page;
import com.arf.core.Pageable;
import com.arf.core.entity.Admin;
import com.arf.core.entity.Goods;
import com.arf.core.entity.Member;
import com.arf.core.entity.Order;

/**
 * Dao - 订单
 * 
 * @author arf
 * @version 4.0
 */
public interface OrderDao extends BaseDao<Order, Long> {
	
	
	/**
	 * 根据编号查找订单
	 * 
	 * @param sn
	 *            编号(忽略大小写)
	 * @return 订单，若不存在则返回null
	 */
	Order findBySn(String sn);

	/**
	 * 查找订单
	 * 
	 * @param type
	 *            类型
	 * @param status
	 *            状态
	 * @param member
	 *            会员
	 * @param isPendingReceive
	 *            是否等待收款
	 * @param isPendingRefunds
	 *            是否等待退款
	 * @param isAllocatedStock
	 *            是否已分配库存
	 * @param hasExpired
	 *            是否已过期
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @return 订单
	 */
	List<Order> findList(Order.Type type, Order.Status status, Member member, Boolean isPendingReceive, Boolean isPendingRefunds, Boolean isAllocatedStock, Boolean hasExpired, Integer count, List<Filter> filters, List<com.arf.core.Order> orders);

	/**
	 * 查找订单分页
	 * 
	 * @param type
	 *            类型
	 * @param status
	 *            状态
	 * @param member
	 *            会员
	 * @param isPendingReceive
	 *            是否等待收款
	 * @param isPendingRefunds
	 *            是否等待退款
	 * @param isAllocatedStock
	 *            是否已分配库存
	 * @param hasExpired
	 *            是否已过期
	 * @param pageable
	 *            分页信息
	 * @return 订单分页
	 */
	Page<Order> findPage(Order.Type type, Order.Status status, Member member, Boolean isPendingReceive, Boolean isPendingRefunds, Boolean isAllocatedStock, Boolean hasExpired, Pageable pageable);

	/**
	 * 查找订单分页
	 * 
	 * @param type
	 *            类型
	 * @param status
	 *            状态
	 * @param admin
	 *            管理员
	 * @param isPendingReceive
	 *            是否等待收款
	 * @param isPendingRefunds
	 *            是否等待退款
	 * @param isAllocatedStock
	 *            是否已分配库存
	 * @param hasExpired
	 *            是否已过期
	 * @param pageable
	 *            分页信息
	 * @return 订单分页
	 */
	Page<Order> findPageFilterOrder(Order.Type type, Order.Status status, Admin admin, Boolean isPendingReceive, Boolean isPendingRefunds, Boolean isAllocatedStock, Boolean hasExpired, Pageable pageable);

	/**
	 * 查询订单数量
	 * 
	 * @param type
	 *            类型
	 * @param status
	 *            状态
	 * @param member
	 *            会员
	 * @param goods
	 *            货品
	 * @param isPendingReceive
	 *            是否等待收款
	 * @param isPendingRefunds
	 *            是否等待退款
	 * @param isAllocatedStock
	 *            是否已分配库存
	 * @param hasExpired
	 *            是否已过期
	 * @return 订单数量
	 */
	Long count(Order.Type type, Order.Status status, Member member, Goods goods, Boolean isPendingReceive, Boolean isPendingRefunds, Boolean isAllocatedStock, Boolean hasExpired);
	
	/**
	 * 筛选订单数量
	 * 
	 * @param type
	 *            类型
	 * @param status
	 *            状态
	 * @param admin
	 *            管理员
	 * @param goods
	 *            货品
	 * @param isPendingReceive
	 *            是否等待收款
	 * @param isPendingRefunds
	 *            是否等待退款
	 * @param isAllocatedStock
	 *            是否已分配库存
	 * @param hasExpired
	 *            是否已过期
	 * @return 订单数量
	 */
	Long countFilterOrder(Order.Type type, Order.Status status, Admin admin, Goods goods, Boolean isPendingReceive, Boolean isPendingRefunds, Boolean isAllocatedStock, Boolean hasExpired);

	/**
	 * 查询订单创建数
	 * 
	 * @param beginDate
	 *            起始日期
	 * @param endDate
	 *            结束日期
	 * @return 订单创建数
	 */
	Long createOrderCount(Date beginDate, Date endDate);

	/**
	 * 查询订单完成数
	 * 
	 * @param beginDate
	 *            起始日期
	 * @param endDate
	 *            结束日期
	 * @return 订单完成数
	 */
	Long completeOrderCount(Date beginDate, Date endDate);

	/**
	 * 查询订单创建金额
	 * 
	 * @param beginDate
	 *            起始日期
	 * @param endDate
	 *            结束日期
	 * @return 订单创建金额
	 */
	BigDecimal createOrderAmount(Date beginDate, Date endDate);

	/**
	 * 查询订单完成金额
	 * 
	 * @param beginDate
	 *            起始日期
	 * @param endDate
	 *            结束日期
	 * @return 订单完成金额
	 */
	BigDecimal completeOrderAmount(Date beginDate, Date endDate);
	
	Page<Order> findPagebiz(Order.Type type, Order.Status status, Member member, Boolean isPendingReceive, Boolean isPendingRefunds, Boolean isAllocatedStock, Boolean hasExpired, Pageable pageable);
	
	/**
	 * 获取订单数量
	 * @param sql
	 * @return
	 */
	public List<Map<String,Object>> getOrderSize(String sql);
	
	/***
	 * 获取未评论的最小的订单id
	 * @param goodId
	 * @param memberId
	 * @return
	 */
	public Long getNoReviewOrderId(Long goodId,Long memberId);
}