package com.arf.activity.parkrate.dao;

import com.arf.activity.parkrate.entity.CommunityActivity;
import com.arf.core.dao.BaseDao;

public interface ICommunityActivityDao extends BaseDao<CommunityActivity, Long> {

	CommunityActivity findByCommunity(String communityNumber);

	

}
