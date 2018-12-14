package com.arf.activity.childrensday.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.activity.childrensday.dao.IActivityChildren61WorksDao;
import com.arf.activity.childrensday.entity.ActivityChildren61Works;
import com.arf.activity.childrensday.service.IActivityChildren61WorksService;
import com.arf.core.Page;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;

@Service("activityChildren61WorksServiceImpl")
public class ActivityChildren61WorksServiceImpl extends
		BaseServiceImpl<ActivityChildren61Works, Long> implements
		IActivityChildren61WorksService {

	@Resource(name = "activityChildren61WorksDaoImpl")
	private IActivityChildren61WorksDao activityChildren61WorksDaoImpl;
	
	@Override
	protected BaseDao<ActivityChildren61Works, Long> getBaseDao() {
		return activityChildren61WorksDaoImpl;
	}

	@Override
	public ActivityChildren61Works findByWorksNum(Integer worksNum) {
		return activityChildren61WorksDaoImpl.findByWorksNum(worksNum);
	}

	@Override
	public Page<ActivityChildren61Works> findByCondition(Integer pageNo,
			Integer pageSize, String keyword,Integer orderBy) {
		return activityChildren61WorksDaoImpl.findByCondition(pageNo,pageSize,keyword,orderBy);
	}

	@Override
	public List<ActivityChildren61Works> findAllByOrder() {
		return activityChildren61WorksDaoImpl.findAllByOrder();
	}
	
}
