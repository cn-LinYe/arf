package com.arf.activity.parkrate.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.activity.parkrate.dao.ICommunityActivityDao;
import com.arf.activity.parkrate.entity.CommunityActivity;
import com.arf.activity.parkrate.service.ICommunityActivityService;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;

@Service("communityActivityServiceImpl")
public class CommunityActivityServiceImpl extends BaseServiceImpl<CommunityActivity, Long>
		implements ICommunityActivityService {

	@Resource(name = "communityActivityDaoImpl")
	private ICommunityActivityDao communityActivityDaoImpl;
	
	@Override
	protected BaseDao<CommunityActivity, Long> getBaseDao() {
		return communityActivityDaoImpl;
	}

	@Override
	public CommunityActivity findByCommunity(String communityNumber) {
		return communityActivityDaoImpl.findByCommunity(communityNumber);
	}

}
