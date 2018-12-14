/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;

import javax.annotation.Resource;

import com.arf.core.dao.AttributeDao;
import com.arf.core.dao.BaseDao;
import com.arf.core.dao.GoodsDao;
import com.arf.core.entity.Attribute;
import com.arf.core.entity.ProductCategory;
import com.arf.core.service.AttributeService;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * Service - 属性
 * 
 * @author arf
 * @version 4.0
 */
@Service("attributeServiceImpl")
public class AttributeServiceImpl extends BaseServiceImpl<Attribute, Long> implements AttributeService {

	@Resource(name = "attributeDaoImpl")
	private AttributeDao attributeDao;
	@Resource(name = "goodsDaoImpl")
	private GoodsDao goodsDao;

	@Override
	protected BaseDao<Attribute, Long> getBaseDao() {
		return attributeDao;
	}

	@Transactional(readOnly = true)
	public Integer findUnusedPropertyIndex(ProductCategory productCategory) {
		return attributeDao.findUnusedPropertyIndex(productCategory);
	}

	@Transactional(readOnly = true)
	public String toAttributeValue(Attribute attribute, String value) {
		Assert.notNull(attribute);

		if (StringUtils.isEmpty(value) || attribute.getOptions() == null || !attribute.getOptions().contains(value)) {
			return null;
		}
		return value;
	}

	@Override
	@Transactional
	public Attribute save(Attribute attribute) {
		Assert.notNull(attribute);

		Integer unusedPropertyIndex = attributeDao.findUnusedPropertyIndex(attribute.getProductCategory());
		Assert.notNull(unusedPropertyIndex);

		attribute.setPropertyIndex(unusedPropertyIndex);
		return super.save(attribute);
	}

	@Override
	@Transactional
	public void delete(Attribute attribute) {
		if (attribute != null) {
			goodsDao.clearAttributeValue(attribute);
		}

		super.delete(attribute);
	}

}