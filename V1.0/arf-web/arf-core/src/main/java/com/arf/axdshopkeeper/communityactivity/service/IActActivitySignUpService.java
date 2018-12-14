package com.arf.axdshopkeeper.communityactivity.service;

import com.arf.axdshopkeeper.communityactivity.entity.ActActivitySignUp;
import com.arf.core.service.BaseService;

public interface IActActivitySignUpService extends
		BaseService<ActActivitySignUp, Long> {

	ActActivitySignUp findByUsernameActivityId(String userName, Long activityId);
	
	

}
