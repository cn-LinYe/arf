package com.arf.smart.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.smart.dao.UserCustomFunctionRecordDao;
import com.arf.smart.entity.UserCustomFunctionRecord;
import com.arf.smart.service.UserCustomFunctionRecordService;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;

@Service("userCustomFunctionRecordService")
public class UserCustomFunctionRecordServiceImpl extends BaseServiceImpl<UserCustomFunctionRecord, Long> implements UserCustomFunctionRecordService{

	@Resource(name="userCustomFunctionRecordDao")
	UserCustomFunctionRecordDao userCustomFunctionRecordDao;
	
	@Override
	protected BaseDao<UserCustomFunctionRecord, Long> getBaseDao() {
		return userCustomFunctionRecordDao;
	}

	@Override
	public UserCustomFunctionRecord findByUserName(String userName,String cityCode,String communityNumber) {
		return userCustomFunctionRecordDao.findByUserName(userName, cityCode,communityNumber);
	}

}
