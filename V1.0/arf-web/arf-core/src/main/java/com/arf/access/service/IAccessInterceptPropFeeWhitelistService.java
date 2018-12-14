package com.arf.access.service;

import com.arf.access.entity.AccessInterceptPropFeeWhitelist;
import com.arf.core.service.BaseService;

public interface IAccessInterceptPropFeeWhitelistService extends
		BaseService<AccessInterceptPropFeeWhitelist, Long> {

	/**
	 * 根据小区编号和用户名查询
	 * @param communityNumber
	 * @param userName
	 * @return
	 */
	AccessInterceptPropFeeWhitelist findByCommunityNumberUsername(
			String communityNumber, String userName);

	
	
}
