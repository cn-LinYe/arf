package com.arf.relievedassist.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.relievedassist.dao.IRelievedAssistUserGradeRecordDao;
import com.arf.relievedassist.entity.RelievedAssistUserGradeRecord;

@Repository("relievedAssistUserGradeRecordDao")
public class RelievedAssistUserGradeRecordDaoImpl extends BaseDaoImpl<RelievedAssistUserGradeRecord, Long> implements IRelievedAssistUserGradeRecordDao{

	@Override
	public RelievedAssistUserGradeRecord findDefaultGrade() {
		StringBuffer hql = new StringBuffer("from RelievedAssistUserGradeRecord order by grade");		
		TypedQuery<RelievedAssistUserGradeRecord> query = entityManager.createQuery(hql.toString(),RelievedAssistUserGradeRecord.class);		
		query.setMaxResults(1);		
		List<RelievedAssistUserGradeRecord> records = query.getResultList();
		if(CollectionUtils.isNotEmpty(records)){
			return records.get(0);
		}
		return null;
	}

	@Override
	public RelievedAssistUserGradeRecord findByAssistPoint(Integer assistPoint) {
		StringBuffer hql = new StringBuffer("from RelievedAssistUserGradeRecord where gradeMinPoint <:assistPoint and gradeMaxPoint>:assistPoint ");		
		TypedQuery<RelievedAssistUserGradeRecord> query = entityManager.createQuery(hql.toString(),RelievedAssistUserGradeRecord.class);		
		query.setParameter("assistPoint", assistPoint);	
		List<RelievedAssistUserGradeRecord> records = query.getResultList();
		if(CollectionUtils.isNotEmpty(records)){
			return records.get(0);
		}
		return null;
	}

}
