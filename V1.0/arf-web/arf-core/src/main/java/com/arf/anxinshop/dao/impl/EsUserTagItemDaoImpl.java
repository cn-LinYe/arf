package com.arf.anxinshop.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.arf.anxinshop.dao.IEsUserTagItemDao;
import com.arf.anxinshop.entity.EsUserTagItem;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("esUserTagItemDaoImpl")
public class EsUserTagItemDaoImpl extends BaseDaoImpl<EsUserTagItem, Long> 
	implements IEsUserTagItemDao{

	@Override
	public List<EsUserTagItem> findByGroupId(Long groupId){
		StringBuffer hql = new StringBuffer(" from EsUserTagItem where 1=1");
		if(groupId != null){
			hql.append(" and groupId = :groupId");
		}
		TypedQuery<EsUserTagItem> typedQuery = entityManager.createQuery(hql.toString(), EsUserTagItem.class);
		typedQuery.setParameter("groupId", groupId.intValue());
		return typedQuery.getResultList();
	}
}
	