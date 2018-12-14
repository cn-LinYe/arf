package com.arf.activity.childrensday.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.activity.childrensday.dao.IActivityChildren61VotedRecordDao;
import com.arf.activity.childrensday.entity.ActivityChildren61VotedRecord;
import com.arf.activity.childrensday.entity.ActivityChildren61VotedRecord.SourceType;
import com.arf.activity.childrensday.service.IActivityChildren61VotedRecordService;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;

@Service("activityChildren61VotedRecordServiceImpl")
public class ActivityChildren61VotedRecordServiceImpl extends
		BaseServiceImpl<ActivityChildren61VotedRecord, Long> implements
		IActivityChildren61VotedRecordService {

	@Resource(name = "activityChildren61VotedRecordDaoImpl")
	private IActivityChildren61VotedRecordDao activityChildren61VotedRecordDaoImpl;
	
	@Override
	protected BaseDao<ActivityChildren61VotedRecord, Long> getBaseDao() {
		return activityChildren61VotedRecordDaoImpl;
	}

	@Override
	public List<ActivityChildren61VotedRecord> findByUserIdentifySourceType(
			String userIdentify, SourceType sourceType) {
		return activityChildren61VotedRecordDaoImpl.findByUserIdentifySourceType(userIdentify,sourceType);
	}
	
	
}
