package com.arf.axdshopkeeper.communityactivity.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.axdshopkeeper.communityactivity.dao.IActActivityCommunityDao;
import com.arf.axdshopkeeper.communityactivity.entity.ActActivityCommunity;
import com.arf.axdshopkeeper.communityactivity.service.IActActivityCommunityService;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;

@Service("actActivityCommunityServiceImpl")
public class ActActivityCommunityServiceImpl extends
		BaseServiceImpl<ActActivityCommunity, Long> implements
		IActActivityCommunityService {
	
	@Resource(name = "actActivityCommunityDaoImpl")
	private IActActivityCommunityDao actActivityCommunityDaoImpl;

	@Override
	protected BaseDao<ActActivityCommunity, Long> getBaseDao() {
		return actActivityCommunityDaoImpl;
	}

	@Override
	public List<ActActivityCommunity> findByActivityId(Long activityId) {
		return actActivityCommunityDaoImpl.findByActivityId(activityId);
	}

}
