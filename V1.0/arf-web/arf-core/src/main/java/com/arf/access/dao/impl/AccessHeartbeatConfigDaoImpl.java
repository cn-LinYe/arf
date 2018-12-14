package com.arf.access.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.arf.access.dao.IAccessHeartbeatConfigDao;
import com.arf.access.entity.AccessHeartbeatConfig;
import com.arf.access.entity.AccessHeartbeatConfig.Status;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("accessHeartbeatConfigDao")
public class AccessHeartbeatConfigDaoImpl extends BaseDaoImpl<AccessHeartbeatConfig, Long> implements IAccessHeartbeatConfigDao{

	@Override
	public List<AccessHeartbeatConfig> findByAccessNumAndStatus(String accessMac, Status status) {
		StringBuffer hql = new StringBuffer("from AccessHeartbeatConfig where accessMac = :accessMac");
		hql.append(" and status = :status");
		TypedQuery<AccessHeartbeatConfig> typedQuery = super.entityManager.createQuery(hql.toString(), AccessHeartbeatConfig.class);
		
		typedQuery.setParameter("accessMac", accessMac);
		typedQuery.setParameter("status", status);
		
		List<AccessHeartbeatConfig> list = typedQuery.getResultList();
		
		return list;
	}
	
}
