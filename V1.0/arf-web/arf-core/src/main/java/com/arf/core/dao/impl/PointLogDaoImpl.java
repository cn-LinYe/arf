/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.dao.impl;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import com.arf.core.Page;
import com.arf.core.Pageable;
import com.arf.core.dao.PointLogDao;
import com.arf.core.entity.Admin;
import com.arf.core.entity.Member;
import com.arf.core.entity.PointLog;

import org.springframework.stereotype.Repository;

/**
 * Dao - 积分记录
 * 
 * @author arf
 * @version 4.0
 */
@Repository("pointLogDaoImpl")
public class PointLogDaoImpl extends BaseDaoImpl<PointLog, Long> implements PointLogDao {

	public Page<PointLog> findPage(Member member, Pageable pageable) {
		if (member == null) {
			return Page.emptyPage(pageable);
		}
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<PointLog> criteriaQuery = criteriaBuilder.createQuery(PointLog.class);
		Root<PointLog> root = criteriaQuery.from(PointLog.class);
		criteriaQuery.select(root);
		criteriaQuery.where(criteriaBuilder.equal(root.get("member"), member));
		return super.findPage(criteriaQuery, pageable);
	}

	@Override
	public Page<PointLog> findPage(Admin admin,  Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<PointLog> criteriaQuery = criteriaBuilder.createQuery(PointLog.class);
		Root<PointLog> root = criteriaQuery.from(PointLog.class);		 
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		Subquery<Member> subquery = criteriaQuery.subquery(Member.class);
		Root<Member> subqueryRoot = subquery.from(Member.class);
		subquery.select(subqueryRoot);
		subquery.where(criteriaBuilder.equal(subqueryRoot.get("admin"), admin),criteriaBuilder.equal(subqueryRoot.get("id"), root.get("member")));		
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.exists(subquery));	
		criteriaQuery.where(restrictions);	
		return super.findPage(criteriaQuery, pageable);
	}

}