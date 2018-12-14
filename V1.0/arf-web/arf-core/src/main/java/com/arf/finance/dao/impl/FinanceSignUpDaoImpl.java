package com.arf.finance.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.finance.dao.IFinanceSignUpDao;
import com.arf.finance.entity.FinanceSignUp;

@Repository("financeSignUpDaoImpl")
public class FinanceSignUpDaoImpl extends BaseDaoImpl<FinanceSignUp, Long>
		implements IFinanceSignUpDao {

	@Override
	public List<FinanceSignUp> findByFinanceInfoIdphone(Long financeInfoId, String userName) {
		StringBuffer sb = new StringBuffer("from FinanceSignUp");
		sb.append(" where 1 = 1");
		sb.append(" and financeInfoId = :financeInfoId");
		sb.append(" and phone = :userName");
		sb.append(" order by signUpTime desc");
		TypedQuery<FinanceSignUp> query = this.entityManager.createQuery(sb.toString(), FinanceSignUp.class);
		query.setParameter("financeInfoId", financeInfoId);
		query.setParameter("userName", userName);
		return query.getResultList();
	}

}
