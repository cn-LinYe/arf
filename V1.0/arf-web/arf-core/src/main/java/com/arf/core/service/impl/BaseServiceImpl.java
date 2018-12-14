/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;
import com.arf.base.AppLog;
import com.arf.core.Filter;
import com.arf.core.Order;
import com.arf.core.Page;
import com.arf.core.Pageable;
import com.arf.core.dao.BaseDao;
import com.arf.core.entity.BaseEntity;
import com.arf.core.entity.CommunityModel;
import com.arf.core.entity.Member;
import com.arf.core.service.BaseService;
import com.arf.core.service.CommunityModelService;
import com.arf.core.service.MemberService;
import com.arf.core.util.MStringUntils;

/**
 * Service - 基类
 * 
 * @author arf
 * @version 4.0
 */
@Transactional
public abstract class BaseServiceImpl<T extends BaseEntity<ID>, ID extends Serializable> implements BaseService<T, ID> {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	/** 更新忽略属性 */
	private static final String[] UPDATE_IGNORE_PROPERTIES = new String[] { BaseEntity.CREATE_DATE_PROPERTY_NAME, BaseEntity.MODIFY_DATE_PROPERTY_NAME, BaseEntity.VERSION_PROPERTY_NAME };

	@Resource(name = "communityModelServiceImpl")
	private CommunityModelService communityModelService;
	
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	
	
	/**
	 * 获取Dao实现类
	 * 
	 * @return Dao实现类
	 */
	protected abstract BaseDao<T, ID> getBaseDao();

