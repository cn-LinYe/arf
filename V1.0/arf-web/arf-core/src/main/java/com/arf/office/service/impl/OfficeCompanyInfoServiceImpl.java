package com.arf.office.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.office.dao.IOfficeCompanyInfoDao;
import com.arf.office.entity.OfficeCompanyInfo;
import com.arf.office.service.IOfficeCompanyInfoService;

@Service("officeCompanyInfoServiceImpl")
public class OfficeCompanyInfoServiceImpl extends
		BaseServiceImpl<OfficeCompanyInfo, Long> implements
		IOfficeCompanyInfoService {

	@Resource(name = "officeCompanyInfoDaoImpl")
	private IOfficeCompanyInfoDao officeCompanyInfoDaoImpl;
	
	@Override
	protected BaseDao<OfficeCompanyInfo, Long> getBaseDao() {
		return officeCompanyInfoDaoImpl;
	}

}
