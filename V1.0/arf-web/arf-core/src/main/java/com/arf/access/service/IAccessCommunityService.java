package com.arf.access.service;

import java.util.List;

import com.arf.access.entity.AccessCommunity;
import com.arf.core.service.BaseService;
import com.arf.propertymgr.entity.PropertyOfficeUser;

public interface IAccessCommunityService extends BaseService<AccessCommunity, Long>{

	/**
	 * 根据communityNumber查询
	 * @param AccessCommunity
	 * @return
	 */
	public AccessCommunity findByCommunityNumber(String communityNumber);
	
	/**
	 * 根据communityNumbers查询
	 * @param AccessCommunity
	 * @return
	 */
	public List<AccessCommunity> findByCommunityNumbers(List<String> communityNumbers);
	
}
