package com.arf.eparking.dao;

import com.arf.core.dao.BaseDao;
import com.arf.eparking.entity.BlacklistRecord;
import com.arf.eparking.entity.BlacklistRecord.Status;
import com.arf.eparking.entity.BlacklistRecord.Type;

public interface BlacklistRecordDao extends BaseDao<BlacklistRecord, Long>{

	/**
	 * 根据唯一标示查询
	 * @param blacklistNo
	 * @return
	 */
	BlacklistRecord finByBlacklistNo(String blacklistNo);

	/**
	 * 根据用户名，状态和类型查询
	 * @param userName
	 * @param status
	 * @param type
	 * @return
	 */
	BlacklistRecord findByUserNameStatusAndType(String userName, Status status,Type type);

}
