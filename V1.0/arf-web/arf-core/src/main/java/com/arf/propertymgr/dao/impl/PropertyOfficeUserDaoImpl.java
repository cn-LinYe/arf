package com.arf.propertymgr.dao.impl;

import java.util.List;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.arf.access.entity.AccessManagement;
import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.propertymgr.dao.IPropertyOfficeUserDao;
import com.arf.propertymgr.entity.PropertyOfficeUser;

@Repository("propertyOfficeUserDaoImpl")
public class PropertyOfficeUserDaoImpl extends BaseDaoImpl<PropertyOfficeUser, Long> implements IPropertyOfficeUserDao {

	
	@Override
	public List<PropertyOfficeUser> findByCommunityNumber(String communityNumber) {
		StringBuffer hql = new StringBuffer("from PropertyOfficeUser where communityNumber = :communityNumber");
		TypedQuery<PropertyOfficeUser> typedQuery = super.entityManager.createQuery(hql.toString(), PropertyOfficeUser.class);
		typedQuery.setParameter("communityNumber", communityNumber);
		return typedQuery.getResultList();
	}
	@Override
	public List<PropertyOfficeUser> findByUserName(String userName) {
		StringBuffer hql = new StringBuffer("from PropertyOfficeUser where userName = :userName");
		TypedQuery<PropertyOfficeUser> typedQuery = super.entityManager.createQuery(hql.toString(), PropertyOfficeUser.class);
		typedQuery.setParameter("userName", userName);
		return typedQuery.getResultList();
	}
	@Override
	public List<PropertyOfficeUser> findByCommunitys(List<String> communityNumbers) {
		if (communityNumbers ==null||communityNumbers.size()<=0) {
			return null;
		}
		StringBuffer sb =new StringBuffer(" from PropertyOfficeUser access where communityNumber in (:communityNumber)");
		TypedQuery<PropertyOfficeUser> query =this.entityManager.createQuery(sb.toString(),PropertyOfficeUser.class);
		query.setParameter("communityNumber", communityNumbers);
		return query.getResultList();
	}
}
