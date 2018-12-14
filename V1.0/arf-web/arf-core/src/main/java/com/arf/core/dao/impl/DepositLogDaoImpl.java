/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.dao.impl;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.arf.core.Page;
import com.arf.core.Pageable;
import com.arf.core.dao.DepositLogDao;
import com.arf.core.entity.DepositLog;
import com.arf.core.entity.Member;

import org.springframework.stereotype.Repository;

/**
 * Dao - 预存款记录
 * 
 * @author arf
 * @version 4.0
 */
@Repository("depositLogDaoImpl")
public class DepositLogDaoImpl extends BaseDaoImpl<DepositLog, Long> implements DepositLogDao {

	public Page<DepositLog> findPage(Member member, Pageable pageable) {
		if (member == null) {
			return Page.emptyPage(pageable);
		}
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<DepositLog> criteriaQuery = criteriaBuilder.createQuery(DepositLog.class);
		Root<DepositLog> root = criteriaQuery.from(DepositLog.class);
		criteriaQuery.select(root);
		criteriaQuery.where(criteriaBuilder.equal(root.get("member"), member));
		return super.findPage(criteriaQuery, pageable);
	}

}