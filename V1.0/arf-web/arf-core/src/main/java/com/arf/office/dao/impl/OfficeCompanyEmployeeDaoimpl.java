package com.arf.office.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.office.dao.IOfficeCompanyEmployeeDao;
import com.arf.office.entity.OfficeCompanyEmployee;
import com.arf.office.entity.OfficeCompanyEquipmentRelation;

@Repository("officeCompanyEmployeeDaoimpl")
public class OfficeCompanyEmployeeDaoimpl extends
		BaseDaoImpl<OfficeCompanyEmployee, Long> implements
		IOfficeCompanyEmployeeDao {

	@Override
	public OfficeCompanyEmployee findByUsernameCompanyNumber(String userName,
			String companyNumber) {
		StringBuffer hql = new StringBuffer("from OfficeCompanyEmployee a where 1 = 1 and a.userName = :userName and companyNumber = :companyNumber");
		TypedQuery<OfficeCompanyEmployee> typedQuery = entityManager.createQuery(hql.toString(), OfficeCompanyEmployee.class);
		typedQuery.setParameter("userName", userName);
		typedQuery.setParameter("companyNumber", companyNumber);
		try {
			return typedQuery.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public List<OfficeCompanyEmployee> findByCompanyNumber(String companyNumber) {
		StringBuffer hql = new StringBuffer("from OfficeCompanyEmployee a where 1 = 1 and companyNumber = :companyNumber");
		TypedQuery<OfficeCompanyEmployee> typedQuery = entityManager.createQuery(hql.toString(), OfficeCompanyEmployee.class);
		typedQuery.setParameter("companyNumber", companyNumber);
		return typedQuery.getResultList();
	}

}
