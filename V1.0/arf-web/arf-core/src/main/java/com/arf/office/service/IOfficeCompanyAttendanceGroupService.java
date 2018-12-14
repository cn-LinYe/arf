package com.arf.office.service;

import java.util.List;

import com.arf.core.service.BaseService;
import com.arf.office.entity.OfficeCompanyAttendanceGroup;

public interface IOfficeCompanyAttendanceGroupService extends
		BaseService<OfficeCompanyAttendanceGroup, Long> {

	List<OfficeCompanyAttendanceGroup> findByCompanyNumber(String companyNumber);

}
