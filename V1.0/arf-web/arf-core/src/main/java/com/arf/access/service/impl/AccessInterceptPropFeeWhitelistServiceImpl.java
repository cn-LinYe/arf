package com.arf.access.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.access.dao.IAccessInterceptPropFeeWhitelistDao;
import com.arf.access.entity.AccessInterceptPropFeeWhitelist;
import com.arf.access.service.IAccessInterceptPropFeeWhitelistService;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;

@Service("accessInterceptPropFeeWhitelistServiceImpl")
public class AccessInterceptPropFeeWhitelistServiceImpl 
		extends BaseServiceImpl<AccessInterceptPropFeeWhitelist, Long> 
		implements IAccessInterceptPropFeeWhitelistService {

	@Resource(name = "accessInterceptPropFeeWhitelistDaoImpl")
	private IAccessInterceptPropFeeWhitelistDao accessInterceptPropFeeWhitelistDaoImpl;
	
	@Override
	protected BaseDao<AccessInterceptPropFeeWhitelist, Long> getBaseDao() {
		return accessInterceptPropFeeWhitelistDaoImpl;
	}

	@Override
	public AccessInterceptPropFeeWhitelist findByCommunityNumberUsername(
			String communityNumber, String userName) {
		return accessInterceptPropFeeWhitelistDaoImpl.findByCommunityNumberUsername(communityNumber,userName);
	}

	
	
}
