/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.dao.impl;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import com.arf.core.dao.ProductDao;
import com.arf.core.entity.Admin;
import com.arf.core.entity.Goods;
import com.arf.core.entity.Goods.Type;
import com.arf.core.entity.Product;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

/**
 * Dao - 商品
 * 
 * @author arf
 * @version 4.0
 */
@Repository("productDaoImpl")
public class ProductDaoImpl extends BaseDaoImpl<Product, Long> implements ProductDao {

	public boolean snExists(String sn) {
		if (StringUtils.isEmpty(sn)) {
			return false;
		}

		String jpql = "select count(*) from Product product where lower(product.sn) = lower(:sn)";
		Long count = entityManager.createQuery(jpql, Long.class).setParameter("sn", sn).getSingleResult();
		return count > 0;
	}

	public Product findBySn(String sn) {
		if (StringUtils.isEmpty(sn)) {
			return null;
		}

		String jpql = "select product from Product product where lower(product.sn) = lower(:sn)";
		try {
			return entityManager.createQuery(jpql, Product.class).setParameter("sn", sn).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<Product> search(Goods.Type type, String keyword, Set<Product> excludes, Integer count) {
		if (StringUtils.isEmpty(keyword)) {
			return Collections.emptyList();
		}

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (type != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("goods").get("type"), type));
		}
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(criteriaBuilder.like(root.<String> get("sn"), "%" + keyword + "%"), criteriaBuilder.like(root.get("goods").<String> get("name"), "%" + keyword + "%")));
		if (CollectionUtils.isNotEmpty(excludes)) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.not(root.in(excludes)));
		}
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, count, null, null);
	}

	@Override
	public List<Product> search(Type type, String keyword, Set<Product> excludes, Integer count, Admin admin) {
		if (StringUtils.isEmpty(keyword)) {
			return Collections.emptyList();
		}

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		
		Subquery<Goods> subquery = criteriaQuery.subquery(Goods.class);
		Root<Goods> subqueryRoot = subquery.from(Goods.class);
		subquery.select(subqueryRoot);
		subquery.where(criteriaBuilder.equal(subqueryRoot.get("admin"), admin),criteriaBuilder.equal(subqueryRoot.get("id"), root.get("goods")));		
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.exists(subquery));	
		
		if (type != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("goods").get("type"), type));
		}
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(criteriaBuilder.like(root.<String> get("sn"), "%" + keyword + "%"), criteriaBuilder.like(root.get("goods").<String> get("name"), "%" + keyword + "%")));
		if (CollectionUtils.isNotEmpty(excludes)) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.not(root.in(excludes)));
		}
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, count, null, null);
	}

}