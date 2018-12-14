package com.arf.core.service.impl;


import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import com.arf.core.dao.P_DeviceinfoDao;
import com.arf.core.dao.BaseDao;
import com.arf.core.entity.PDeviceinfo;
import com.arf.core.service.PDeviceinfoService;


/**
 * Service - P_Deviceinfo表
 * 
 * @author arf dg
 * @version 4.0
 */
@Service("P_DeviceinfoServiceImpl")
public class PDeviceinfoServiceImpl extends BaseServiceImpl<PDeviceinfo, Long> implements PDeviceinfoService {

	@Resource(name = "P_DeviceinfoDaoImpl")
	private P_DeviceinfoDao P_DeviceinfoDao;

	@Override
	protected BaseDao<PDeviceinfo, Long> getBaseDao() {
		return P_DeviceinfoDao;
	}

	@Override
	public PDeviceinfo findByDeviceNum(String deviceNum,String communityNum) {
		return P_DeviceinfoDao.findByDeviceNum(deviceNum,communityNum);
	}

}