package com.arf.access.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.access.dao.IAccessNHouseholdsDao;
import com.arf.access.entity.AccessNHouseholds;
import com.arf.access.service.IAccessNHouseholdsService;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;

@Service("accessNHouseholdsService")
public class AccessNHouseholdsServiceImpl extends BaseServiceImpl<AccessNHouseholds, Long> implements IAccessNHouseholdsService{

	@Resource(name="accessNHouseholdsDao")
	IAccessNHouseholdsDao accessNHouseholdsDao;
	
	@Override
	protected BaseDao<AccessNHouseholds, Long> getBaseDao() {
		return accessNHouseholdsDao;
	}
	
	@Override
	public List<Map<String,Object>> findByCommunityAndUserName(String userName, String communityNo) {
		return accessNHouseholdsDao.findByCommunityAndUserName(userName, communityNo);
	}

}
