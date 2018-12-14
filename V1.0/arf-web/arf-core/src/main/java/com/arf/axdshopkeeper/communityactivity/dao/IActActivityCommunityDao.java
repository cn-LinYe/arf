package com.arf.axdshopkeeper.communityactivity.dao;

import java.util.List;

import com.arf.axdshopkeeper.communityactivity.entity.ActActivityCommunity;
import com.arf.core.dao.BaseDao;

public interface IActActivityCommunityDao extends
		BaseDao<ActActivityCommunity, Long> {

	List<ActActivityCommunity> findByActivityId(Long activityId);

}
