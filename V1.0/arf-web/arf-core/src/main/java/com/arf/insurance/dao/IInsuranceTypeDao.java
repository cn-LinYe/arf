package com.arf.insurance.dao;

import java.util.List;

import com.arf.core.dao.BaseDao;
import com.arf.insurance.entity.InsuranceType;

public interface IInsuranceTypeDao extends BaseDao<InsuranceType, Long>{
	/**根据状态查找满足条件的险种
	 * @param status
	 * @return
	 */
	public List<InsuranceType> findTypeByStatus(InsuranceType.Status status);
}
