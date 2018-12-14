package com.arf.access.dao.impl;



import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.arf.access.dao.IAccessOpenSettingDao;
import com.arf.access.entity.AccessOpenSetting;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("accessOpenSettingDao")
public class AccessOpenSettingDaoImpl extends BaseDaoImpl<AccessOpenSetting, Long> implements IAccessOpenSettingDao{

	@Override
	public AccessOpenSetting findByAccessId(Long accessId, String userName) {
		StringBuffer hql = new StringBuffer("from AccessOpenSetting where accessId = :accessId and userName = :userName");
		TypedQuery<AccessOpenSetting> typedQuery = super.entityManager.createQuery(hql.toString(), AccessOpenSetting.class);
		typedQuery.setParameter("accessId", accessId);
		typedQuery.setParameter("userName", userName);
		try{
			return typedQuery.getSingleResult();
		}catch (Exception e) {
			return null;
		}
	}
	@Override
	public List<AccessOpenSetting> findByUserName(String userName) {
		StringBuffer sb = new StringBuffer(" from AccessOpenSetting where userName = :userName");
		TypedQuery<AccessOpenSetting> query = this.entityManager.createQuery(sb.toString(), AccessOpenSetting.class);
		query.setParameter("userName", userName);
		return query.getResultList();
	}

}
