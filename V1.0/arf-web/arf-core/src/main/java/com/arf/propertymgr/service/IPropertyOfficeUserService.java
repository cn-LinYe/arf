package com.arf.propertymgr.service;


import java.util.List;

import com.arf.access.entity.AccessManagement;
import com.arf.core.service.BaseService;
import com.arf.propertymgr.entity.PropertyOffice;
import com.arf.propertymgr.entity.PropertyOfficeUser;

public interface IPropertyOfficeUserService extends BaseService<PropertyOfficeUser, Long> {

	/**
	 * 查找小区下的办公室人员
	 * @param communityNumber
	 * @return
	 */
	public List<PropertyOfficeUser> findByCommunityNumber(String communityNumber);
	/**
	 * 查找小区下的办公室人员
	 * @param userName
	 * @return
	 */
	public List<PropertyOfficeUser> findByUserName(String userName);
	/**
	 * 查找小区下的办公室人员
	 * @param communityNumbers
	 * @return
	 */
	public List<PropertyOfficeUser> findByCommunitys(List<String> communityNumbers);
	
}