	@Override
	@Transactional(readOnly = true)
	public T find(ID id) {
		if (id==null) {
			return null;
		}
		return getBaseDao().find(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<T> findAll() {
		return findList(null, null, null, null);
	}

	@Override
	@Transactional(readOnly = true)
	public List<T> findList(ID... ids) {
		List<T> result = new ArrayList<T>();
		if (ids != null) {
			for (ID id : ids) {
				T entity = find(id);
				if (entity != null) {
					result.add(entity);
				}
			}
		}
		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public List<T> findList(Integer count, List<Filter> filters, List<Order> orders) {
		return findList(null, count, filters, orders);
	}
	
	@Transactional(readOnly = true)
	public List<T> findList(Integer count, List<Order> orders,Filter ...filters) {
		List<Filter> fs = null;
		if(filters != null && filters.length > 0){
			fs = Arrays.asList(filters);
		}
		return findList(null, count, fs, orders);
	}

	@Override
	@Transactional(readOnly = true)
	public List<T> findList(Integer first, Integer count, List<Filter> filters, List<Order> orders) {
		return getBaseDao().findList(first, count, filters, orders);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<T> findPage(Pageable pageable) {
		return getBaseDao().findPage(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public long count() {
		return count(new Filter[] {});
	}
	
	@Override
	@Transactional(readOnly = true)
	public long count(Filter... filters) {
		return getBaseDao().count(filters);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean exists(ID id) {
		return getBaseDao().find(id) != null;
	}

	@Override
	@Transactional(readOnly = true)
	public boolean exists(Filter... filters) {
		return getBaseDao().count(filters) > 0;
	}

	@Override
	@Transactional
	public T save(T entity) {
		Assert.notNull(entity);
		Assert.isTrue(entity.isNew());

		getBaseDao().persist(entity);
		return entity;
	}

//	@Transactional
	@Override
	public T update(T entity) {
		Assert.notNull(entity);
		Assert.isTrue(!entity.isNew());

		if (!getBaseDao().isManaged(entity)) {
			T persistant = getBaseDao().find(getBaseDao().getIdentifier(entity));
			if (persistant != null) {
				copyProperties(entity, persistant, UPDATE_IGNORE_PROPERTIES);
			}
			return persistant;
		}
		return entity;
	}

	@Override
	@Transactional
	public T update(T entity, String... ignoreProperties) {
		Assert.notNull(entity);
		Assert.isTrue(!entity.isNew());
		Assert.isTrue(!getBaseDao().isManaged(entity));

		T persistant = getBaseDao().find(getBaseDao().getIdentifier(entity));
		if (persistant != null) {
			copyProperties(entity, persistant, (String[]) ArrayUtils.addAll(ignoreProperties, UPDATE_IGNORE_PROPERTIES));
		}
		return update(persistant);
	}

	@Override
	@Transactional
	public void delete(ID id) {
		delete(getBaseDao().find(id));
	}

	@Override
	@Transactional
	public void delete(ID... ids) {
		if (ids != null) {
			for (ID id : ids) {
				delete(getBaseDao().find(id));
			}
		}
	}

	@Override
	@Transactional
	public void delete(T entity) {
		if (entity != null) {
			getBaseDao().remove(getBaseDao().isManaged(entity) ? entity : getBaseDao().merge(entity));
		}
	}

	protected void copyProperties(T source, T target, String... ignoreProperties) {
		Assert.notNull(source);
		Assert.notNull(target);

		PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(target);
		for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
			String propertyName = propertyDescriptor.getName();
			Method readMethod = propertyDescriptor.getReadMethod();
			Method writeMethod = propertyDescriptor.getWriteMethod();
			if (ArrayUtils.contains(ignoreProperties, propertyName) || readMethod == null || writeMethod == null || !getBaseDao().isLoaded(source, propertyName)) {
				continue;
			}
			try {
				Object sourceValue = readMethod.invoke(source);
				writeMethod.invoke(target, sourceValue);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e.getMessage(), e);
			} catch (IllegalArgumentException e) {
				throw new RuntimeException(e.getMessage(), e);
			} catch (InvocationTargetException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
	}

	/**
     * 保存app日志 已废弃
	 * @see {@link com.arf.core.service.impl.BaseServiceImpl.saveAppLogs(AppLog)}
     * @param tablename
     * @param community_number
     * @param username
     * @param type
     * @param descript
     */
	@Override
	@Deprecated
    public void saveAppLogs(String tablename,String community_number,String username,AppLog.LogType type,String licenseplate,String descript,
    		String version,String deviceType,String deviceNumber){
    	Integer propertyNumber = null;
    	Integer branchId = null;
    	if(MStringUntils.isNotEmptyOrNull(community_number)){
	    	List<CommunityModel> communityList = communityModelService.checkByCommunity_number(community_number);
	    	if(communityList != null && communityList.size()>0){
	    		propertyNumber = communityList.get(0).getProperty_number();
	    		branchId = communityList.get(0).getBranchId();
	    	}
    	}
        getBaseDao().saveAppLogs(tablename, community_number, username, type,licenseplate,descript,version,deviceType,deviceNumber,propertyNumber,branchId);
    }
    
    @Override
	public void saveAppLogs(AppLog appLog){
    	if(appLog == null){
    		logger.error("记录app_log时出现AppLog=null的方法非法调用");
    		return;
    	}
    	try{
    		Member user = null;
			if(appLog.getCommunityNumber() == null || appLog.getPropertyNumber() == null
					|| appLog.getBranchId() == null){
				user = memberService.findByUsername(appLog.getUserName());
				if(user != null && appLog.getCommunityNumber() == null){
	    			appLog.setCommunityNumber(user.getCommunityNumber());
	    		}
	    		
	    		if(user != null && appLog.getPropertyNumber() == null){
	    			appLog.setPropertyNumber(user.getPropertyNumber());
	    		}
	    		if(user != null && appLog.getBranchId() == null){
	    			appLog.setBranchId(user.getBranchId());
	    		}
			}
    		
    		this.getBaseDao().saveAppLogs(appLog);
    	}catch(Exception e){
    		logger.error("记录app_log时出现异常,AppLog="+JSON.toJSONString(appLog),e);
    	}
    }
    
    @Override
	public boolean existTable(String tableName){
    	return this.getBaseDao().existTable(tableName);
    }
    
    @Override
	public boolean copyTable(String sourceTable,String newTable){
    	return this.getBaseDao().copyTable(sourceTable, newTable);
    }
}