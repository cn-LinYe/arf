package com.arf.access.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.access.dao.IAccessHeartbeatConfigDao;
import com.arf.access.entity.AccessHeartbeatConfig;
import com.arf.access.entity.AccessHeartbeatConfig.Status;
import com.arf.access.service.IAccessHeartbeatConfigService;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;

@Service("accessHeartbeatConfigService")
public class AccessHeartbeatConfigServiceImpl extends BaseServiceImpl<AccessHeartbeatConfig, Long> implements IAccessHeartbeatConfigService{

	@Resource(name="accessHeartbeatConfigDao")
	IAccessHeartbeatConfigDao accessHeartbeatConfigDao;
	
	@Override
	protected BaseDao<AccessHeartbeatConfig, Long> getBaseDao() {
		return accessHeartbeatConfigDao;
	}

	@Override
	public List<AccessHeartbeatConfig> findByAccessNumAndStatus(String accessMac, Status status) {
		return accessHeartbeatConfigDao.findByAccessNumAndStatus(accessMac, status);
	}

}
