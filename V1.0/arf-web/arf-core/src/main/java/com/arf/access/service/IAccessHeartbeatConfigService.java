package com.arf.access.service;

import java.util.List;

import com.arf.access.entity.AccessHeartbeatConfig;
import com.arf.core.service.BaseService;

public interface IAccessHeartbeatConfigService extends BaseService<AccessHeartbeatConfig, Long>{
	
	/**
	 * 根据门禁编号和状态查询
	 * @param accessNum
	 * @param status
	 * @return
	 */
	public List<AccessHeartbeatConfig> findByAccessNumAndStatus(String accessMac,AccessHeartbeatConfig.Status status);

}
