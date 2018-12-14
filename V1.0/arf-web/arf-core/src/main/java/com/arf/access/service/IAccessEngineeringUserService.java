package com.arf.access.service;

import com.arf.access.entity.AccessEngineeringUser;
import com.arf.core.service.BaseService;

public interface IAccessEngineeringUserService extends BaseService<AccessEngineeringUser, Long>{

	/**
	 * 根据用户名密码查找
	 * @param userName
	 * @param passWord
	 * @return
	 */
	AccessEngineeringUser findByUser(String userName,String passWord);
}
