/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.dao.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.arf.core.dao.StatisticDao;
import com.arf.core.entity.Statistic;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

/**
 * Dao - 统计
 * 
 * @author arf
 * @version 4.0
 */
@Repository("statisticDaoImpl")
public class StatisticDaoImpl extends BaseDaoImpl<Statistic, Long> implements StatisticDao {

	public List<Statistic> analyze(Statistic.Period period, Date beginDate, Date endDate) {
		Assert.notNull(period);

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Statistic> criteriaQuery = criteriaBuilder.createQuery(Statistic.class);
		Root<Statistic> root = criteriaQuery.from(Statistic.class);
		switch (period) {
		case year:
			criteriaQuery.select(criteriaBuilder.construct(Statistic.class, root.get("year"), criteriaBuilder.sum(root.<Long> get("registerMemberCount")), criteriaBuilder.sum(root.<Long> get("createOrderCount")), criteriaBuilder.sum(root.<Long> get("completeOrderCount")),
					criteriaBuilder.sum(root.<BigDecimal> get("createOrderAmount")), criteriaBuilder.sum(root.<BigDecimal> get("completeOrderAmount"))));
			criteriaQuery.groupBy(root.get("year"));
			break;
		case month:
			criteriaQuery.select(criteriaBuilder.construct(Statistic.class, root.get("year"), root.get("month"), criteriaBuilder.sum(root.<Long> get("registerMemberCount")), criteriaBuilder.sum(root.<Long> get("createOrderCount")), criteriaBuilder.sum(root.<Long> get("completeOrderCount")),
					criteriaBuilder.sum(root.<BigDecimal> get("createOrderAmount")), criteriaBuilder.sum(root.<BigDecimal> get("completeOrderAmount"))));
			criteriaQuery.groupBy(root.get("year"), root.get("month"));
			break;
		case day:
			criteriaQuery.select(criteriaBuilder.construct(Statistic.class, root.get("year"), root.get("month"), root.get("day"), root.<Long> get("registerMemberCount"), root.<Long> get("createOrderCount"), root.<Long> get("completeOrderCount"), root.<BigDecimal> get("createOrderAmount"),
					root.<BigDecimal> get("completeOrderAmount")));
			break;
		}
		Predicate restrictions = criteriaBuilder.conjunction();
		if (beginDate != null) {
			Calendar calendar = DateUtils.toCalendar(beginDate);
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH);
			int day = calendar.get(Calendar.DAY_OF_MONTH);
			restrictions = criteriaBuilder.and(
					restrictions,
					criteriaBuilder.or(criteriaBuilder.greaterThan(root.<Integer> get("year"), year), criteriaBuilder.and(criteriaBuilder.equal(root.<Integer> get("year"), year), criteriaBuilder.greaterThan(root.<Integer> get("month"), month)),
							criteriaBuilder.and(criteriaBuilder.equal(root.<Integer> get("year"), year), criteriaBuilder.equal(root.<Integer> get("month"), month), criteriaBuilder.greaterThanOrEqualTo(root.<Integer> get("day"), day))));
		}
		if (endDate != null) {
			Calendar calendar = DateUtils.toCalendar(endDate);
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH);
			int day = calendar.get(Calendar.DAY_OF_MONTH);
			restrictions = criteriaBuilder.and(
					restrictions,
					criteriaBuilder.or(criteriaBuilder.lessThan(root.<Integer> get("year"), year), criteriaBuilder.and(criteriaBuilder.equal(root.<Integer> get("year"), year), criteriaBuilder.lessThan(root.<Integer> get("month"), month)),
							criteriaBuilder.and(criteriaBuilder.equal(root.<Integer> get("year"), year), criteriaBuilder.equal(root.<Integer> get("month"), month), criteriaBuilder.lessThanOrEqualTo(root.<Integer> get("day"), day))));
		}
		criteriaQuery.where(restrictions);
		return entityManager.createQuery(criteriaQuery).getResultList();
	}


}