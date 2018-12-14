package com.arf.core.dao.impl;

import javax.persistence.TypedQuery;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.arf.core.dao.PaymentSetingModelDao;
import com.arf.core.entity.PaymentSetingModel;
import com.arf.core.entity.PaymentSetingModel.Type;

/**
 * Dao - 
 * 
 * @author arf  dg
 * @version 4.0
 */
@Repository("paymentSetingModelDaoImpl")
public class PaymentSetingModelDaoImpl extends BaseDaoImpl<PaymentSetingModel, Long> implements PaymentSetingModelDao {
	
	@Override
	public PaymentSetingModel findByCommunityNumber(String CommunityNumber){
		if (StringUtils.isEmpty(CommunityNumber)) {
			return null;
		}
		try {
			String jpql = "select paymentSetingModel from PaymentSetingModel paymentSetingModel where 1=1 "
					+ " and paymentSetingModel.community_number = :community_number "
					+ " and paymentSetingModel.objectType = " + PaymentSetingModel.Type.COMMUNITY.ordinal();
			return entityManager.createQuery(jpql, PaymentSetingModel.class).setParameter("community_number", CommunityNumber).getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public PaymentSetingModel findByObjectTypeNumber(Type type, String number) {
		String hql = "from PaymentSetingModel where objectType = :type and community_number = :number";
		TypedQuery<PaymentSetingModel> typedQuery = entityManager.createQuery(hql, PaymentSetingModel.class);
		typedQuery.setParameter("type", type);
		if(type == Type.ANERFA){
			typedQuery.setParameter("number", "ANERFA");
		}else{
			typedQuery.setParameter("number", number);
		}
		try {
			return typedQuery.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
}
