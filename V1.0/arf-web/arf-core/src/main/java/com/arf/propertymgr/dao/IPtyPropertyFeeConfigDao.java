package com.arf.propertymgr.dao;

import java.util.List;

import com.arf.core.dao.BaseDao;
import com.arf.propertymgr.entity.PtyPropertyFeeConfig;

public interface IPtyPropertyFeeConfigDao extends BaseDao<PtyPropertyFeeConfig, Long>{
	/**
	 * 查询开启物业缴费配置的小区
	 * @return
	 */
	public List<PtyPropertyFeeConfig> findCommunity();
}
