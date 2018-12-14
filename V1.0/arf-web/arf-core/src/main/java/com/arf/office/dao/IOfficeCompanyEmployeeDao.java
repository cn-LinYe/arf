package com.arf.office.dao;

import java.util.List;

import com.arf.core.dao.BaseDao;
import com.arf.office.entity.OfficeCompanyEmployee;

public interface IOfficeCompanyEmployeeDao extends
		BaseDao<OfficeCompanyEmployee, Long> {

	OfficeCompanyEmployee findByUsernameCompanyNumber(String userName,
			String companyNumber);

	List<OfficeCompanyEmployee> findByCompanyNumber(String companyNumber);

}
