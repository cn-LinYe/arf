/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.LockModeType;

import com.arf.base.AppLog;
import com.arf.core.Filter;
import com.arf.core.HQLResultConvert;
import com.arf.core.Order;
import com.arf.core.Page;
import com.arf.core.Pageable;
import com.arf.core.entity.BaseEntity;

/**
 * Dao - 基类
 * 
 * @author arf
 * @version 4.0
 */
public interface BaseDao<T extends BaseEntity<ID>, ID extends Serializable> {

	/**
	 * 查找实体对象
	 * 
	 * @param id
	 *            ID
	 * @return 实体对象，若不存在则返回null
	 */
	T find(ID id);

	/**
	 * 查找实体对象
	 * 
	 * @param id
	 *            ID
	 * @param lockModeType
	 *            锁定方式
	 * @return 实体对象，若不存在则返回null
	 */
	T find(ID id, LockModeType lockModeType);

	/**
	 * 查找实体对象集合
	 * 
	 * @param first
	 *            起始记录
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @return 实体对象集合
	 */
	List<T> findList(Integer first, Integer count, List<Filter> filters, List<Order> orders);

	/**
	 * 查找实体对象分页
	 * 
	 * @param pageable
	 *            分页信息
	 * @return 实体对象分页
	 */
	Page<T> findPage(Pageable pageable);

	/**
	 * 查询实体对象数量
	 * 
	 * @param filters
	 *            筛选
	 * @return 实体对象数量
	 */
	long count(Filter... filters);

	/**
	 * 持久化实体对象
	 * 
	 * @param entity
	 *            实体对象
	 */
	void persist(T entity);

	/**
	 * 合并实体对象
	 * 
	 * @param entity
	 *            实体对象
	 * @return 实体对象
	 */
	T merge(T entity);

	/**
	 * 移除实体对象
	 * 
	 * @param entity
	 *            实体对象
	 */
	void remove(T entity);

	/**
	 * 刷新实体对象
	 * 
	 * @param entity
	 *            实体对象
	 */
	void refresh(T entity);

	/**
	 * 刷新实体对象
	 * 
	 * @param entity
	 *            实体对象
	 * @param lockModeType
	 *            锁定方式
	 */
	void refresh(T entity, LockModeType lockModeType);

	/**
	 * 获取实体对象ID
	 * 
	 * @param entity
	 *            实体对象
	 * @return 实体对象ID
	 */
	ID getIdentifier(T entity);

	/**
	 * 判断实体对象是否已加载
	 * 
	 * @param entity
	 *            实体对象
	 * @return 实体对象是否已加载
	 */
	boolean isLoaded(T entity);

	/**
	 * 判断实体对象属性是否已加载
	 * 
	 * @param entity
	 *            实体对象
	 * @param attributeName
	 *            属性名称
	 * @return 实体对象属性是否已加载
	 */
	boolean isLoaded(T entity, String attributeName);

	/**
	 * 判断是否为托管状态
	 * 
	 * @param entity
	 *            实体对象
	 * @return 是否为托管状态
	 */
	boolean isManaged(T entity);

	/**
	 * 设置为游离状态
	 * 
	 * @param entity
	 *            实体对象
	 */
	void detach(T entity);

	/**
	 * 锁定实体对象
	 * 
	 * @param entity
	 *            实体对象
	 * @param lockModeType
	 *            锁定方式
	 */
	void lock(T entity, LockModeType lockModeType);

	/**
	 * 清除缓存
	 */
	void clear();

	/**
	 * 同步数据
	 */
	void flush();

	/**
	 * 通过Hql查询结果集
	 * @param hql  hql语句
	 * @param countHql  查询总条数的hql
	 * @param pageable  分页对象
	 * @param values    参数值
	 * @param convert   转换数据的接口
	 * @return
	 */
	public Page<T> findPageByHQL(String hql, String countHql,Pageable pageable,List<Object> values,HQLResultConvert<T> convert);
	/**
	 * 通过sql查询结果集
	 * @param sql
	 * @param pageable
	 * @param values
	 * @param convert
	 * @return
	 */
	Page<T> findPageBySQL(String sql,Pageable pageable,List<Object> values,HQLResultConvert<T> convert);
	/**
	 * 保存app日志 已废弃
	 * @see {@link com.arf.core.dao.BaseDao.saveAppLogs(AppLog)}
	 * @param tablename
	 * @param community_number
	 * @param username
	 * @param type
	 * @param licenseplate
	 * @param descript
	 * @param version
	 * @param deviceType
	 * @param deviceNumber
	 * @param propertyNumber
	 * @param branchId
	 */
	@Deprecated
    void saveAppLogs(String tablename,String community_number,String username,AppLog.LogType type,String licenseplate,String descript,
    		String version,String deviceType,String deviceNumber,Integer propertyNumber,Integer branchId);
    
    /**
	 *  保存app操作日志
	 * @param appLog applog_yyyyMM表的实体
	 */
	void saveAppLogs(AppLog appLog);
	
	/**
	 * 判断表是不是存在
	 * @param tableName
	 * @return
	 */
	boolean existTable(String tableName);
	
	/**
	 * 复制sourceTable的表结构到新的表newTable
	 * @param sourceTable
	 * @param newTable
	 * @return
	 */
	boolean copyTable(String sourceTable,String newTable);
	
	/**
	 * 修改表存储引擎
	 * @param table
	 * @param engine
	 * @return
	 */
	boolean changeEngine(String table,String engine);
}