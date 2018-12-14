package com.arf.core.dao.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.PaymentRecordModelDao;
import com.arf.core.entity.PaymentRecordModel;

/**
 * Dao - 支付纪录表
 * 
 * @author arf
 * @version 4.0
 */
@Repository("paymentRecordModelDaoImpl")
public class PaymentRecordModelDaoImpl extends BaseDaoImpl<PaymentRecordModel, Long> implements PaymentRecordModelDao {
	
	public List<PaymentRecordModel> select(String user_name) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<PaymentRecordModel> criteriaQuery = criteriaBuilder.createQuery(PaymentRecordModel.class);
		Root<PaymentRecordModel> root = criteriaQuery.from(PaymentRecordModel.class);
		criteriaQuery.select(root);		
		criteriaQuery.where(criteriaBuilder.equal(root.get("user_name"), user_name));
		return super.findList(criteriaQuery, null, null, null, null);
	}
	
}
