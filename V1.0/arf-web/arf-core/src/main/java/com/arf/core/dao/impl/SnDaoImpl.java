/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.dao.impl;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;

import com.arf.core.dao.SnDao;
import com.arf.core.entity.Sn;
import com.arf.core.entity.Sn.Type;
import com.arf.core.util.FreeMarkerUtils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import freemarker.template.TemplateException;

/**
 * Dao - 序列号
 * 
 * @author arf
 * @version 4.0
 */
@Repository("snDaoImpl")
public class SnDaoImpl implements SnDao, InitializingBean {

	private HiloOptimizer goodsHiloOptimizer;
	private HiloOptimizer orderHiloOptimizer;
	private HiloOptimizer paymentLogHiloOptimizer;
	private HiloOptimizer paymentHiloOptimizer;
	private HiloOptimizer refundsHiloOptimizer;
	private HiloOptimizer shippingHiloOptimizer;
	private HiloOptimizer returnsHiloOptimizer;

	@PersistenceContext
	private EntityManager entityManager;
	@Value("${sn.goods.prefix}")
	private String goodsPrefix;
	@Value("${sn.goods.maxLo}")
	private int goodsMaxLo;
	@Value("${sn.order.prefix}")
	private String orderPrefix;
	@Value("${sn.order.maxLo}")
	private int orderMaxLo;
	@Value("${sn.paymentLog.prefix}")
	private String paymentLogPrefix;
	@Value("${sn.paymentLog.maxLo}")
	private int paymentLogMaxLo;
	@Value("${sn.payment.prefix}")
	private String paymentPrefix;
	@Value("${sn.payment.maxLo}")
	private int paymentMaxLo;
	@Value("${sn.refunds.prefix}")
	private String refundsPrefix;
	@Value("${sn.refunds.maxLo}")
	private int refundsMaxLo;
	@Value("${sn.shipping.prefix}")
	private String shippingPrefix;
	@Value("${sn.shipping.maxLo}")
	private int shippingMaxLo;
	@Value("${sn.returns.prefix}")
	private String returnsPrefix;
	@Value("${sn.returns.maxLo}")
	private int returnsMaxLo;

	public void afterPropertiesSet() throws Exception {
		goodsHiloOptimizer = new HiloOptimizer(Type.goods, goodsPrefix, goodsMaxLo);
		orderHiloOptimizer = new HiloOptimizer(Type.order, orderPrefix, orderMaxLo);
		paymentLogHiloOptimizer = new HiloOptimizer(Type.paymentLog, paymentLogPrefix, paymentLogMaxLo);
		paymentHiloOptimizer = new HiloOptimizer(Type.payment, paymentPrefix, paymentMaxLo);
		refundsHiloOptimizer = new HiloOptimizer(Type.refunds, refundsPrefix, refundsMaxLo);
		shippingHiloOptimizer = new HiloOptimizer(Type.shipping, shippingPrefix, shippingMaxLo);
		returnsHiloOptimizer = new HiloOptimizer(Type.returns, returnsPrefix, returnsMaxLo);
	}

	public String generate(Type type) {
		Assert.notNull(type);

		switch (type) {
		case goods:
			return goodsHiloOptimizer.generate();
		case order:
			return orderHiloOptimizer.generate();
		case paymentLog:
			return paymentLogHiloOptimizer.generate();
		case payment:
			return paymentHiloOptimizer.generate();
		case refunds:
			return refundsHiloOptimizer.generate();
		case shipping:
			return shippingHiloOptimizer.generate();
		case returns:
			return returnsHiloOptimizer.generate();
		}
		return null;
	}

	private long getLastValue(Type type) {
		String jpql = "select sn from Sn sn where sn.type = :type";
		Sn sn = entityManager.createQuery(jpql, Sn.class).setLockMode(LockModeType.PESSIMISTIC_WRITE).setParameter("type", type).getSingleResult();
		long lastValue = sn.getLastValue();
		sn.setLastValue(lastValue + 1);
		return lastValue;
	}

	/**
	 * 高低位算法
	 */
	private class HiloOptimizer {

		private Type type;
		private String prefix;
		private int maxLo;
		private int lo;
		private long hi;
		private long lastValue;

		public HiloOptimizer(Type type, String prefix, int maxLo) {
			this.type = type;
			this.prefix = prefix != null ? prefix.replace("{", "${") : "";
			this.maxLo = maxLo;
			this.lo = maxLo + 1;
		}

		public synchronized String generate() {
			if (lo > maxLo) {
				lastValue = getLastValue(type);
				lo = lastValue == 0 ? 1 : 0;
				hi = lastValue * (maxLo + 1);
			}
			try {
				return FreeMarkerUtils.process(prefix) + (hi + lo++);
			} catch (IOException e) {
				throw new RuntimeException(e.getMessage(), e);
			} catch (TemplateException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
	}

}