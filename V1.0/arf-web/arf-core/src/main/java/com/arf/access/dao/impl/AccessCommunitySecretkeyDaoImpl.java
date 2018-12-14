package com.arf.access.dao.impl;

import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.arf.access.dao.IAccessCommunitySecretkeyDao;
import com.arf.access.entity.AccessCommunitySecretkey;
import com.arf.core.dao.impl.BaseDaoImpl;

@Deprecated
@Repository("accessCommunitySecretkeyDaoImpl")
public class AccessCommunitySecretkeyDaoImpl extends
		BaseDaoImpl<AccessCommunitySecretkey, Long> implements
		IAccessCommunitySecretkeyDao {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public AccessCommunitySecretkey findByCommunityNumber(String communityNumber){
		StringBuffer sb = new StringBuffer(" from AccessCommunitySecretkey where 1=1");
		sb.append(" and communityNumber =:communityNumber");
		TypedQuery<AccessCommunitySecretkey> query = this.entityManager.createQuery(sb.toString(),AccessCommunitySecretkey.class);
		query.setParameter("communityNumber", communityNumber);
		try{
			return query.getSingleResult();
		}catch (Exception e){
			logger.error("AccessCommunitySecretkeyDaoImpl.findByCommunityNumber获取数据错误：",e);
			return null;
		}
	}
	
}
