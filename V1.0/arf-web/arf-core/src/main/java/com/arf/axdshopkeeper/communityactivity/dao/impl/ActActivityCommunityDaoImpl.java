package com.arf.axdshopkeeper.communityactivity.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.arf.axdshopkeeper.communityactivity.dao.IActActivityCommunityDao;
import com.arf.axdshopkeeper.communityactivity.entity.ActActivityCommunity;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("actActivityCommunityDaoImpl")
public class ActActivityCommunityDaoImpl extends
		BaseDaoImpl<ActActivityCommunity, Long> implements IActActivityCommunityDao{

	@Override
	public List<ActActivityCommunity> findByActivityId(Long activityId) {
		StringBuffer sb = new StringBuffer("from ActActivityCommunity where 1 = 1 and activityId = :activityId");
		TypedQuery<ActActivityCommunity> typedQuery = entityManager.createQuery(sb.toString(), ActActivityCommunity.class);
		typedQuery.setParameter("activityId", activityId);
		return typedQuery.getResultList();
	}

}
