package com.arf.axdshopkeeper.communityactivity.service;

import java.util.List;

import com.arf.axdshopkeeper.communityactivity.entity.ActActivityCommunity;
import com.arf.core.service.BaseService;

public interface IActActivityCommunityService extends
		BaseService<ActActivityCommunity, Long> {

	List<ActActivityCommunity> findByActivityId(Long activityId);

}
