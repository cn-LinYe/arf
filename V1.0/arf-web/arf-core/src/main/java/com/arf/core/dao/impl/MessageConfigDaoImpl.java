/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.dao.impl;

import javax.persistence.NoResultException;

import com.arf.core.dao.MessageConfigDao;
import com.arf.core.entity.MessageConfig;
import com.arf.core.entity.MessageConfig.Type;

import org.springframework.stereotype.Repository;

/**
 * Dao - 消息配置
 * 
 * @author arf
 * @version 4.0
 */
@Repository("messageConfigDaoImpl")
public class MessageConfigDaoImpl extends BaseDaoImpl<MessageConfig, Long> implements MessageConfigDao {

	public MessageConfig find(Type type) {
		if (type == null) {
			return null;
		}
		try {
			String jpql = "select messageConfig from MessageConfig messageConfig where messageConfig.type = :type";
			return entityManager.createQuery(jpql, MessageConfig.class).setParameter("type", type).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}