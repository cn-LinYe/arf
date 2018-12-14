package com.arf.access.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.access.dao.IAccessEngineeringUserDao;
import com.arf.access.entity.AccessEngineeringUser;
import com.arf.access.service.IAccessEngineeringUserService;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;

@Service("accessEngineeringUserServiceImpl")
public class AccessEngineeringUserServiceImpl extends BaseServiceImpl<AccessEngineeringUser, Long> implements IAccessEngineeringUserService{

	@Resource(name="accessEngineeringUserDaoImpl")
	private IAccessEngineeringUserDao accessEngineeringUserDao;
	
	@Override
	protected BaseDao<AccessEngineeringUser, Long> getBaseDao() {
		return accessEngineeringUserDao;
	}

	@Override
	public AccessEngineeringUser findByUser(String userName, String passWord) {
		return accessEngineeringUserDao.findByUser(userName, passWord);
	}
}
