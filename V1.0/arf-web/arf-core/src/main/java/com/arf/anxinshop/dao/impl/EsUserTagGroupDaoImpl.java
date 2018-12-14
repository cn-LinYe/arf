package com.arf.anxinshop.dao.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.arf.anxinshop.dao.IEsUserTagGroupDao;
import com.arf.anxinshop.entity.EsUserTagGroup;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("esUserTagGroupDaoImpl")
public class EsUserTagGroupDaoImpl extends BaseDaoImpl<EsUserTagGroup, Long>
	implements IEsUserTagGroupDao{

	@Override
	public List<EsUserTagGroup> findAllGroup() {
		String hql = "from EsUserTagGroup where 1=1";
		TypedQuery<EsUserTagGroup> typedQuery = entityManager.createQuery(hql, EsUserTagGroup.class);
		return typedQuery.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> findGroupUser(String sql){
		if(StringUtils.isBlank(sql)){
			return null;
		}
		Query query = entityManager.createNativeQuery(sql);
		//query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.getResultList();
	}
}
