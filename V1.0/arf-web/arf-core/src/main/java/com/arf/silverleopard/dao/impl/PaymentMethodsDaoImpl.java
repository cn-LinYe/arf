package com.arf.silverleopard.dao.impl;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.silverleopard.dao.PaymentMethodsDao;
import com.arf.silverleopard.entity.PaymentMethods;

@Repository("paymentMethodsDao")
public class PaymentMethodsDaoImpl extends BaseDaoImpl<PaymentMethods, Long> implements PaymentMethodsDao{

	@Override
	public int findCount(String appId, String code) {
		if(StringUtils.isBlank(appId) || StringUtils.isBlank(code)) {
			return 1;
		}
		StringBuffer countHql = new StringBuffer("select count(1) as COUNT from p_payment_methods a");
		countHql.append(" where 1=1 and a.app_id=:appId");
		countHql.append(" and a.code=:code");
		Query queryCount = this.entityManager.createNativeQuery(countHql.toString());
		queryCount.setParameter("appId", appId);
		queryCount.setParameter("code", code);
		String o = queryCount.getSingleResult().toString();
		if(StringUtils.isBlank(o)) {
			return 0;
		}else {
			return Integer.parseInt(o);
		}
	}

}
