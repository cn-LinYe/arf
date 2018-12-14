package com.arf.axdshopkeeper.communityactivity.dao;

import com.arf.axdshopkeeper.communityactivity.entity.ActActivitySignUp;
import com.arf.core.dao.BaseDao;

public interface IActActivitySignUpDao extends BaseDao<ActActivitySignUp, Long> {

	ActActivitySignUp findByUsernameActivityId(String userName, Long activityId);

}
