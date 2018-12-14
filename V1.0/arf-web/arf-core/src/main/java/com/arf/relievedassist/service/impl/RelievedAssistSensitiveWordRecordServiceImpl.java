package com.arf.relievedassist.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.relievedassist.dao.IRelievedAssistSensitiveWordRecordDao;
import com.arf.relievedassist.entity.RelievedAssistSensitiveWordRecord;
import com.arf.relievedassist.service.IRelievedAssistSensitiveWordRecordService;

@Service("relievedAssistSensitiveWordRecordService")
public class RelievedAssistSensitiveWordRecordServiceImpl extends BaseServiceImpl<RelievedAssistSensitiveWordRecord, Long> implements IRelievedAssistSensitiveWordRecordService{

	@Resource(name="relievedAssistSensitiveWordRecordDao")
	IRelievedAssistSensitiveWordRecordDao relievedAssistSensitiveWordRecordDao;

	@Override
	protected BaseDao<RelievedAssistSensitiveWordRecord, Long> getBaseDao() {
		return relievedAssistSensitiveWordRecordDao;
	}
	
	
}
