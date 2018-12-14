package com.arf.insurance.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.insurance.dao.IInsuranceTypeDao;
import com.arf.insurance.entity.InsuranceType;
import com.arf.insurance.entity.InsuranceType.Status;

@Repository("insuranceTypeDaoImpl")
public class InsuranceTypeDaoImpl extends BaseDaoImpl<InsuranceType, Long> implements IInsuranceTypeDao {

	@Override
	public List<InsuranceType> findTypeByStatus(Status status) {
		String hql = "from InsuranceType i where 1=1";
		if(status!=null){			
			hql=hql.concat(" and i.status=:status");
		}
		TypedQuery<InsuranceType> query = entityManager.createQuery(hql, InsuranceType.class);		
		if(status!=null){			
			query.setParameter("status", (byte)status.ordinal());
		}
		return query.getResultList();
	}
}
