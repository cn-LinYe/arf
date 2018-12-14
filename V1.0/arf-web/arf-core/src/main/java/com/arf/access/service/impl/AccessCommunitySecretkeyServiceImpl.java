package com.arf.access.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.access.dao.IAccessCommunitySecretkeyDao;
import com.arf.access.entity.AccessCommunitySecretkey;
import com.arf.access.service.IAccessCommunitySecretkeyService;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;

@Deprecated
@Service("accessCommunitySecretkeyServiceImpl")
public class AccessCommunitySecretkeyServiceImpl extends
		BaseServiceImpl<AccessCommunitySecretkey, Long> implements
		IAccessCommunitySecretkeyService {

	@Resource(name = "accessCommunitySecretkeyDaoImpl")
	private IAccessCommunitySecretkeyDao accessCommunitySecretkeyDaoImpl;
	
	@Override
	protected BaseDao<AccessCommunitySecretkey, Long> getBaseDao() {
		return accessCommunitySecretkeyDaoImpl;
	}

	@Override
	public AccessCommunitySecretkey findByCommunityNumber(String communityNumber){
		return accessCommunitySecretkeyDaoImpl.findByCommunityNumber(communityNumber);
	}
	
}
