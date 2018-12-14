package com.arf.access.dao;

import java.util.List;
import java.util.Map;

import com.arf.access.entity.AccessNHouseholds;
import com.arf.core.dao.BaseDao;

public interface IAccessNHouseholdsDao  extends BaseDao<AccessNHouseholds, Long>{

	/***
	 * 根据用户名查找住户
	 * @param userName
	 * @return
	 */
	public List<Map<String,Object>> findByCommunityAndUserName(String userName,String communityNo);
}
