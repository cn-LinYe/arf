package com.arf.access.service;

import java.util.Date;
import java.util.List;

import com.arf.access.entity.AccessUnlockLog;
import com.arf.core.service.BaseService;

public interface IAccessUnlockLogService extends
		BaseService<AccessUnlockLog, Long> {

	List<com.arf.access.entity.AccessUnlockLog> findByUserNameAndtimeGetoneorallSuccess(
			String userName, Date time, boolean getOne);

	List<AccessUnlockLog> findByStartDateEndDate(Date startTime, Date endTime);

}
