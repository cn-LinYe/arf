package com.arf.office.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.office.dao.IOfficeCompanyAttendanceGroupDao;
import com.arf.office.entity.OfficeCompanyAttendanceGroup;
import com.arf.office.entity.OfficeCompanyEquipmentRelation;

@Repository("officeCompanyAttendanceGroupDaoImpl")
public class OfficeCompanyAttendanceGroupDaoImpl extends
		BaseDaoImpl<OfficeCompanyAttendanceGroup, Long> implements
		IOfficeCompanyAttendanceGroupDao {

	@Override
	public List<OfficeCompanyAttendanceGroup> findByCompanyNumber(
			String companyNumber) {
		StringBuffer hql = new StringBuffer("from OfficeCompanyAttendanceGroup a where 1 = 1 and a.companyNumber = :companyNumber");
		TypedQuery<OfficeCompanyAttendanceGroup> typedQuery = entityManager.createQuery(hql.toString(), OfficeCompanyAttendanceGroup.class);
		typedQuery.setParameter("companyNumber", companyNumber);
		return typedQuery.getResultList();
	}
	
}
