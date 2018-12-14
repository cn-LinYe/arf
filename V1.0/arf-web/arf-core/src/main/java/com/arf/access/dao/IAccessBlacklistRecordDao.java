package com.arf.access.dao;

import java.util.List;

import com.arf.access.entity.AccessBlacklistRecord;
import com.arf.access.entity.AccessBlacklistRecord.Status;
import com.arf.access.entity.AccessGuestRecord.GuestType;
import com.arf.core.dao.BaseDao;

public interface IAccessBlacklistRecordDao extends
		BaseDao<AccessBlacklistRecord, Long> {

	/**
	 * 根据访客微信open_id、访客类型、用户名查询
	 * @param guestIdentifyId
	 * @param guestType
	 * @param userName
	 * @param oprateUsername
	 * @return
	 */
	AccessBlacklistRecord findByGuestUsernameOprateUsername(String guestIdentifyId,
			GuestType guestType, String userName,String oprateUsername);

	/**
	 * 根据用户名、记录状态、操作用户查询 （userName 和 oprateUsername 不能同时为空）
	 * @param userName 可为空
	 * @param status
	 * @param oprateUsername 可为空
	 * @return
	 */
	List<AccessBlacklistRecord> findByUsernameStatusOprateUsername(String userName,
			Status status,String oprateUsername);

	
	
}
