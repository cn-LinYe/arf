package com.arf.activity.parkrate.service;

import com.arf.activity.parkrate.entity.CommunityActivity;
import com.arf.core.service.BaseService;

public interface ICommunityActivityService extends BaseService<CommunityActivity, Long> {

	CommunityActivity findByCommunity(String communityNumber);

}
