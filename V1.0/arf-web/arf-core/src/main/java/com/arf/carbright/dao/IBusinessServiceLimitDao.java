package com.arf.carbright.dao;

import com.arf.carbright.entity.BusinessServiceLimit;
import com.arf.core.dao.BaseDao;

public interface IBusinessServiceLimitDao extends BaseDao<BusinessServiceLimit, Long>{

	
	/**
	 * 通过商户编号查找
	 * @param businessNum
	 * @return
	 */
	BusinessServiceLimit findByBusinessNum(Integer businessNum);
}
