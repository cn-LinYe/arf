package com.arf.propertymgr.dao;


import java.util.List;

import com.arf.core.dao.BaseDao;
import com.arf.propertymgr.entity.PropertyOffice;
import com.arf.propertymgr.entity.PropertyOfficeUser;

public interface IPropertyOfficeUserDao extends BaseDao<PropertyOfficeUser, Long> {
	/**
	 * 查找小区下的办公室人员
	 * @param communityNumber
	 * @return
	 */
	List<PropertyOfficeUser> findByCommunityNumber(String communityNumber);
	/**
	 * 查找小区下的办公室人员
	 * @param userName
	 * @return
	 */
	List<PropertyOfficeUser> findByUserName(String userName);
	
	List<PropertyOfficeUser> findByCommunitys(List<String> communityNumbers);

	
	
}
