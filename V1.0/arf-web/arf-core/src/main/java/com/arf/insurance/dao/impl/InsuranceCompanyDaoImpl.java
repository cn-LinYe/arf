package com.arf.insurance.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.insurance.dao.IInsuranceCompanyDao;
import com.arf.insurance.entity.InsuranceCompany;

@Repository("insuranceCompanyDaoImpl")
public class InsuranceCompanyDaoImpl extends BaseDaoImpl<InsuranceCompany, Long> implements IInsuranceCompanyDao {

	@Override
	public List<InsuranceCompany> findCompanyByStatus(InsuranceCompany.Status status) {
		String hql = "from InsuranceCompany i where 1=1";
		if(status!=null){			
			hql=hql.concat(" and i.status=:status");
		}
		TypedQuery<InsuranceCompany> query = entityManager.createQuery(hql, InsuranceCompany.class);		
		if(status!=null){			
			query.setParameter("status", (byte)status.ordinal());
		}
		return query.getResultList();
	}


}
