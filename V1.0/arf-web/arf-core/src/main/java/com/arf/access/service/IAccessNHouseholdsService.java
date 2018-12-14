package com.arf.access.service;

import java.util.List;
import java.util.Map;

import com.arf.access.entity.AccessNHouseholds;
import com.arf.core.service.BaseService;

public interface IAccessNHouseholdsService extends BaseService<AccessNHouseholds, Long>{

	/***
	 * 根据用户名查找住户
	 * @param userName
	 * @return
	 */
	public List<Map<String,Object>> findByCommunityAndUserName(String userName,String communityNo);
}
