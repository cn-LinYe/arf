package com.arf.office.dao.impl;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.office.dao.IOfficeCompanyInfoDao;
import com.arf.office.entity.OfficeCompanyInfo;

@Repository("officeCompanyInfoDaoImpl")
public class OfficeCompanyInfoDaoImpl extends
		BaseDaoImpl<OfficeCompanyInfo, Long> implements IOfficeCompanyInfoDao {

}
