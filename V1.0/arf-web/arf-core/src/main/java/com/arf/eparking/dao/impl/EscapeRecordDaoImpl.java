package com.arf.eparking.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.eparking.dao.EscapeRecordDao;
import com.arf.eparking.entity.EscapeRecord;
import com.arf.eparking.entity.EscapeRecord.RecoveryStatus;

@Repository("escapeRecordDaoImpl")
public class EscapeRecordDaoImpl extends BaseDaoImpl<EscapeRecord, Long> implements EscapeRecordDao {

	@Override
	public List<EscapeRecord> findByUserNameAndRecoveryStatus(String userName,RecoveryStatus recoveryStatus) {
		String hql = "from EscapeRecord where userName = :userName and recoveryStatus = :recoveryStatus order by createDate desc";
		TypedQuery<EscapeRecord> query = entityManager.createQuery(hql, EscapeRecord.class);
		query.setParameter("userName", userName);
		query.setParameter("recoveryStatus", (byte)recoveryStatus.ordinal());
		return CollectionUtils.isEmpty(query.getResultList())?null:query.getResultList();
	}

}
