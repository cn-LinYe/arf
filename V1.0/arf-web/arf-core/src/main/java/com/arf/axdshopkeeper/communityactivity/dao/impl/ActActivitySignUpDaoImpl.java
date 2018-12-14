package com.arf.axdshopkeeper.communityactivity.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.arf.axdshopkeeper.communityactivity.dao.IActActivitySignUpDao;
import com.arf.axdshopkeeper.communityactivity.entity.ActActivitySignUp;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("actActivitySignUpDaoImpl")
public class ActActivitySignUpDaoImpl extends
		BaseDaoImpl<ActActivitySignUp, Long> implements IActActivitySignUpDao {

	@Override
	public ActActivitySignUp findByUsernameActivityId(String userName,
			Long activityId) {
		String hql = "from ActActivitySignUp where mobile = :userName and activityId = :activityId";
		TypedQuery<ActActivitySignUp> typedQuery = super.entityManager.createQuery(hql, ActActivitySignUp.class);
		typedQuery.setParameter("userName", userName);
		typedQuery.setParameter("activityId", activityId);
		List<ActActivitySignUp> list = typedQuery.getResultList();
		if(CollectionUtils.isNotEmpty(list)){
			return list.get(0);
		}else{
			return null;
		}
	}

}
