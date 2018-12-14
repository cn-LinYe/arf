package com.arf.access.service;

import com.arf.access.entity.AccessInterceptPropFeeConf;
import com.arf.core.service.BaseService;

public interface IAccessInterceptPropFeeConfService 
		extends BaseService<AccessInterceptPropFeeConf, Long> {

	/**
	 * 根据小区查询
	 * @param communityNumber
	 * @return
	 */
	AccessInterceptPropFeeConf findByCommunityNumber(String communityNumber);

}
