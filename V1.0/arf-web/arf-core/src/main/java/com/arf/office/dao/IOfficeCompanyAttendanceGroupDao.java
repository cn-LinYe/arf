package com.arf.office.dao;

import java.util.List;

import com.arf.core.dao.BaseDao;
import com.arf.office.entity.OfficeCompanyAttendanceGroup;

public interface IOfficeCompanyAttendanceGroupDao extends
		BaseDao<OfficeCompanyAttendanceGroup, Long> {

	List<OfficeCompanyAttendanceGroup> findByCompanyNumber(String companyNumber);

}
