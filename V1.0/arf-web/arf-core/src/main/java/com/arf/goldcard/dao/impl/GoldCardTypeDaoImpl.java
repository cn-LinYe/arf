package com.arf.goldcard.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.goldcard.dao.IGoldCardTypeDao;
import com.arf.goldcard.entity.GoldCardType;

@Repository("goldCardTypeDao")
public class GoldCardTypeDaoImpl extends BaseDaoImpl<GoldCardType, Long> implements IGoldCardTypeDao {

	@Override
	public List<GoldCardType> findByUnShelved(Integer unShelved,Date nowTime) {
		StringBuffer hql = new StringBuffer("from GoldCardType where unShelved = :unShelved");
		hql.append(" and unshelvedDate >=:nowTime");
		hql.append(" and shelvedDate <=:nowTime");
		TypedQuery<GoldCardType> typedQuery = super.entityManager.createQuery(hql.toString(), GoldCardType.class);
		typedQuery.setParameter("unShelved", unShelved);
		typedQuery.setParameter("nowTime", nowTime, TemporalType.DATE);
		return typedQuery.getResultList();
	}
}
