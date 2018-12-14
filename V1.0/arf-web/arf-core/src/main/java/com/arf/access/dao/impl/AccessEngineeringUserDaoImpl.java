package com.arf.access.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.arf.access.dao.IAccessEngineeringUserDao;
import com.arf.access.entity.AccessEngineeringUser;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("accessEngineeringUserDaoImpl")
public class AccessEngineeringUserDaoImpl extends BaseDaoImpl<AccessEngineeringUser, Long>implements IAccessEngineeringUserDao {

	@Override
	public AccessEngineeringUser findByUser(String userName, String password) {
		String hql = "from AccessEngineeringUser p where p.userName = :userName";
		if(password!=null){
			hql=hql.concat(" and p.passwrod = :passwrod");
		}
		TypedQuery<AccessEngineeringUser> query=this.entityManager.createQuery(hql, AccessEngineeringUser.class);
		query.setParameter("userName", userName);
		if(password!=null){			
			query.setParameter("password", password);
		}
		List<AccessEngineeringUser> records=query.getResultList();
		if(CollectionUtils.isEmpty(records)){
			return null;
		}else{
			return records.get(0);
		}
	}

}
