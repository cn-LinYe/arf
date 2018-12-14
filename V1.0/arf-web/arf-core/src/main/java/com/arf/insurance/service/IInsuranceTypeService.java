package com.arf.insurance.service;

import java.util.List;

import com.arf.core.service.BaseService;
import com.arf.insurance.entity.InsuranceType;

public interface IInsuranceTypeService extends BaseService<InsuranceType, Long>{
	/**根据状态查找满足条件的险种
	 * @param status
	 * @return
	 */
	public List<InsuranceType> findTypeByStatus(InsuranceType.Status status);
}
