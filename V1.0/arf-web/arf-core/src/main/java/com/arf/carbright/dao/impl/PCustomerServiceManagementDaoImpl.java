package com.arf.carbright.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.arf.carbright.dao.IPCustomerServiceManagementDao;
import com.arf.carbright.entity.PCustomerServiceManagement;
import com.arf.carbright.entity.PCustomerServiceManagement.Department;
import com.arf.carbright.entity.PCustomerServiceManagement.MessageType;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("pCustomerServiceManagementDaoImpl")
public class PCustomerServiceManagementDaoImpl extends BaseDaoImpl<PCustomerServiceManagement, Long> implements IPCustomerServiceManagementDao{

	@Override
	public List<PCustomerServiceManagement> findByMessagType(MessageType messageType) {
		String hql = "from PCustomerServiceManagement r where ( r.messageType = :messageType";
		if(messageType.ordinal()>MessageType.ALL.ordinal()){//如果等于所有权限直接
			hql=hql.concat(" or r.messageType = :messageTypesign");
		}
		hql=hql.concat(" ) and r.status=:status");
		TypedQuery<PCustomerServiceManagement> query=this.entityManager.createQuery(hql, PCustomerServiceManagement.class);
		query.setParameter("messageType", messageType.ordinal());
		if(messageType.ordinal()>MessageType.ALL.ordinal()){//如果等于所有权限直接
			query.setParameter("messageTypesign", MessageType.ALL.ordinal());
		}
		query.setParameter("status", PCustomerServiceManagement.Status.Enable.ordinal());
		return query.getResultList();
	}
	
	@Override
	public List<PCustomerServiceManagement> findByDepartment(Department department){
		String hql = "from PCustomerServiceManagement r where r.department =:department";
		hql = hql.concat(" and r.status=:status");
		TypedQuery<PCustomerServiceManagement> query = this.entityManager.createQuery(hql, PCustomerServiceManagement.class);
		query.setParameter("department", department);
		query.setParameter("status", PCustomerServiceManagement.Status.Enable.ordinal());
		return query.getResultList();
	}

	@Override
	public List<PCustomerServiceManagement> findPropertyMgrUsers(String communityNumber) {
		String hql = "from PCustomerServiceManagement r where r.department = :department and status = :status and find_in_set(:communityNumber,r.communities)>0";
		TypedQuery<PCustomerServiceManagement> query=this.entityManager.createQuery(hql, PCustomerServiceManagement.class);
		query.setParameter("department", PCustomerServiceManagement.Department.PropertyMGR);
		query.setParameter("communityNumber", communityNumber);
		query.setParameter("status", PCustomerServiceManagement.Status.Enable.ordinal());
		 return query.getResultList();
	}

	@Override
	public List<PCustomerServiceManagement> findByStatus(Integer status,String userName) {
		StringBuffer hql =new StringBuffer( "from PCustomerServiceManagement r where r.status = :status ");
		if (StringUtils.isNotBlank(userName)) {
			hql.append("and r.phone=:userName");
		}
		TypedQuery<PCustomerServiceManagement> query=this.entityManager.createQuery(hql.toString(), PCustomerServiceManagement.class);
		query.setParameter("status",status);
		if (StringUtils.isNotBlank(userName)) {
			query.setParameter("userName",userName);
		}
		 return query.getResultList();
	}

}
