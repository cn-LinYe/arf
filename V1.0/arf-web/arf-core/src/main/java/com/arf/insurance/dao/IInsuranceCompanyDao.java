package com.arf.insurance.dao;

import java.util.List;

import com.arf.core.dao.BaseDao;
import com.arf.insurance.entity.InsuranceCompany;

public interface IInsuranceCompanyDao extends BaseDao<InsuranceCompany, Long>{

	/**根据状态查找满足条件的保险公司
	 * @param status
	 * @return
	 */
	public List<InsuranceCompany> findCompanyByStatus(InsuranceCompany.Status status);
}
