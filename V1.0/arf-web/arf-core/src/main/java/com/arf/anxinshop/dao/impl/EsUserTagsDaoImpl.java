package com.arf.anxinshop.dao.impl;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.arf.anxinshop.dao.IEsUserTagsDao;
import com.arf.anxinshop.entity.EsUserTags;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("esUserTagsDaoImpl")
public class EsUserTagsDaoImpl extends BaseDaoImpl<EsUserTags, Long> implements
		IEsUserTagsDao {

	@Override
	public EsUserTags findByUserName(String userName) {
		StringBuffer hql = new  StringBuffer("from EsUserTags where 1 = 1");
		hql.append(" and userName = :userName ");
		TypedQuery<EsUserTags> typedQuery = entityManager.createQuery(hql.toString(), EsUserTags.class);
		typedQuery.setParameter("userName", userName);
		if(CollectionUtils.isNotEmpty(typedQuery.getResultList())){
			return typedQuery.getResultList().get(0);
		}else{
			return null;
		}
	}

	@Override
	public void deleteAll(){
		String sql = "delete from es_user_tags where 1=1";
		Query query = this.entityManager.createNativeQuery(sql);
		query.executeUpdate();
	}
}
