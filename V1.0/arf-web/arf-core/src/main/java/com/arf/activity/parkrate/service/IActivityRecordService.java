package com.arf.activity.parkrate.service;

import com.arf.activity.parkrate.dto.ParkRateActivityDto;
import com.arf.activity.parkrate.entity.ActivityRecord;
import com.arf.core.entity.ParkRateRecordModel;
import com.arf.core.service.BaseService;

public interface IActivityRecordService extends
		BaseService<ActivityRecord, Long> {

	
	ParkRateActivityDto parkRateActivity(String userName,String outTradeNo);
	
	ActivityRecord findByOutTradeNo(String outTradeNo);
}
