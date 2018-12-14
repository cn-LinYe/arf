package com.arf.finance.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.finance.dao.IFinanceTagDao;
import com.arf.finance.entity.FinanceTag;
import com.google.common.collect.Lists;

@Repository("financeTagDaoImpl")
public class FinanceTagDaoImpl extends BaseDaoImpl<FinanceTag, Long> implements
		IFinanceTagDao {

	@Override
	public List<FinanceTag> findByIds(List<Long> tagids) {
		List<FinanceTag> financeTagList = Lists.newArrayList();
		if(CollectionUtils.isEmpty(tagids)){
			return financeTagList;
		}
		StringBuffer sb = new StringBuffer("from FinanceTag");
		sb.append(" where 1 = 1");
		sb.append(" and id in (:ids)");
		TypedQuery<FinanceTag> query = this.entityManager.createQuery(sb.toString(), FinanceTag.class);
		query.setParameter("ids", tagids);
		return query.getResultList();
	}

	
	
}
