package com.arf.axd_b2b.service;

import com.arf.axd_b2b.entity.AxdUserId;
import com.arf.core.service.BaseService;

public interface IAxdUserIdService extends BaseService<AxdUserId, Long> {

	AxdUserId findByUseridAndBusinessnum(String axdUserID, String businessNum);
	
	AxdUserId findByUserNameAndBusinessnum(String userName, String businessNum);
}
