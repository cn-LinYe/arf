package com.arf.office.service;

import java.util.List;

import com.arf.core.service.BaseService;
import com.arf.office.entity.OfficeCompanyEmployee;

public interface IOfficeCompanyEmployeeService extends
		BaseService<OfficeCompanyEmployee, Long> {

	OfficeCompanyEmployee findByUsernameCompanyNumber(String userName,
			String companyNumber);

	List<OfficeCompanyEmployee> findByCompanyNumber(String companyNumber);

}
