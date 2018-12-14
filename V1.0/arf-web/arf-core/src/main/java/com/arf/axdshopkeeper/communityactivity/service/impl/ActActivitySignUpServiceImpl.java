package com.arf.axdshopkeeper.communityactivity.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.axdshopkeeper.communityactivity.dao.IActActivitySignUpDao;
import com.arf.axdshopkeeper.communityactivity.entity.ActActivitySignUp;
import com.arf.axdshopkeeper.communityactivity.service.IActActivitySignUpService;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;

@Service("actActivitySignUpServiceImpl")
public class ActActivitySignUpServiceImpl extends
		BaseServiceImpl<ActActivitySignUp, Long> implements
		IActActivitySignUpService {

	@Resource(name = "actActivitySignUpDaoImpl")
	private IActActivitySignUpDao actActivitySignUpDaoImpl;
	
	@Override
	protected BaseDao<ActActivitySignUp, Long> getBaseDao() {
		return actActivitySignUpDaoImpl;
	}

	@Override
	public ActActivitySignUp findByUsernameActivityId(String userName,
			Long activityId) {
		return actActivitySignUpDaoImpl.findByUsernameActivityId(userName,activityId);
	}

}
