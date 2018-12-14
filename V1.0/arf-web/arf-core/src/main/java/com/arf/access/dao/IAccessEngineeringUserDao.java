package com.arf.access.dao;

import com.arf.access.entity.AccessEngineeringUser;
import com.arf.core.dao.BaseDao;

public interface IAccessEngineeringUserDao extends BaseDao<AccessEngineeringUser, Long>{
	/**
	 * 根据用户名密码查找
	 * @param userName
	 * @param passWord
	 * @return
	 */
	AccessEngineeringUser findByUser(String userName,String passWord);
}
