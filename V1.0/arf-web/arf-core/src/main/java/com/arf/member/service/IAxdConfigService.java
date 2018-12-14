package com.arf.member.service;

import com.arf.core.service.BaseService;
import com.arf.member.entity.AxdConfig;

public interface IAxdConfigService extends BaseService<AxdConfig, Long> {

	/**
	 * 根据用户名和车牌号查询安心点配置表axd_config
	 * @param userName
	 * @param license
	 * @return
	 */
	AxdConfig findByUsernameAndLicense(String userName, String license);

	
}
