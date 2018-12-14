package com.arf.carbright.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.arf.carbright.dao.IPEngineeringMachineUserDao;
import com.arf.carbright.entity.PEngineeringMachineUser;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("pEngineeringMachineUserDaoImpl")
public class PEngineeringMachineUserDaoImpl extends BaseDaoImpl<PEngineeringMachineUser, Long> implements IPEngineeringMachineUserDao{
	@Override
	public PEngineeringMachineUser findByUser(String userName, String passwrod) {
		String hql = "from PEngineeringMachineUser p where p.userName = :userName";
		if(passwrod!=null){
			hql=hql.concat(" and p.passwrod = :passwrod");
		}
		TypedQuery<PEngineeringMachineUser> query=this.entityManager.createQuery(hql, PEngineeringMachineUser.class);
		query.setParameter("userName", userName);
		if(passwrod!=null){			
			query.setParameter("passwrod", passwrod);
		}
		List<PEngineeringMachineUser> records=query.getResultList();
		if(CollectionUtils.isEmpty(records)){
			return null;
		}else{
			return records.get(0);
		}
	}

	
}
