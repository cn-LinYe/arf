package com.arf.carbright.dao.impl;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.arf.carbright.dao.CabinetDao;
import com.arf.carbright.entity.Cabinet;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("cabinetDao")
public class CabinetDaoImpl extends BaseDaoImpl<Cabinet, Long> implements CabinetDao {

	@Override
	public List<Cabinet> findByCommunityNum(String communityNum) {
		String jpql = "select cab from Cabinet cab where cab.parkingId = :communityNum";
		return entityManager.createQuery(jpql, Cabinet.class)
				.setParameter("communityNum",communityNum)
				.getResultList();
	}

}
