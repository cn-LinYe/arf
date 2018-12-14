package com.arf.activity.parkrate.dao.impl;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.arf.activity.parkrate.dao.IActivityRecordDao;
import com.arf.activity.parkrate.entity.ActivityRecord;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("activityRecordDaoImpl")
public class ActivityRecordDaoImpl extends BaseDaoImpl<ActivityRecord, Long> implements
		IActivityRecordDao {

	@Override
	public ActivityRecord findByOutTradeNo(String outTradeNo) {
		StringBuffer hql = new StringBuffer("from ActivityRecord where outTradeNo = :outTradeNo");
		TypedQuery<ActivityRecord> typedQuery = super.entityManager.createQuery(hql.toString(), ActivityRecord.class);
		typedQuery.setParameter("outTradeNo", outTradeNo);
		try {
			return typedQuery.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

}
