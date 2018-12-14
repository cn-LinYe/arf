package com.arf.relievedassist.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.relievedassist.dao.IRelievedAssistReportRecordDao;
import com.arf.relievedassist.entity.RelievedAssistReportRecord;
import com.arf.relievedassist.entity.RelievedAssistReportRecord.HandleStatus;
import com.arf.relievedassist.entity.RelievedAssistReportRecord.ReportType;
import com.arf.relievedassist.service.IRelievedAssistReportRecordService;

@Service("relievedAssistReportRecordService")
public class RelievedAssistReportRecordServiceImpl extends BaseServiceImpl<RelievedAssistReportRecord, Long> implements IRelievedAssistReportRecordService{

	@Resource(name="relievedAssistReportRecordDao")
	IRelievedAssistReportRecordDao relievedAssistReportRecordDao;

	@Override
	protected BaseDao<RelievedAssistReportRecord, Long> getBaseDao() {
		return relievedAssistReportRecordDao;
	}

	@Override
	public List<RelievedAssistReportRecord> findReportByCondition(ReportType reportType, Long contentId, Long commentId,
			String reportUserName,HandleStatus handleStatus) {
		return relievedAssistReportRecordDao.findReportByCondition(reportType, contentId, commentId, reportUserName,handleStatus);
	}
	
}
