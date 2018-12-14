package com.arf.access.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.access.dao.IAccessUnlockLogDao;
import com.arf.access.entity.AccessUnlockLog;
import com.arf.access.service.IAccessUnlockLogService;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;

@Service("accessUnlockLogService")
public class AccessUnlockLogServiceImpl extends
		BaseServiceImpl<AccessUnlockLog, Long> implements
		IAccessUnlockLogService{

	@Resource(name = "accessUnlockLogDao")
	private IAccessUnlockLogDao accessUnlockLogDao;
	
	@Override
	protected BaseDao<AccessUnlockLog, Long> getBaseDao() {
		return accessUnlockLogDao;
	}

	@Override
	public List<AccessUnlockLog> findByUserNameAndtimeGetoneorallSuccess(
			String userName, Date time, boolean getOne) {
		return accessUnlockLogDao.findByUserNameAndtimeGetoneorallSuccess(userName, time,getOne);
	}

	@Override
	public List<AccessUnlockLog> findByStartDateEndDate(Date startTime,
			Date endTime) {
		return accessUnlockLogDao.findByStartDateEndDate(startTime,endTime);
	}
}
