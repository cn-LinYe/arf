package com.arf.smart.dao;

import java.util.List;

import com.arf.core.dao.BaseDao;
import com.arf.smart.entity.SmartVoiceRecord;
import com.arf.smart.entity.SmartVoiceRecord.DeviceType;
import com.arf.smart.entity.SmartVoiceRecord.Type;

public interface ISmartVoiceRecordDao extends BaseDao<SmartVoiceRecord, Long> {

	List<SmartVoiceRecord> findByUserDeviceTypeIdentifyRef(String userName,DeviceType deviceType, String identifyRef);

	List<SmartVoiceRecord> findByUserCondition(String userName,DeviceType deviceType,String identifyRef,SmartVoiceRecord.Status status,Type type);
	
	List<SmartVoiceRecord> findByIdentifyRef(String identifyRef,DeviceType deviceType);
}
