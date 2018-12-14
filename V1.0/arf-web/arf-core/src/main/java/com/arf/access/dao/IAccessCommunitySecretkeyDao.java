package com.arf.access.dao;

import com.arf.access.entity.AccessCommunitySecretkey;
import com.arf.core.dao.BaseDao;

@Deprecated
public interface IAccessCommunitySecretkeyDao extends 
		BaseDao<AccessCommunitySecretkey, Long>{

	/**
	 * 根据小区编号查询信息
	 * @param communityNumber
	 * @return
	 */
	public AccessCommunitySecretkey findByCommunityNumber(String communityNumber);
}
