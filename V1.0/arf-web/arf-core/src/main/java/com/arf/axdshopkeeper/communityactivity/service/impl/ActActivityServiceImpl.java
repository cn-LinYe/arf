package com.arf.axdshopkeeper.communityactivity.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.axdshopkeeper.communityactivity.dao.IActActivityDao;
import com.arf.axdshopkeeper.communityactivity.entity.ActActivity;
import com.arf.axdshopkeeper.communityactivity.service.IActActivityService;
import com.arf.base.PageResult;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;

@Service("actActivityServiceImpl")
public class ActActivityServiceImpl extends BaseServiceImpl<ActActivity, Long> implements
		IActActivityService {

	@Resource(name = "actActivityDaoImpl")
	private IActActivityDao actActivityDaoImpl;
	
	@Override
	protected BaseDao<ActActivity, Long> getBaseDao() {
		return actActivityDaoImpl;
	}

	@Override
	public PageResult<ActActivity> findListByCondition(
			String communityNumber, Integer pageNo, Integer pageSize,
			boolean findTimeout) {
		return actActivityDaoImpl.findListByCondition(communityNumber,pageNo,pageSize,findTimeout);
	}

	@Override
	public List<ActActivity> findByKeyword(String keyword,
			String communityNumber) {
		return actActivityDaoImpl.findByKeyword(keyword,communityNumber);
	}

}
