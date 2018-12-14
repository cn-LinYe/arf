package com.arf.insurance.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.insurance.dao.IInsuranceTypeDao;
import com.arf.insurance.entity.InsuranceType;
import com.arf.insurance.entity.InsuranceType.Status;
import com.arf.insurance.service.IInsuranceTypeService;

@Repository("insuranceTypeServiceImpl")
public class InsuranceTypeServiceImpl extends BaseServiceImpl<InsuranceType, Long> implements IInsuranceTypeService  {

	@Resource(name = "insuranceTypeDaoImpl")
	private IInsuranceTypeDao insuranceTypeDao;
	
	@Override
	protected BaseDao<InsuranceType, Long> getBaseDao() {
		return insuranceTypeDao;
	}

	@Override
	public List<InsuranceType> findTypeByStatus(Status status) {		
		return insuranceTypeDao.findTypeByStatus(status);
	}

}
