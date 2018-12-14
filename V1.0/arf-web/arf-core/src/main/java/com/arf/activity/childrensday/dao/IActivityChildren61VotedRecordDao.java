package com.arf.activity.childrensday.dao;

import java.util.List;

import com.arf.activity.childrensday.entity.ActivityChildren61VotedRecord;
import com.arf.activity.childrensday.entity.ActivityChildren61VotedRecord.SourceType;
import com.arf.core.dao.BaseDao;

public interface IActivityChildren61VotedRecordDao extends
		BaseDao<ActivityChildren61VotedRecord, Long> {

	List<ActivityChildren61VotedRecord> findByUserIdentifySourceType(
			String userIdentify, SourceType sourceType);

	
	
}
