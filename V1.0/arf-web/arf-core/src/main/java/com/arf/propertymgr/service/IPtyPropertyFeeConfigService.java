package com.arf.propertymgr.service;

import java.util.List;

import com.arf.core.service.BaseService;
import com.arf.propertymgr.entity.PtyPropertyFeeConfig;

public interface IPtyPropertyFeeConfigService extends BaseService<PtyPropertyFeeConfig, Long>{

	/**
	 * 查询开启物业缴费配置的小区
	 * @return
	 */
	public List<PtyPropertyFeeConfig> findCommunity();
}
