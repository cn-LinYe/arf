package com.arf.activity.childrensday.service;

import java.util.List;

import com.arf.activity.childrensday.entity.ActivityChildren61VotedRecord;
import com.arf.activity.childrensday.entity.ActivityChildren61VotedRecord.SourceType;
import com.arf.core.service.BaseService;

public interface IActivityChildren61VotedRecordService extends
		BaseService<ActivityChildren61VotedRecord, Long> {

	List<ActivityChildren61VotedRecord> findByUserIdentifySourceType(
			String userIdentify, SourceType sourceType);

	
	
}
