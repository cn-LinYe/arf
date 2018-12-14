package com.arf.royalstar.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.royalstar.dao.RSCarwashingDeviceLogDao;
import com.arf.royalstar.entity.RSCarwashingDeviceLog;
import com.arf.royalstar.service.RSCarwashingDeviceLogService;

@Service("rsCarwashingDeviceLogServiceImpl")
public class RSCarwashingDeviceLogServiceImpl extends BaseServiceImpl<RSCarwashingDeviceLog, Long>
		implements RSCarwashingDeviceLogService {

	@Resource(name = "rsCarwashingDeviceLogDaoImpl")
	private RSCarwashingDeviceLogDao rsCarwashingDeviceLogDao;

	@Override
	protected BaseDao<RSCarwashingDeviceLog, Long> getBaseDao() {
		return rsCarwashingDeviceLogDao;
	}
	
}
