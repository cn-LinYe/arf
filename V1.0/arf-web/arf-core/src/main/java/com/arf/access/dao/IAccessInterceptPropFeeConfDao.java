package com.arf.access.dao;

import com.arf.access.entity.AccessInterceptPropFeeConf;
import com.arf.core.dao.BaseDao;

public interface IAccessInterceptPropFeeConfDao 
		extends BaseDao<AccessInterceptPropFeeConf, Long> {

	/**
	 * 根据小区查询
	 * @param communityNumber
	 * @return
	 */
	AccessInterceptPropFeeConf findByCommunityNumber(String communityNumber);

	
	
}
