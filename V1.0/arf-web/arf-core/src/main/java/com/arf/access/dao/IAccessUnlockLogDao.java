package com.arf.access.dao;

import java.util.Date;
import java.util.List;

import com.arf.access.entity.AccessUnlockLog;
import com.arf.core.dao.BaseDao;

public interface IAccessUnlockLogDao extends BaseDao<AccessUnlockLog, Long>{

	List<AccessUnlockLog> findByUserNameAndtimeGetoneorallSuccess(
			String userName, Date time, boolean getOne);

	List<AccessUnlockLog> findByStartDateEndDate(Date startTime, Date endTime);
}
