package com.arf.eparking.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.eparking.dao.EscapeRecordDao;
import com.arf.eparking.entity.EscapeRecord;
import com.arf.eparking.entity.EscapeRecord.RecoveryStatus;
import com.arf.eparking.service.EscapeRecordService;

@Repository("escapeRecordServiceImpl")
public class EscapeRecordServiceImpl extends BaseServiceImpl<EscapeRecord, Long> implements EscapeRecordService {

	@Resource(name = "escapeRecordDaoImpl")
	private EscapeRecordDao escapeRecordDaoImpl;
	
	@Override
	protected BaseDao<EscapeRecord, Long> getBaseDao() {
		return escapeRecordDaoImpl;
	}

	@Override
	public List<EscapeRecord> findByUserNameAndRecoveryStatus(String userName,RecoveryStatus recoveryStatus) {
		return escapeRecordDaoImpl.findByUserNameAndRecoveryStatus(userName,recoveryStatus);
	}

	

}
