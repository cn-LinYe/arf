package com.arf.activity.childrensday.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.arf.activity.childrensday.dao.IActivityChildren61VotedRecordDao;
import com.arf.activity.childrensday.entity.ActivityChildren61VotedRecord;
import com.arf.activity.childrensday.entity.ActivityChildren61VotedRecord.SourceType;
import com.arf.activity.parkrate.entity.ActivityRecord;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("activityChildren61VotedRecordDaoImpl")
public class ActivityChildren61VotedRecordDaoImpl extends
		BaseDaoImpl<ActivityChildren61VotedRecord, Long> implements
		IActivityChildren61VotedRecordDao {

	@Override
	public List<ActivityChildren61VotedRecord> findByUserIdentifySourceType(
			String userIdentify, SourceType sourceType) {
		StringBuffer hql = new StringBuffer("from ActivityChildren61VotedRecord where userIdentify = :userIdentify and sourceType = :sourceType");
		TypedQuery<ActivityChildren61VotedRecord> typedQuery = 
				super.entityManager.createQuery(hql.toString(), ActivityChildren61VotedRecord.class);
		typedQuery.setParameter("userIdentify", userIdentify);
		typedQuery.setParameter("sourceType", sourceType);
		return typedQuery.getResultList();
	}

	
	
}
