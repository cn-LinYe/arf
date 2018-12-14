/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import com.arf.core.Filter;
import com.arf.core.Order;
import com.arf.core.dao.BaseDao;
import com.arf.core.dao.BrandDao;
import com.arf.core.dao.MemberRankDao;
import com.arf.core.dao.ProductCategoryDao;
import com.arf.core.dao.ProductDao;
import com.arf.core.dao.PromotionDao;
import com.arf.core.entity.MemberRank;
import com.arf.core.entity.ProductCategory;
import com.arf.core.entity.Promotion;
import com.arf.core.service.PromotionService;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * Service - 促销
 * 
 * @author arf
 * @version 4.0
 */
@Service("promotionServiceImpl")
public class PromotionServiceImpl extends BaseServiceImpl<Promotion, Long> implements PromotionService {

	private static final List<Map<String, Object>> PRICE_EXPRESSION_VARIABLES = new ArrayList<Map<String, Object>>();
	private static final List<Map<String, Object>> POINT_EXPRESSION_VARIABLES = new ArrayList<Map<String, Object>>();

	@Resource(name = "promotionDaoImpl")
	private PromotionDao promotionDao;
	@Resource(name = "memberRankDaoImpl")
	private MemberRankDao memberRankDao;
	@Resource(name = "productCategoryDaoImpl")
	private ProductCategoryDao productCategoryDao;
	@Resource(name = "productDaoImpl")
	private ProductDao productDao;
	@Resource(name = "brandDaoImpl")
	private BrandDao brandDao;

	static {
		Map<String, Object> variable0 = new HashMap<String, Object>();
		Map<String, Object> variable1 = new HashMap<String, Object>();
		Map<String, Object> variable2 = new HashMap<String, Object>();
		Map<String, Object> variable3 = new HashMap<String, Object>();
		variable0.put("quantity", 99);
		variable0.put("price", new BigDecimal("99"));
		variable1.put("quantity", 99);
		variable1.put("price", new BigDecimal("9.9"));
		variable2.put("quantity", 99);
		variable2.put("price", new BigDecimal("0.99"));
		variable3.put("quantity", 99);
		variable3.put("point", 99L);
		PRICE_EXPRESSION_VARIABLES.add(variable0);
		PRICE_EXPRESSION_VARIABLES.add(variable1);
		PRICE_EXPRESSION_VARIABLES.add(variable2);
		POINT_EXPRESSION_VARIABLES.add(variable3);
	}

	@Override
	protected BaseDao<Promotion, Long> getBaseDao() {
		return promotionDao;
	}

	@Transactional(readOnly = true)
	public boolean isValidPriceExpression(String priceExpression) {
		Assert.hasText(priceExpression);

		for (Map<String, Object> variable : PRICE_EXPRESSION_VARIABLES) {
			try {
				Binding binding = new Binding();
				for (Entry<String, Object> entry : variable.entrySet()) {
					binding.setVariable(entry.getKey(), entry.getValue());
				}
				GroovyShell groovyShell = new GroovyShell(binding);
				Object result = groovyShell.evaluate(priceExpression);
				new BigDecimal(result.toString());
			} catch (Exception e) {
				return false;
			}
		}
		return true;
	}

	@Transactional(readOnly = true)
	public boolean isValidPointExpression(String pointExpression) {
		Assert.hasText(pointExpression);

		for (Map<String, Object> variable : POINT_EXPRESSION_VARIABLES) {
			try {
				Binding binding = new Binding();
				for (Entry<String, Object> entry : variable.entrySet()) {
					binding.setVariable(entry.getKey(), entry.getValue());
				}
				GroovyShell groovyShell = new GroovyShell(binding);
				Object result = groovyShell.evaluate(pointExpression);
				Long.valueOf(result.toString());
			} catch (Exception e) {
				return false;
			}
		}
		return true;
	}

	@Transactional(readOnly = true)
	public List<Promotion> findList(MemberRank memberRank, ProductCategory productCategory, Boolean hasBegun, Boolean hasEnded, Integer count, List<Filter> filters, List<Order> orders) {
		return promotionDao.findList(memberRank, productCategory, hasBegun, hasEnded, count, filters, orders);
	}

	@Transactional(readOnly = true)
	@Cacheable(value = "promotion", condition = "#useCache")
	public List<Promotion> findList(Long memberRankId, Long productCategoryId, Boolean hasBegun, Boolean hasEnded, Integer count, List<Filter> filters, List<Order> orders, boolean useCache) {
		MemberRank memberRank = memberRankDao.find(memberRankId);
		if (memberRankId != null && memberRank == null) {
			return Collections.emptyList();
		}
		ProductCategory productCategory = productCategoryDao.find(productCategoryId);
		if (productCategoryId != null && productCategory == null) {
			return Collections.emptyList();
		}
		return promotionDao.findList(memberRank, productCategory, hasBegun, hasEnded, count, filters, orders);
	}

	@Override
	@Transactional
	@CacheEvict(value = "promotion", allEntries = true)
	public Promotion save(Promotion promotion) {
		return super.save(promotion);
	}

	@Override
	@Transactional
	@CacheEvict(value = "promotion", allEntries = true)
	public Promotion update(Promotion promotion) {
		return super.update(promotion);
	}

	@Override
	@Transactional
	@CacheEvict(value = "promotion", allEntries = true)
	public Promotion update(Promotion promotion, String... ignoreProperties) {
		return super.update(promotion, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = "promotion", allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = "promotion", allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = "promotion", allEntries = true)
	public void delete(Promotion promotion) {
		super.delete(promotion);
	}

}