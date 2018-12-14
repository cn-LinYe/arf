package com.arf.eparking.service;

import com.arf.core.service.BaseService;
import com.arf.eparking.entity.BlacklistRecord;
import com.arf.eparking.entity.BlacklistRecord.Status;
import com.arf.eparking.entity.BlacklistRecord.Type;

public interface BlacklistRecordService extends BaseService<BlacklistRecord,Long>{

	/**
	 * 生成黑名单记录唯一标示yyyyMMddHHmmssSSS+8位随机数
	 * @return
	 */
	String genBlacklistNo();

	/**
	 * 根据用户名，状态和类型查询
	 * @param userName
	 * @param status
	 * @param type
	 * @return
	 */
	BlacklistRecord findByUserNameStatusAndType(String userName, Status status,Type type);
	
}
