package com.arf.carbright.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import com.arf.carbright.dao.PUserRangeDao;
import com.arf.carbright.entity.PUserRange;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("pUserRangeDao")
public class PUserRangeDaoImpl extends BaseDaoImpl<PUserRange, Long> implements PUserRangeDao {

	@Override
	public List<PUserRange> findbyparkingID(String parkingId) {
		String jpql = "select puserRange from PUserRange puserRange where puserRange.parkingId = :parkingId";
		return entityManager.createQuery(jpql, PUserRange.class).setParameter("parkingId", parkingId).getResultList();

	}

	@Override
	public PUserRange findByUseRangeNum(String useRangeNum) {
		String jpql = "select puserRange from PUserRange puserRange where puserRange.useRangeNum = :useRangeNum";
		try {
			return entityManager.createQuery(jpql, PUserRange.class).setParameter("useRangeNum", useRangeNum).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public List<PUserRange> findbyBusinessId(int businessId) {
		String jpql = "select puserRange from PUserRange puserRange where puserRange.businessId = :businessId";
		return entityManager.createQuery(jpql, PUserRange.class).setParameter("businessId", businessId).getResultList();

	}
}
