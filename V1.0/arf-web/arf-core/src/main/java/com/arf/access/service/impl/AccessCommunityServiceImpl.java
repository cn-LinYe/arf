package com.arf.access.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.access.dao.IAccessCommunityDao;
import com.arf.access.entity.AccessCommunity;
import com.arf.access.service.IAccessCommunityService;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;

@Service("accessCommunityService")
public class AccessCommunityServiceImpl extends BaseServiceImpl<AccessCommunity, Long> implements IAccessCommunityService{

	@Resource(name="accessCommunityDao")
	IAccessCommunityDao accessCommunityDao;
	
	@Override
	protected BaseDao<AccessCommunity, Long> getBaseDao() {
		return accessCommunityDao;
	}

	@Override
	public AccessCommunity findByCommunityNumber(String communityNumber) {
		return accessCommunityDao.findByCommunityNumber(communityNumber);
	}

	@Override
	public List<AccessCommunity> findByCommunityNumbers(List<String> communityNumbers) {
		return accessCommunityDao.findByCommunityList(communityNumbers);
	}

}
