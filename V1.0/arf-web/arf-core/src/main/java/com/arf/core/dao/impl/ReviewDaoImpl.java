/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.dao.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import com.arf.core.Filter;
import com.arf.core.Order;
import com.arf.core.Page;
import com.arf.core.Pageable;
import com.arf.core.dao.ReviewDao;
import com.arf.core.entity.Admin;
import com.arf.core.entity.Goods;
import com.arf.core.entity.Member;
import com.arf.core.entity.Review;
import com.arf.core.entity.Review.Type;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

/**
 * Dao - 评论
 * 
 * @author arf
 * @version 4.0
 */
@Repository("reviewDaoImpl")
public class ReviewDaoImpl extends BaseDaoImpl<Review, Long> implements ReviewDao {

	public List<Review> findList(Member member, Goods goods, Type type, Boolean isShow, Integer count, List<Filter> filters, List<Order> orders) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Review> criteriaQuery = criteriaBuilder.createQuery(Review.class);
		Root<Review> root = criteriaQuery.from(Review.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (member != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"), member));
		}
		if (goods != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("goods"), goods));
		}
		if (type != null) {
			switch (type) {
			case positive:
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.ge(root.<Number> get("score"), 4));
				break;
			case moderate:
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.<Number> get("score"), 3));
				break;
			case negative:
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.le(root.<Number> get("score"), 2));
				break;
			}
		}
		if (isShow != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isShow"), isShow));
		}
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, count, filters, orders);
	}

	public Page<Review> findPage(Member member, Goods goods, Type type, Boolean isShow, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Review> criteriaQuery = criteriaBuilder.createQuery(Review.class);
		Root<Review> root = criteriaQuery.from(Review.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (member != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"), member));
		}
		if (goods != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("goods"), goods));
		}
		if (type != null) {
			switch (type) {
			case positive:
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.ge(root.<Number> get("score"), 4));
				break;
			case moderate:
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.<Number> get("score"), 3));
				break;
			case negative:
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.le(root.<Number> get("score"), 2));
				break;
			}
		}
		if (isShow != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isShow"), isShow));
		}
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}

	public Long count(Member member, Goods goods, Type type, Boolean isShow) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Review> criteriaQuery = criteriaBuilder.createQuery(Review.class);
		Root<Review> root = criteriaQuery.from(Review.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (member != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"), member));
		}
		if (goods != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("goods"), goods));
		}
		if (type != null) {
			switch (type) {
			case positive:
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.ge(root.<Number> get("score"), 4));
				break;
			case moderate:
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.<Number> get("score"), 3));
				break;
			case negative:
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.le(root.<Number> get("score"), 2));
				break;
			}
		}
		if (isShow != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isShow"), isShow));
		}
		criteriaQuery.where(restrictions);
		return super.count(criteriaQuery, null);
	}

	public long calculateTotalScore(Goods goods) {
		Assert.notNull(goods);
		String jpql = "select sum(review.score) from Review review where review.goods = :goods and review.isShow = :isShow";
		Long totalScore = entityManager.createQuery(jpql, Long.class).setParameter("goods", goods).setParameter("isShow", true).getSingleResult();
		return totalScore != null ? totalScore : 0L;
	}

	public long calculateScoreCount(Goods goods) {
		Assert.notNull(goods);

		String jpql = "select count(*) from Review review where review.goods = :goods and review.isShow = :isShow";
		return entityManager.createQuery(jpql, Long.class).setParameter("goods", goods).setParameter("isShow", true).getSingleResult();
	}

	@Override
	public Page<Review> findPage(Admin admin, Type type, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Review> criteriaQuery = criteriaBuilder.createQuery(Review.class);
		Root<Review> root = criteriaQuery.from(Review.class);		 
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		Subquery<Goods> subquery = criteriaQuery.subquery(Goods.class);
		Root<Goods> subqueryRoot = subquery.from(Goods.class);
		subquery.select(subqueryRoot);
		subquery.where(criteriaBuilder.equal(subqueryRoot.get("admin"), admin),criteriaBuilder.equal(subqueryRoot.get("id"), root.get("goods")));		
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.exists(subquery));	
		criteriaQuery.where(restrictions);	
		return super.findPage(criteriaQuery, pageable);
	}

}