/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.dao.impl;

import com.arf.core.Page;
import com.arf.core.Pageable;
import com.arf.core.dao.StockLogDao;
import com.arf.core.entity.Admin;
import com.arf.core.entity.StockLog;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

/**
 * Dao - 库存记录
 * 
 * @author arf
 * @version 4.0
 */
@Repository("stockLogDaoImpl")
public class StockLogDaoImpl extends BaseDaoImpl<StockLog, Long> implements StockLogDao {

	@Override
	public Page<StockLog> findPage(Admin admin, Pageable pageable) {
		String sql="SELECT * from xx_stock_log sto where EXISTS (SELECT pro.id from xx_product pro where  EXISTS (SELECT g.id from xx_goods g where  g.admin=:admin and pro.goods=g.id) and sto.product = pro.id )";
		if(pageable.getSearchProperty()!=null && pageable.getSearchValue()!=null){
			if(pageable.getSearchProperty().equals("operator")){
				//搜索条件是管理员
				sql="SELECT * from xx_stock_log sto where EXISTS (SELECT pro.id from xx_product pro where  EXISTS (SELECT g.id from xx_goods g where  g.admin=:admin and pro.goods=g.id) and sto.product = pro.id ) and sto.operator LIKE :operator";
			}else{
				sql="SELECT * from xx_stock_log sto where EXISTS (SELECT pro.id from xx_product pro where  EXISTS (SELECT g.id from xx_goods g where  g.admin=:admin and pro.goods=g.id) and sto.product = pro.id and pro.sn LIKE :sn)";
			}
		}		
		Query query = entityManager.createNativeQuery(sql,StockLog.class);
		Query query1 = entityManager.createNativeQuery(sql,StockLog.class);
		query.setParameter("admin", admin.getId());		
		query1.setParameter("admin", admin.getId());
		if(pageable.getSearchProperty()!=null && pageable.getSearchValue()!=null){
			if(pageable.getSearchProperty().equals("operator")){
				//搜索条件是管理员	
				query.setParameter("operator", "%"+pageable.getSearchValue()+"%");		
				query1.setParameter("operator", "%"+pageable.getSearchValue()+"%");
			}else{
				query.setParameter("sn", "%"+pageable.getSearchValue()+"%");		
				query1.setParameter("sn", "%"+pageable.getSearchValue()+"%");
			}
		}
		query.setFirstResult((pageable.getPageNumber() - 1) * pageable.getPageSize());
		query.setMaxResults(pageable.getPageSize());
		
		List<StockLog> list= query1.getResultList();
		long total = list.size();
		return new Page<StockLog>(query.getResultList(), total, pageable);
	}

}