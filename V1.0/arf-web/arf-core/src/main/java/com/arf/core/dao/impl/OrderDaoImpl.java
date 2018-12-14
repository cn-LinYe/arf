/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import com.arf.core.Filter;
import com.arf.core.Page;
import com.arf.core.Pageable;
import com.arf.core.dao.OrderDao;
import com.arf.core.entity.Admin;
import com.arf.core.entity.Goods;
import com.arf.core.entity.Member;
import com.arf.core.entity.Order;
import com.arf.core.entity.OrderItem;
import com.arf.core.entity.PaymentMethod;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

/**
 * Dao - 订单
 * 
 * @author arf
 * @version 4.0
 */
@Repository("orderDaoImpl")
public class OrderDaoImpl extends BaseDaoImpl<Order, Long> implements OrderDao {

	public Order findBySn(String sn) {
		if (StringUtils.isEmpty(sn)) {
			return null;
		}
		String jpql = "select orders from Order orders where lower(orders.sn) = lower(:sn)";
		try {
			return entityManager.createQuery(jpql, Order.class).setParameter("sn", sn).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<Order> findList(Order.Type type, Order.Status status, Member member, Boolean isPendingReceive, Boolean isPendingRefunds, Boolean isAllocatedStock, Boolean hasExpired, Integer count, List<Filter> filters, List<com.arf.core.Order> orders) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
		Root<Order> root = criteriaQuery.from(Order.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (type != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("type"), type));
		}
		if (status != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("status"), status));
		}
		if (member != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"), member));
		}
		if (isPendingReceive != null) {
			Predicate predicate = criteriaBuilder.and(criteriaBuilder.or(root.get("expire").isNull(), criteriaBuilder.greaterThan(root.<Date> get("expire"), new Date())), criteriaBuilder.equal(root.get("paymentMethodType"), PaymentMethod.Type.cashOnDelivery),
					criteriaBuilder.notEqual(root.get("status"), Order.Status.completed), criteriaBuilder.notEqual(root.get("status"), Order.Status.failed), criteriaBuilder.notEqual(root.get("status"), Order.Status.canceled), criteriaBuilder.notEqual(root.get("status"), Order.Status.denied),
					criteriaBuilder.lessThan(root.<BigDecimal> get("amountPaid"), root.<BigDecimal> get("amount")));
			if (isPendingReceive) {
				restrictions = criteriaBuilder.and(restrictions, predicate);
			} else {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.not(predicate));
			}
		}
		if (isPendingRefunds != null) {
			Predicate predicate = criteriaBuilder.or(
					criteriaBuilder.and(
							criteriaBuilder.or(criteriaBuilder.and(root.get("expire").isNotNull(), criteriaBuilder.lessThanOrEqualTo(root.<Date> get("expire"), new Date())), criteriaBuilder.equal(root.get("status"), Order.Status.failed),
									criteriaBuilder.equal(root.get("status"), Order.Status.canceled), criteriaBuilder.equal(root.get("status"), Order.Status.denied)), criteriaBuilder.greaterThan(root.<BigDecimal> get("amountPaid"), BigDecimal.ZERO)),
					criteriaBuilder.and(criteriaBuilder.equal(root.get("status"), Order.Status.completed), criteriaBuilder.greaterThan(root.<BigDecimal> get("amountPaid"), root.<BigDecimal> get("amount"))));
			if (isPendingRefunds) {
				restrictions = criteriaBuilder.and(restrictions, predicate);
			} else {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.not(predicate));
			}
		}
		if (isAllocatedStock != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isAllocatedStock"), isAllocatedStock));
		}
		if (hasExpired != null) {
			if (hasExpired) {
				restrictions = criteriaBuilder.and(restrictions, root.get("expire").isNotNull(), criteriaBuilder.lessThanOrEqualTo(root.<Date> get("expire"), new Date()));
			} else {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(root.get("expire").isNull(), criteriaBuilder.greaterThan(root.<Date> get("expire"), new Date())));
			}
		}
		criteriaQuery.where(restrictions);
		return super.findList(criteriaQuery, null, count, filters, orders);
	}

	public Page<Order> findPage(Order.Type type, Order.Status status, Member member, Boolean isPendingReceive, Boolean isPendingRefunds, Boolean isAllocatedStock, Boolean hasExpired, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
		Root<Order> root = criteriaQuery.from(Order.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (type != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("type"), type));
		}
		if (status != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("status"), status));
		}
		if (member != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"), member));
		}
		restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(root.get("memberdel").isNull(),criteriaBuilder.equal(root.get("memberdel"),false)));
		if (isPendingReceive != null) {
			Predicate predicate = criteriaBuilder.and(criteriaBuilder.or(root.get("expire").isNull(), criteriaBuilder.greaterThan(root.<Date> get("expire"), new Date())), criteriaBuilder.equal(root.get("paymentMethodType"), PaymentMethod.Type.cashOnDelivery),
					criteriaBuilder.notEqual(root.get("status"), Order.Status.completed), criteriaBuilder.notEqual(root.get("status"), Order.Status.failed), criteriaBuilder.notEqual(root.get("status"), Order.Status.canceled), criteriaBuilder.notEqual(root.get("status"), Order.Status.denied),
					criteriaBuilder.lessThan(root.<BigDecimal> get("amountPaid"), root.<BigDecimal> get("amount")));
			if (isPendingReceive) {
				restrictions = criteriaBuilder.and(restrictions, predicate);
			} else {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.not(predicate));
			}
		}
		if (isPendingRefunds != null) {
			Predicate predicate = criteriaBuilder.or(
					criteriaBuilder.and(
							criteriaBuilder.or(criteriaBuilder.and(root.get("expire").isNotNull(), criteriaBuilder.lessThanOrEqualTo(root.<Date> get("expire"), new Date())), criteriaBuilder.equal(root.get("status"), Order.Status.failed),
									criteriaBuilder.equal(root.get("status"), Order.Status.canceled), criteriaBuilder.equal(root.get("status"), Order.Status.denied)), criteriaBuilder.greaterThan(root.<BigDecimal> get("amountPaid"), BigDecimal.ZERO)),
					criteriaBuilder.and(criteriaBuilder.equal(root.get("status"), Order.Status.completed), criteriaBuilder.greaterThan(root.<BigDecimal> get("amountPaid"), root.<BigDecimal> get("amount"))));
			if (isPendingRefunds) {
				restrictions = criteriaBuilder.and(restrictions, predicate);
			} else {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.not(predicate));
			}
		}
		if (isAllocatedStock != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isAllocatedStock"), isAllocatedStock));
		}
		if (hasExpired != null) {
			if (hasExpired) {
				restrictions = criteriaBuilder.and(restrictions, root.get("expire").isNotNull(), criteriaBuilder.lessThanOrEqualTo(root.<Date> get("expire"), new Date()));
			} else {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(root.get("expire").isNull(), criteriaBuilder.greaterThan(root.<Date> get("expire"), new Date())));
			}
		}
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}
	
	public Page<Order> findPageFilterOrder(Order.Type type, Order.Status status,Admin admin,Boolean isPendingReceive, Boolean isPendingRefunds, Boolean isAllocatedStock, Boolean hasExpired, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
		Root<Order> root = criteriaQuery.from(Order.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (type != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("type"), type));
		}
		if (status != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("status"), status));
		}
		if(Admin.Type.admin!=admin.getType()){
			Subquery<Member> subquery = criteriaQuery.subquery(Member.class);
			Root<Member> subqueryRoot = subquery.from(Member.class);
			subquery.select(subqueryRoot);
			subquery.where(criteriaBuilder.equal(subqueryRoot.get("admin"), admin),criteriaBuilder.equal(subqueryRoot.get("id"), root.get("memberBiz")));		
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.exists(subquery));
		}
		if (isPendingReceive != null) {
			Predicate predicate = criteriaBuilder.and(criteriaBuilder.or(root.get("expire").isNull(), criteriaBuilder.greaterThan(root.<Date> get("expire"), new Date())), criteriaBuilder.equal(root.get("paymentMethodType"), PaymentMethod.Type.cashOnDelivery),
					criteriaBuilder.notEqual(root.get("status"), Order.Status.completed), criteriaBuilder.notEqual(root.get("status"), Order.Status.failed), criteriaBuilder.notEqual(root.get("status"), Order.Status.canceled), criteriaBuilder.notEqual(root.get("status"), Order.Status.denied),
					criteriaBuilder.lessThan(root.<BigDecimal> get("amountPaid"), root.<BigDecimal> get("amount")));
			if (isPendingReceive) {
				restrictions = criteriaBuilder.and(restrictions, predicate);
			} else {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.not(predicate));
			}
		}
		if (isPendingRefunds != null) {
			Predicate predicate = criteriaBuilder.or(
					criteriaBuilder.and(
							criteriaBuilder.or(criteriaBuilder.and(root.get("expire").isNotNull(), criteriaBuilder.lessThanOrEqualTo(root.<Date> get("expire"), new Date())), criteriaBuilder.equal(root.get("status"), Order.Status.failed),
									criteriaBuilder.equal(root.get("status"), Order.Status.canceled), criteriaBuilder.equal(root.get("status"), Order.Status.denied)), criteriaBuilder.greaterThan(root.<BigDecimal> get("amountPaid"), BigDecimal.ZERO)),
					criteriaBuilder.and(criteriaBuilder.equal(root.get("status"), Order.Status.completed), criteriaBuilder.greaterThan(root.<BigDecimal> get("amountPaid"), root.<BigDecimal> get("amount"))));
			if (isPendingRefunds) {
				restrictions = criteriaBuilder.and(restrictions, predicate);
			} else {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.not(predicate));
			}
		}
		if (isAllocatedStock != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isAllocatedStock"), isAllocatedStock));
		}
		if (hasExpired != null) {
			if (hasExpired) {
				restrictions = criteriaBuilder.and(restrictions, root.get("expire").isNotNull(), criteriaBuilder.lessThanOrEqualTo(root.<Date> get("expire"), new Date()));
			} else {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(root.get("expire").isNull(), criteriaBuilder.greaterThan(root.<Date> get("expire"), new Date())));
			}
		}
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}

	public Long count(Order.Type type, Order.Status status, Member member, Goods goods, Boolean isPendingReceive, Boolean isPendingRefunds, Boolean isAllocatedStock, Boolean hasExpired) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
		Root<Order> root = criteriaQuery.from(Order.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (type != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("type"), type));
		}
		if (status != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("status"), status));
		}
		if (member != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("member"), member));
		}
		if (goods != null) {
			Subquery<OrderItem> subquery = criteriaQuery.subquery(OrderItem.class);
			Root<OrderItem> subqueryRoot = subquery.from(OrderItem.class);
			subquery.select(subqueryRoot);
			subquery.where(criteriaBuilder.equal(subqueryRoot.get("order"), root), criteriaBuilder.equal(subqueryRoot.get("product").get("goods"), goods));
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.exists(subquery));
		}
		if (isPendingReceive != null) {
			Predicate predicate = criteriaBuilder.and(criteriaBuilder.or(root.get("expire").isNull(), criteriaBuilder.greaterThan(root.<Date> get("expire"), new Date())), criteriaBuilder.equal(root.get("paymentMethodType"), PaymentMethod.Type.cashOnDelivery),
					criteriaBuilder.notEqual(root.get("status"), Order.Status.completed), criteriaBuilder.notEqual(root.get("status"), Order.Status.failed), criteriaBuilder.notEqual(root.get("status"), Order.Status.canceled), criteriaBuilder.notEqual(root.get("status"), Order.Status.denied),
					criteriaBuilder.lessThan(root.<BigDecimal> get("amountPaid"), root.<BigDecimal> get("amount")));
			if (isPendingReceive) {
				restrictions = criteriaBuilder.and(restrictions, predicate);
			} else {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.not(predicate));
			}
		}
		if (isPendingRefunds != null) {
			Predicate predicate = criteriaBuilder.or(
					criteriaBuilder.and(
							criteriaBuilder.or(criteriaBuilder.and(root.get("expire").isNotNull(), criteriaBuilder.lessThanOrEqualTo(root.<Date> get("expire"), new Date())), criteriaBuilder.equal(root.get("status"), Order.Status.failed),
									criteriaBuilder.equal(root.get("status"), Order.Status.canceled), criteriaBuilder.equal(root.get("status"), Order.Status.denied)), criteriaBuilder.greaterThan(root.<BigDecimal> get("amountPaid"), BigDecimal.ZERO)),
					criteriaBuilder.and(criteriaBuilder.equal(root.get("status"), Order.Status.completed), criteriaBuilder.greaterThan(root.<BigDecimal> get("amountPaid"), root.<BigDecimal> get("amount"))));
			if (isPendingRefunds) {
				restrictions = criteriaBuilder.and(restrictions, predicate);
			} else {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.not(predicate));
			}
		}
		if (isAllocatedStock != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isAllocatedStock"), isAllocatedStock));
		}
		if (hasExpired != null) {
			if (hasExpired) {
				restrictions = criteriaBuilder.and(restrictions, root.get("expire").isNotNull(), criteriaBuilder.lessThanOrEqualTo(root.<Date> get("expire"), new Date()));
			} else {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(root.get("expire").isNull(), criteriaBuilder.greaterThan(root.<Date> get("expire"), new Date())));
			}
		}
		criteriaQuery.where(restrictions);
		return super.count(criteriaQuery, null);
	}
	
	public Long countFilterOrder(Order.Type type, Order.Status status, Admin admin, Goods goods, Boolean isPendingReceive, Boolean isPendingRefunds, Boolean isAllocatedStock, Boolean hasExpired) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
		Root<Order> root = criteriaQuery.from(Order.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (type != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("type"), type));
		}
		if (status != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("status"), status));
		}
		if(Admin.Type.admin!=admin.getType()){
			Subquery<Member> subquery = criteriaQuery.subquery(Member.class);
			Root<Member> subqueryRoot = subquery.from(Member.class);
			subquery.select(subqueryRoot);
			subquery.where(criteriaBuilder.equal(subqueryRoot.get("admin"), admin),criteriaBuilder.equal(subqueryRoot.get("id"), root.get("memberBiz")));		
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.exists(subquery));
		}
		if (goods != null) {
			Subquery<OrderItem> subquery = criteriaQuery.subquery(OrderItem.class);
			Root<OrderItem> subqueryRoot = subquery.from(OrderItem.class);
			subquery.select(subqueryRoot);
			subquery.where(criteriaBuilder.equal(subqueryRoot.get("order"), root), criteriaBuilder.equal(subqueryRoot.get("product").get("goods"), goods));
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.exists(subquery));
		}
		if (isPendingReceive != null) {
			Predicate predicate = criteriaBuilder.and(criteriaBuilder.or(root.get("expire").isNull(), criteriaBuilder.greaterThan(root.<Date> get("expire"), new Date())), criteriaBuilder.equal(root.get("paymentMethodType"), PaymentMethod.Type.cashOnDelivery),
					criteriaBuilder.notEqual(root.get("status"), Order.Status.completed), criteriaBuilder.notEqual(root.get("status"), Order.Status.failed), criteriaBuilder.notEqual(root.get("status"), Order.Status.canceled), criteriaBuilder.notEqual(root.get("status"), Order.Status.denied),
					criteriaBuilder.lessThan(root.<BigDecimal> get("amountPaid"), root.<BigDecimal> get("amount")));
			if (isPendingReceive) {
				restrictions = criteriaBuilder.and(restrictions, predicate);
			} else {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.not(predicate));
			}
		}
		if (isPendingRefunds != null) {
			Predicate predicate = criteriaBuilder.or(
					criteriaBuilder.and(
							criteriaBuilder.or(criteriaBuilder.and(root.get("expire").isNotNull(), criteriaBuilder.lessThanOrEqualTo(root.<Date> get("expire"), new Date())), criteriaBuilder.equal(root.get("status"), Order.Status.failed),
									criteriaBuilder.equal(root.get("status"), Order.Status.canceled), criteriaBuilder.equal(root.get("status"), Order.Status.denied)), criteriaBuilder.greaterThan(root.<BigDecimal> get("amountPaid"), BigDecimal.ZERO)),
					criteriaBuilder.and(criteriaBuilder.equal(root.get("status"), Order.Status.completed), criteriaBuilder.greaterThan(root.<BigDecimal> get("amountPaid"), root.<BigDecimal> get("amount"))));
			if (isPendingRefunds) {
				restrictions = criteriaBuilder.and(restrictions, predicate);
			} else {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.not(predicate));
			}
		}
		if (isAllocatedStock != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isAllocatedStock"), isAllocatedStock));
		}
		if (hasExpired != null) {
			if (hasExpired) {
				restrictions = criteriaBuilder.and(restrictions, root.get("expire").isNotNull(), criteriaBuilder.lessThanOrEqualTo(root.<Date> get("expire"), new Date()));
			} else {
				restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(root.get("expire").isNull(), criteriaBuilder.greaterThan(root.<Date> get("expire"), new Date())));
			}
		}
		criteriaQuery.where(restrictions);
		return super.count(criteriaQuery, null);
	}

	public Long createOrderCount(Date beginDate, Date endDate) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
		Root<Order> root = criteriaQuery.from(Order.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (beginDate != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThanOrEqualTo(root.<Date> get("createDate"), beginDate));
		}
		if (endDate != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lessThanOrEqualTo(root.<Date> get("createDate"), endDate));
		}
		criteriaQuery.where(restrictions);
		return super.count(criteriaQuery, null);
	}

	public Long completeOrderCount(Date beginDate, Date endDate) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
		Root<Order> root = criteriaQuery.from(Order.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (beginDate != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThanOrEqualTo(root.<Date> get("completeDate"), beginDate));
		}
		if (endDate != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lessThanOrEqualTo(root.<Date> get("completeDate"), endDate));
		}
		criteriaQuery.where(restrictions);
		return super.count(criteriaQuery, null);
	}

	public BigDecimal createOrderAmount(Date beginDate, Date endDate) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<BigDecimal> criteriaQuery = criteriaBuilder.createQuery(BigDecimal.class);
		Root<Order> root = criteriaQuery.from(Order.class);
		criteriaQuery.select(criteriaBuilder.sum(root.<BigDecimal> get("amount")));
		Predicate restrictions = criteriaBuilder.conjunction();
		if (beginDate != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThanOrEqualTo(root.<Date> get("createDate"), beginDate));
		}
		if (endDate != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lessThanOrEqualTo(root.<Date> get("createDate"), endDate));
		}
		criteriaQuery.where(restrictions);
		BigDecimal result = entityManager.createQuery(criteriaQuery).getSingleResult();
		return result != null ? result : BigDecimal.ZERO;
	}

	public BigDecimal completeOrderAmount(Date beginDate, Date endDate) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<BigDecimal> criteriaQuery = criteriaBuilder.createQuery(BigDecimal.class);
		Root<Order> root = criteriaQuery.from(Order.class);
		criteriaQuery.select(criteriaBuilder.sum(root.<BigDecimal> get("amount")));
		Predicate restrictions = criteriaBuilder.conjunction();
		if (beginDate != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.greaterThanOrEqualTo(root.<Date> get("completeDate"), beginDate));
		}
		if (endDate != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.lessThanOrEqualTo(root.<Date> get("completeDate"), endDate));
		}
		criteriaQuery.where(restrictions);
		BigDecimal result = entityManager.createQuery(criteriaQuery).getSingleResult();
		return result != null ? result : BigDecimal.ZERO;
	}

	public Page<Order> findPagebiz(Order.Type type, Order.Status status, Member member, Boolean isPendingReceive, Boolean isPendingRefunds, Boolean isAllocatedStock, Boolean hasExpired, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);
        criteriaQuery.select(root);
        Predicate restrictions = criteriaBuilder.conjunction();
        if (type != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("type"), type));
        }
        if (status != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("status"), status));
        }
        if (member != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("memberBiz"), member));
        }
        restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(root.get("memberbizdel").isNull(),criteriaBuilder.equal(root.get("memberbizdel"),false)));
        
        if (isPendingReceive != null) {
            Predicate predicate = criteriaBuilder.and(criteriaBuilder.or(root.get("expire").isNull(), criteriaBuilder.greaterThan(root.<Date> get("expire"), new Date())), criteriaBuilder.equal(root.get("paymentMethodType"), PaymentMethod.Type.cashOnDelivery),
                    criteriaBuilder.notEqual(root.get("status"), Order.Status.completed), criteriaBuilder.notEqual(root.get("status"), Order.Status.failed), criteriaBuilder.notEqual(root.get("status"), Order.Status.canceled), criteriaBuilder.notEqual(root.get("status"), Order.Status.denied),
                    criteriaBuilder.lessThan(root.<BigDecimal> get("amountPaid"), root.<BigDecimal> get("amount")));
            if (isPendingReceive) {
                restrictions = criteriaBuilder.and(restrictions, predicate);
            } else {
                restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.not(predicate));
            }
        }
        if (isPendingRefunds != null) {
            Predicate predicate = criteriaBuilder.or(
                    criteriaBuilder.and(
                            criteriaBuilder.or(criteriaBuilder.and(root.get("expire").isNotNull(), criteriaBuilder.lessThanOrEqualTo(root.<Date> get("expire"), new Date())), criteriaBuilder.equal(root.get("status"), Order.Status.failed),
                                    criteriaBuilder.equal(root.get("status"), Order.Status.canceled), criteriaBuilder.equal(root.get("status"), Order.Status.denied)), criteriaBuilder.greaterThan(root.<BigDecimal> get("amountPaid"), BigDecimal.ZERO)),
                    criteriaBuilder.and(criteriaBuilder.equal(root.get("status"), Order.Status.completed), criteriaBuilder.greaterThan(root.<BigDecimal> get("amountPaid"), root.<BigDecimal> get("amount"))));
            if (isPendingRefunds) {
                restrictions = criteriaBuilder.and(restrictions, predicate);
            } else {
                restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.not(predicate));
            }
        }
        if (isAllocatedStock != null) {
            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("isAllocatedStock"), isAllocatedStock));
        }
        if (hasExpired != null) {
            if (hasExpired) {
                restrictions = criteriaBuilder.and(restrictions, root.get("expire").isNotNull(), criteriaBuilder.lessThanOrEqualTo(root.<Date> get("expire"), new Date()));
            } else {
                restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.or(root.get("expire").isNull(), criteriaBuilder.greaterThan(root.<Date> get("expire"), new Date())));
            }
        }
        criteriaQuery.where(restrictions);
        return super.findPage(criteriaQuery, pageable);
    }

	public List<Map<String,Object>> getOrderSize(String sql){
	    Map<String,Object> map=new HashMap<String,Object>();
	    List<Map<String,Object>> reslist=new ArrayList<Map<String,Object>>();
	    Query query =entityManager.createNativeQuery(sql);
	    List<Object[]> list=query.getResultList();
	    for(int i=0;i<list.size();i++){
	        Object[] obj=list.get(i);
	        map=new HashMap<String,Object>();
	        map.put("name", obj[0]);
	        map.put("value",obj[1]);
	        reslist.add(map);
	    }
	    return reslist;
	}
	/**
	 * 获取未评论的最小的订单id
	 * @return
	 */
	public Long getNoReviewOrderId(Long goodId,Long memberId){
	    String sql="select min(a.id) from xx_order a "+
        "inner join xx_order_item b on a.id=b.orders "+
        "inner join xx_product c on b.product=c.id "+
        "inner join xx_goods d on c.goods=d.id "+
        "where d.id="+goodId+" and a.member="+memberId+" "+
        "and a.id not in(select orders from xx_review where goods="+goodId+" and member="+memberId+")";
	    Query query =entityManager.createNativeQuery(sql);
	    return ((BigInteger)query.getSingleResult()).longValue();
	}
}