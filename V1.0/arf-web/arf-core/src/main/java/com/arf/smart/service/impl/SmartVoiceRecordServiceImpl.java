package com.arf.smart.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.smart.dao.ISmartVoiceRecordDao;
import com.arf.smart.entity.SmartVoiceRecord;
import com.arf.smart.entity.SmartVoiceRecord.DeviceType;
import com.arf.smart.entity.SmartVoiceRecord.Type;
import com.arf.smart.service.ISmartVoiceRecordService;

@Service("smartVoiceRecordServiceImpl")
public class SmartVoiceRecordServiceImpl extends BaseServiceImpl<SmartVoiceRecord, Long>
		implements ISmartVoiceRecordService {

	@Resource(name = "smartVoiceRecordDaoImpl")
	private ISmartVoiceRecordDao smartVoiceRecordDaoImpl;

	@Override
	protected BaseDao<SmartVoiceRecord, Long> getBaseDao() {
		return smartVoiceRecordDaoImpl;
	}

	@Override
	public List<SmartVoiceRecord> findByUserDeviceTypeIdentifyRef(String userName, DeviceType deviceType,
			String identifyRef) {
		return smartVoiceRecordDaoImpl.findByUserDeviceTypeIdentifyRef(userName, deviceType, identifyRef);
	}

	@Override
	public List<SmartVoiceRecord> findByUserCondition(String userName, DeviceType deviceType, String identifyRef,
			SmartVoiceRecord.Status status,Type type) {
		return smartVoiceRecordDaoImpl.findByUserCondition(userName, deviceType, identifyRef, status,type);
	}
	
	@Override
	public List<SmartVoiceRecord> findByIdentifyRef(String identifyRef,DeviceType deviceType){
		return smartVoiceRecordDaoImpl.findByIdentifyRef(identifyRef, deviceType);
	}
}
