package com.arf.smart.service;

import java.util.List;

import com.arf.core.service.BaseService;
import com.arf.smart.entity.SmartVoiceRecord;
import com.arf.smart.entity.SmartVoiceRecord.DeviceType;
import com.arf.smart.entity.SmartVoiceRecord.Type;

public interface ISmartVoiceRecordService extends BaseService<SmartVoiceRecord, Long> {

	/**
	 * 
	 * @param userName 用户名 必填
	 * @param deviceType 设备类型 可为空
	 * @param identifyRef 设备唯一标识 可为空
	 * @return
	 */
	List<SmartVoiceRecord> findByUserDeviceTypeIdentifyRef(String userName,DeviceType deviceType, String identifyRef);

	/**
	 * 
	 * @param userName 用户名 必填
	 * @param deviceType 设备类型 可为空
	 * @param identifyRef 设备唯一标识 可为空
	 * @param status 状态 可为空
	 * @param type 开关类型 可为空
	 * @return
	 */
	List<SmartVoiceRecord> findByUserCondition(String userName, DeviceType deviceType, String identifyRef,
			SmartVoiceRecord.Status status,Type type);
	
	/**
	 * 根据identifyRef找出所有正常语音信息（主用户删除车辆时需要该车辆的所有语音信息）
	 * @param identifyRef 非空
	 * @param deviceType 可空
	 * @return
	 */
	List<SmartVoiceRecord> findByIdentifyRef(String identifyRef,DeviceType deviceType);
}
