package com.arf.member.dao;

import com.arf.core.dao.BaseDao;
import com.arf.member.entity.AxdConfig;

public interface IAxdConfigDao extends BaseDao<AxdConfig, Long> {

	/**
	 * 根据用户名和车牌号查询安心点配置表axd_config
	 * @param userName
	 * @param license
	 * @return
	 */
	AxdConfig findByUsernameAndLicense(String userName, String license);

	
}
