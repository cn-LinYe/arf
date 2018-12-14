package com.arf.access.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Repository;

import com.arf.access.dao.IAccessNCardAuthorityDao;
import com.arf.access.entity.AccessNCardAuthority;
import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.core.entity.Cart;

@Repository("accessNCardAuthorityDao")
public class AccessNCardAuthorityDaoImpl extends BaseDaoImpl<AccessNCardAuthority, Long> implements IAccessNCardAuthorityDao{

	@Override
	public List<AccessNCardAuthority> findByCardId(Long cardId) {
		StringBuffer hql = new StringBuffer(" from AccessNCardAuthority a where 1=1 and a.accessNCardId =:cardId ");
		TypedQuery<AccessNCardAuthority> query = entityManager.createQuery(hql.toString(), AccessNCardAuthority.class);
		query.setParameter("cardId", cardId);
		return query.getResultList();
	}

	@Override
	public void deleteByCardId(Long cardId) {
		String jpql = "delete from AccessNCardAuthority a where a.accessNCardId=:cardId";
		entityManager.createQuery(jpql).setParameter("cardId", cardId).executeUpdate();
	}

}
