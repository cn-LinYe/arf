package com.arf.office.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.office.dao.IOfficeCompanyEmployeeDao;
import com.arf.office.entity.OfficeCompanyEmployee;
import com.arf.office.service.IOfficeCompanyEmployeeService;

@Service("officeCompanyEmployeeServiceImpl")
public class OfficeCompanyEmployeeServiceImpl extends
		BaseServiceImpl<OfficeCompanyEmployee, Long> implements
		IOfficeCompanyEmployeeService {

	@Resource(name = "officeCompanyEmployeeDaoimpl")
	private IOfficeCompanyEmployeeDao officeCompanyEmployeeDaoimpl;
	
	@Override
	protected BaseDao<OfficeCompanyEmployee, Long> getBaseDao() {
		return officeCompanyEmployeeDaoimpl;
	}

	@Override
	public OfficeCompanyEmployee findByUsernameCompanyNumber(String userName,
			String companyNumber) {
		return officeCompanyEmployeeDaoimpl.findByUsernameCompanyNumber(userName,companyNumber);
	}

	@Override
	public List<OfficeCompanyEmployee> findByCompanyNumber(String companyNumber) {
		return officeCompanyEmployeeDaoimpl.findByCompanyNumber(companyNumber);
	}

}
