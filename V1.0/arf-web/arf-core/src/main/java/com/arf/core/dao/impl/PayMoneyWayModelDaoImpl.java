package com.arf.core.dao.impl;

import javax.persistence.NoResultException;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.arf.core.dao.PayMoneyWayModelDao;
import com.arf.core.entity.PayMoneyWayModel;

/**
 * Dao - 根据支付方式获取支付金额表
 * 
 * @author arf
 * @version 4.0
 */
@Repository("payMoneyWayModelDaoImpl")
public class PayMoneyWayModelDaoImpl extends BaseDaoImpl<PayMoneyWayModel, Long> implements PayMoneyWayModelDao {
	
	public PayMoneyWayModel selectByLevel(int level) {
		if (StringUtils.isEmpty(level+"")) {
			return null;
		}
		try {
			String jpql = "select payMoneyWayModel from PayMoneyWayModel payMoneyWayModel where lower(payMoneyWayModel.level) = lower(:level)";
			return entityManager.createQuery(jpql, PayMoneyWayModel.class).setParameter("level", level).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}
