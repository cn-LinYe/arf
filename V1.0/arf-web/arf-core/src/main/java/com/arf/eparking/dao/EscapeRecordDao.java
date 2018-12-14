package com.arf.eparking.dao;

import java.util.List;

import com.arf.core.dao.BaseDao;
import com.arf.eparking.entity.EscapeRecord;
import com.arf.eparking.entity.EscapeRecord.RecoveryStatus;

public interface EscapeRecordDao extends BaseDao<EscapeRecord, Long> {

	/**
	 * 根据用户名和追缴状态查询逃欠费
	 * @param userName
	 * @param recoveryStatus
	 * @return
	 */
	List<EscapeRecord> findByUserNameAndRecoveryStatus(String userName,RecoveryStatus recoveryStatus);

	
}
