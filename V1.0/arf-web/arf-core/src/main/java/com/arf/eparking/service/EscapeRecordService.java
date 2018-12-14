package com.arf.eparking.service;

import java.util.List;

import com.arf.core.service.BaseService;
import com.arf.eparking.entity.EscapeRecord;
import com.arf.eparking.entity.EscapeRecord.RecoveryStatus;

public interface EscapeRecordService extends BaseService<EscapeRecord, Long> {

	/**
	 * 根据用户名和追缴状态查询逃欠费
	 * @param userName
	 * @param recoveryStatus
	 * @return
	 */
	List<EscapeRecord> findByUserNameAndRecoveryStatus(String userName,RecoveryStatus recoveryStatus);

}
