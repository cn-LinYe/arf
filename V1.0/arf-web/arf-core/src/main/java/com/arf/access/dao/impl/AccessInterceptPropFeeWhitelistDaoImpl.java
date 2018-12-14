package com.arf.access.dao.impl;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.arf.access.dao.IAccessInterceptPropFeeWhitelistDao;
import com.arf.access.entity.AccessInterceptPropFeeWhitelist;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("accessInterceptPropFeeWhitelistDaoImpl")
public class AccessInterceptPropFeeWhitelistDaoImpl 
		extends BaseDaoImpl<AccessInterceptPropFeeWhitelist, Long> 
		implements IAccessInterceptPropFeeWhitelistDao {

	@Override
	public AccessInterceptPropFeeWhitelist findByCommunityNumberUsername(
			String communityNumber, String userName) {
		StringBuffer hql = new StringBuffer("from AccessInterceptPropFeeWhitelist where 1 = 1");
		hql.append(" and communityNumber = :communityNumber and userName = :userName");
		TypedQuery<AccessInterceptPropFeeWhitelist> query = 
				this.entityManager.createQuery(hql.toString(), AccessInterceptPropFeeWhitelist.class);
		query.setParameter("communityNumber", communityNumber);
		query.setParameter("userName", userName);
		try {
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
}
