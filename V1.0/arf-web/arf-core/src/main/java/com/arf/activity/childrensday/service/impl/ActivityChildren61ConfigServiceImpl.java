package com.arf.activity.childrensday.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.activity.childrensday.dao.IActivityChildren61ConfigDao;
import com.arf.activity.childrensday.entity.ActivityChildren61Config;
import com.arf.activity.childrensday.service.IActivityChildren61ConfigService;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;

@Service("activityChildren61ConfigServiceImpl")
public class ActivityChildren61ConfigServiceImpl extends
		BaseServiceImpl<ActivityChildren61Config, Long> implements
		IActivityChildren61ConfigService {

	@Resource(name = "activityChildren61ConfigDaoImpl")
	private IActivityChildren61ConfigDao activityChildren61ConfigDaoImpl;
	
	@Override
	protected BaseDao<ActivityChildren61Config, Long> getBaseDao() {
		return activityChildren61ConfigDaoImpl;
	}

	
	
}
