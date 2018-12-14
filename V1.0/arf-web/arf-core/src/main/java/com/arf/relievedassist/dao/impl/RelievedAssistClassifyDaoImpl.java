package com.arf.relievedassist.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.relievedassist.dao.IRelievedAssistClassifyDao;
import com.arf.relievedassist.entity.RelievedAssistClassify;

@Repository("relievedAssistClassifyDao")
public class RelievedAssistClassifyDaoImpl extends BaseDaoImpl<RelievedAssistClassify, Long> implements IRelievedAssistClassifyDao{

	@Override
	public List<RelievedAssistClassify> findNormalClassify(RelievedAssistClassify.Status status) {
		String hql = "from RelievedAssistClassify where status = :status";
		TypedQuery<RelievedAssistClassify> query = entityManager.createQuery(hql,RelievedAssistClassify.class);
		query.setParameter("status",status);
		return query.getResultList();
	}

}
