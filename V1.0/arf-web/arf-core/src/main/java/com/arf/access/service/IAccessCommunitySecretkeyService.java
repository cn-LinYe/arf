package com.arf.access.service;

import com.arf.access.entity.AccessCommunitySecretkey;
import com.arf.core.service.BaseService;

@Deprecated
public interface IAccessCommunitySecretkeyService extends
		BaseService<AccessCommunitySecretkey, Long> {

	/**
	 * 根据小区编号查询
	 * @param communityNumber
	 * @return
	 */
	public AccessCommunitySecretkey findByCommunityNumber(String communityNumber);
	
}
