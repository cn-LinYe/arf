package com.arf.access.dao.impl;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.arf.access.dao.IAccessInterceptPropFeeConfDao;
import com.arf.access.entity.AccessInterceptPropFeeConf;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("accessInterceptPropFeeConfDaoImpl")
public class AccessInterceptPropFeeConfDaoImpl 
		extends BaseDaoImpl<AccessInterceptPropFeeConf, Long> 
		implements IAccessInterceptPropFeeConfDao {

	@Override
	public AccessInterceptPropFeeConf findByCommunityNumber(
			String communityNumber) {
		StringBuffer hql = new StringBuffer("from AccessInterceptPropFeeConf where 1 = 1");
		hql.append(" and communityNumber = :communityNumber");
		TypedQuery<AccessInterceptPropFeeConf> query = this.entityManager.createQuery(hql.toString(), AccessInterceptPropFeeConf.class);
		query.setParameter("communityNumber", communityNumber);
		try {
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	
	
}
