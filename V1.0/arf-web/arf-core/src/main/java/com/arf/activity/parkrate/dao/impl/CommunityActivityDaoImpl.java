package com.arf.activity.parkrate.dao.impl;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.arf.activity.parkrate.dao.ICommunityActivityDao;
import com.arf.activity.parkrate.entity.CommunityActivity;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("communityActivityDaoImpl")
public class CommunityActivityDaoImpl extends BaseDaoImpl<CommunityActivity, Long>
		implements ICommunityActivityDao {

	@Override
	public CommunityActivity findByCommunity(String communityNumber) {
		String hql = "from CommunityActivity a where a.communityNumber = :communityNumber";
		TypedQuery<CommunityActivity> query = entityManager.createQuery(hql, CommunityActivity.class);
		query.setParameter("communityNumber", communityNumber);
		try {
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	

}
