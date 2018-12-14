package com.arf.insurance.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.insurance.dao.IInsuranceCompanyDao;
import com.arf.insurance.entity.InsuranceCompany;
import com.arf.insurance.entity.InsuranceCompany.Status;
import com.arf.insurance.service.IInsuranceCompanyService;

@Repository("insuranceCompanyServiceImpl")
public class InsuranceCompanyServiceImpl extends BaseServiceImpl<InsuranceCompany, Long> implements IInsuranceCompanyService  {

	@Resource(name = "insuranceCompanyDaoImpl")
	private IInsuranceCompanyDao insuranceCompanyDao;
	
	@Override
	protected BaseDao<InsuranceCompany, Long> getBaseDao() {
		return insuranceCompanyDao;
	}

	@Override
	public List<InsuranceCompany> findCompanyByStatus(Status status) {		
		return insuranceCompanyDao.findCompanyByStatus(status);
	}

}
