package com.arf.access.dao;


import java.util.List;

import com.arf.access.entity.AccessOpenSetting;
import com.arf.core.dao.BaseDao;

public interface IAccessOpenSettingDao extends BaseDao<AccessOpenSetting, Long>{
	/**
	 * 根据accessid查询
	 * 
	 * @param accessId
	 * @param userName 
	 * @return
	 */
	AccessOpenSetting findByAccessId(Long accessId, String userName);

	/**
	 * 根据用户名查询
	 * @param userName
	 * @return
	 */
	List<AccessOpenSetting> findByUserName(String userName);
}
