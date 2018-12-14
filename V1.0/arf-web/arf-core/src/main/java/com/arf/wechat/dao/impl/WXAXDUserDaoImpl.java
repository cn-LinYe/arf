package com.arf.wechat.dao.impl;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.wechat.dao.IWXAXDUserDao;
import com.arf.wechat.entity.WXAXDUser;

@Repository("wxAXDUserDaoImpl")
public class WXAXDUserDaoImpl extends BaseDaoImpl<WXAXDUser, Long> implements
		IWXAXDUserDao {

	@Override
	public WXAXDUser findByOpenId(String userIdentify) {
		StringBuffer hql  = new StringBuffer("from WXAXDUser where openId = :openId");
		TypedQuery<WXAXDUser> typedQuery = entityManager.createQuery(hql.toString(),WXAXDUser.class);
		typedQuery.setParameter("openId", userIdentify);
		try {
			return typedQuery.getSingleResult();			
		} catch (Exception e) {
			return null;
		}
	}

	
	
}
