package com.arf.office.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.office.dao.IOfficeCompanyAttendanceGroupDao;
import com.arf.office.entity.OfficeCompanyAttendanceGroup;
import com.arf.office.service.IOfficeCompanyAttendanceGroupService;

@Service("officeCompanyAttendanceGroupServiceImpl")
public class OfficeCompanyAttendanceGroupServiceImpl extends
		BaseServiceImpl<OfficeCompanyAttendanceGroup, Long> implements
		IOfficeCompanyAttendanceGroupService {

	@Resource(name = "officeCompanyAttendanceGroupDaoImpl")
	private IOfficeCompanyAttendanceGroupDao officeCompanyAttendanceGroupDaoImpl;
	
	@Override
	protected BaseDao<OfficeCompanyAttendanceGroup, Long> getBaseDao() {
		return officeCompanyAttendanceGroupDaoImpl;
	}

	@Override
	public List<OfficeCompanyAttendanceGroup> findByCompanyNumber(
			String companyNumber) {
		return officeCompanyAttendanceGroupDaoImpl.findByCompanyNumber(companyNumber);
	}

}
