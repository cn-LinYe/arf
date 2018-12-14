/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.arf.core.Page;
import com.arf.core.Pageable;
import com.arf.core.dao.AdminDao;
import com.arf.core.entity.Admin;
import com.arf.core.entity.Admin.Type;

/**
 * Dao - 管理员
 * 
 * @author arf
 * @version 4.0
 */
@Repository("adminDaoImpl")
public class AdminDaoImpl extends BaseDaoImpl<Admin, Long> implements AdminDao {

	public boolean usernameExists(String username) {
		if (StringUtils.isEmpty(username)) {
			return false;
		}
		String jpql = "select count(*) from Admin admin where lower(admin.username) = lower(:username)";
		Long count = entityManager.createQuery(jpql, Long.class).setParameter("username", username).getSingleResult();
		return count > 0;
	}

	public Admin findByUsername(String username) {
		if (StringUtils.isEmpty(username)) {
			return null;
		}
		try {
			String jpql = "select admin from Admin admin where lower(admin.username) = lower(:username)";
			return entityManager.createQuery(jpql, Admin.class).setParameter("username", username).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}


	@Override
	public Page<Admin> findPage(Admin.Type type, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Admin> criteriaQuery = criteriaBuilder.createQuery(Admin.class);
		Root<Admin> root = criteriaQuery.from(Admin.class);
		criteriaQuery.select(root);
		if (type != null) {
			criteriaQuery.where(criteriaBuilder.equal(root.get("type"), type));
		}
		return super.findPage(criteriaQuery, pageable);
	}

	@Override
	public List<Admin> findByType(Type type) {		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Admin> criteriaQuery = criteriaBuilder.createQuery(Admin.class);
		Root<Admin> root = criteriaQuery.from(Admin.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (type != null) {
			restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("type"), type));
		}
		restrictions=criteriaBuilder.and(restrictions,criteriaBuilder.equal(root.get("isEnabled"), true));
		criteriaQuery.where(restrictions);
		return  entityManager.createQuery(criteriaQuery).getResultList();
	}

	@Override
	public Admin selectAdmin(String userName, String documentCode) {
		if (StringUtils.isEmpty(userName)) {
			return null;
		}
		try {
			String jpql = "select admin from Admin admin where lower(admin.username) = lower(:username) and admin.documentCode = :documentCode";
			return entityManager.createQuery(jpql, Admin.class).setParameter("username", userName).setParameter("documentCode", documentCode).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}