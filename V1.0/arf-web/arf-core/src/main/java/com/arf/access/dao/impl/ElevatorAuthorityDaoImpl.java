package com.arf.access.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.arf.access.dao.IElevatorAuthorityDao;
import com.arf.access.entity.ElevatorAuthority;
import com.arf.access.entity.ElevatorAuthority.Status;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("elevatorAuthorityDao")
public class ElevatorAuthorityDaoImpl extends BaseDaoImpl<ElevatorAuthority, Long> implements IElevatorAuthorityDao{

	@Override
	public List<ElevatorAuthority> findByUserName(String userName, Status status) {
		StringBuffer sb = new StringBuffer("from ElevatorAuthority where 1=1");
		sb.append(" and userName = :userName");
		if(status!=null){
			sb.append(" and status = :status");
		}
		TypedQuery<ElevatorAuthority> query = this.entityManager.createQuery(sb.toString(), ElevatorAuthority.class);
		query.setParameter("userName", userName);
		if(status!=null){
			query.setParameter("status", status);
		}
		return query.getResultList();
	}

}
