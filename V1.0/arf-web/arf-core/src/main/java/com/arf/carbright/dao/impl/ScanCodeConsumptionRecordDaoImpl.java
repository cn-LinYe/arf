package com.arf.carbright.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.arf.carbright.dao.IScanCodeConsumptionRecordDao;
import com.arf.carbright.entity.ScanCodeConsumptionRecord;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("scanCodeConsumptionRecordDaoImpl")
public class ScanCodeConsumptionRecordDaoImpl extends BaseDaoImpl<ScanCodeConsumptionRecord, Long> implements IScanCodeConsumptionRecordDao{

	@Override
	public ScanCodeConsumptionRecord findByOrderNum(String orderNum) {
		try {
			String jpql = " from ScanCodeConsumptionRecord s where s.orderNum = :orderNum";
			TypedQuery<ScanCodeConsumptionRecord> query=this.entityManager.createQuery(jpql, ScanCodeConsumptionRecord.class);
			query.setParameter("orderNum", orderNum);
			List<ScanCodeConsumptionRecord> list =query.getResultList();
			return CollectionUtils.isEmpty(list)?null:list.get(0);
		} catch (NoResultException e) {
			return null;
		}
	}

}
