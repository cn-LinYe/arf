package com.arf.access.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.access.dao.IAccessDeviceStatusInfoDao;
import com.arf.access.entity.AccessDeviceStatusInfo;
import com.arf.access.entity.AccessDeviceStatusInfo.Status;
import com.arf.access.service.IAccessDeviceStatusInfoService;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;

@Service("accessDeviceStatusInfoService")
public class AccessDeviceStatusInfoServiceImpl extends BaseServiceImpl<AccessDeviceStatusInfo, Long> implements IAccessDeviceStatusInfoService{

	@Resource(name="accessDeviceStatusInfoDao")
	IAccessDeviceStatusInfoDao accessDeviceStatusInfoDao;
	
	@Override
	protected BaseDao<AccessDeviceStatusInfo, Long> getBaseDao() {
		return accessDeviceStatusInfoDao;
	}

	@Override
	public AccessDeviceStatusInfo findByAccessMacAndStatus(String accessMac, Status status) {
		return accessDeviceStatusInfoDao.findByAccessMacAndStatus(accessMac, status);
	}

}
