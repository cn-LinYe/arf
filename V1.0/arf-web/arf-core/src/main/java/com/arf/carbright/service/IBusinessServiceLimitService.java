package com.arf.carbright.service;

import com.arf.carbright.entity.BusinessServiceLimit;
import com.arf.core.service.BaseService;

public interface IBusinessServiceLimitService extends BaseService<BusinessServiceLimit, Long>{

	/**
	 * 通过商户编号查找
	 * @param businessNum
	 * @return
	 */
	BusinessServiceLimit findByBusinessNum(Integer businessNum);
}
