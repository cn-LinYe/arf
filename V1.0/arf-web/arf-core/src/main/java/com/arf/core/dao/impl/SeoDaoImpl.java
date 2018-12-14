/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.dao.impl;

import javax.persistence.NoResultException;

import com.arf.core.dao.SeoDao;
import com.arf.core.entity.Seo;
import com.arf.core.entity.Seo.Type;

import org.springframework.stereotype.Repository;

/**
 * Dao - SEO设置
 * 
 * @author arf
 * @version 4.0
 */
@Repository("seoDaoImpl")
public class SeoDaoImpl extends BaseDaoImpl<Seo, Long> implements SeoDao {

	public Seo find(Type type) {
		if (type == null) {
			return null;
		}
		try {
			String jpql = "select seo from Seo seo where seo.type = :type";
			return entityManager.createQuery(jpql, Seo.class).setParameter("type", type).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}