package com.arf.access.dao;

import java.util.List;

import com.arf.access.entity.AccessHeartbeatConfig;
import com.arf.core.dao.BaseDao;

public interface IAccessHeartbeatConfigDao extends BaseDao<AccessHeartbeatConfig, Long>{
	
	/**
	 * 根据门禁编号和状态查询
	 * @param accessMac
	 * @param status
	 * @return
	 */
	public List<AccessHeartbeatConfig> findByAccessNumAndStatus(String accessMac,AccessHeartbeatConfig.Status status);

}
