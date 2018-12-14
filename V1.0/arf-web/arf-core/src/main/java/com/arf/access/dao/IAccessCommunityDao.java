package com.arf.access.dao;

import java.util.List;

import com.arf.access.entity.AccessCommunity;
import com.arf.core.dao.BaseDao;

public interface IAccessCommunityDao extends BaseDao<AccessCommunity, Long>{

	/**
	 * 根据communityNumber查询
	 * @param AccessCommunity
	 * @return
	 */
	public AccessCommunity findByCommunityNumber(String communityNumber);
	
	public List<AccessCommunity> findByCommunityList(List<String> communityList);
}
