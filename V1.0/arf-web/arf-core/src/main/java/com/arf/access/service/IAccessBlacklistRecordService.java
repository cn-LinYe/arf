package com.arf.access.service;

import java.util.List;

import com.arf.access.entity.AccessBlacklistRecord;
import com.arf.access.entity.AccessBlacklistRecord.Status;
import com.arf.access.entity.AccessGuestRecord.GuestType;
import com.arf.core.service.BaseService;

public interface IAccessBlacklistRecordService extends
		BaseService<AccessBlacklistRecord, Long> {

	/**
	 * 根据访客微信open_id、访客类型、业主用户、操作用户查询(一个户主、一个访客，只允许有一条拉黑记录)
	 * @param guestIdentifyId
	 * @param guestType
	 * @param userName
	 * @param oprateUsername 可为空
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
			com.arf.access.entity.AccessBlacklistRecord.Status status,String oprateUsername);
	
	

}
