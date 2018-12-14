package com.arf.insurance.service;

import java.util.List;

import com.arf.core.service.BaseService;
import com.arf.insurance.entity.InsuranceCompany;

public interface IInsuranceCompanyService extends BaseService<InsuranceCompany, Long>{
	/**根据状态查找满足条件的保险公司
	 * @param status
	 * @return
	 */
	public List<InsuranceCompany> findCompanyByStatus(InsuranceCompany.Status status);
}
