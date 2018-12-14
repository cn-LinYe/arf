package com.arf.relievedassist.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.relievedassist.dao.IRelievedAssistUserGradeRecordDao;
import com.arf.relievedassist.entity.RelievedAssistUserGradeRecord;
import com.arf.relievedassist.service.IRelievedAssistUserGradeRecordService;

@Service("relievedAssistUserGradeRecordService")
public class RelievedAssistUserGradeRecordServiceImpl extends BaseServiceImpl<RelievedAssistUserGradeRecord, Long> implements IRelievedAssistUserGradeRecordService{

	@Resource(name="relievedAssistUserGradeRecordDao")
	IRelievedAssistUserGradeRecordDao relievedAssistUserGradeRecordDao;

	@Override
	protected BaseDao<RelievedAssistUserGradeRecord, Long> getBaseDao() {
		return relievedAssistUserGradeRecordDao;
	}

	@Override
	public RelievedAssistUserGradeRecord findDefaultGrade() {		
		return relievedAssistUserGradeRecordDao.findDefaultGrade();
	}

	@Override
	public RelievedAssistUserGradeRecord findByAssistPoint(Integer assistPoint) {
		return relievedAssistUserGradeRecordDao.findByAssistPoint(assistPoint);
	}
	
}
