package com.arf.axd.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.arf.axd.dao.IExchangeCodeRecordDao;
import com.arf.axd.entity.ExchangeCodeRecord;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("exchangeCodeRecordDaoImpl")
public class ExchangeCodeRecordDaoImpl extends BaseDaoImpl<ExchangeCodeRecord, Long> implements IExchangeCodeRecordDao {


	@Override
	public ExchangeCodeRecord findByExchangeCode(String exchangeCode) {
		String hql = "from ExchangeCodeRecord e where e.redeemCode=:exchangeCode";
		TypedQuery<ExchangeCodeRecord> query = entityManager.createQuery(hql, ExchangeCodeRecord.class);
		query.setParameter("exchangeCode", exchangeCode);
		List<ExchangeCodeRecord> exchangeCodeRecords = null;
		try {
			exchangeCodeRecords = query.getResultList();
		} catch (Exception e) {
			return null;
		}
		return CollectionUtils.isEmpty(exchangeCodeRecords) ? null : exchangeCodeRecords.get(0);
	}

}
