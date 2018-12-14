package com.arf.access.dao;

import com.arf.access.entity.AccessInterceptPropFeeWhitelist;
import com.arf.core.dao.BaseDao;

public interface IAccessInterceptPropFeeWhitelistDao 
		extends BaseDao<AccessInterceptPropFeeWhitelist, Long> {

	/**
	 * 根据小区编号和用户名查询
	 * @param communityNumber
	 * @param userName
	 * @return
	 */
	AccessInterceptPropFeeWhitelist findByCommunityNumberUsername(
			String communityNumber, String userName);

	
	
}
