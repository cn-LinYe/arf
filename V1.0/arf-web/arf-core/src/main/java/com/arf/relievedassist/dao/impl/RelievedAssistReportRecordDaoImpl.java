package com.arf.relievedassist.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.relievedassist.dao.IRelievedAssistReportRecordDao;
import com.arf.relievedassist.entity.RelievedAssistReportRecord;
import com.arf.relievedassist.entity.RelievedAssistReportRecord.HandleStatus;
import com.arf.relievedassist.entity.RelievedAssistReportRecord.ReportType;

@Repository("relievedAssistReportRecordDao")
public class RelievedAssistReportRecordDaoImpl extends BaseDaoImpl<RelievedAssistReportRecord, Long> implements IRelievedAssistReportRecordDao{

	@Override
	public List<RelievedAssistReportRecord> findReportByCondition(ReportType reportType, Long contentId, Long commentId,
			String reportUserName,HandleStatus handleStatus) {
		StringBuffer hql = new StringBuffer("from RelievedAssistReportRecord where 1 = 1");
		if(reportType != null){
			hql.append(" and reportType = :reportType");
		}
		if(contentId != null){
			hql.append(" and contentId = :contentId");
		}
		if(commentId != null){
			hql.append(" and commentId = :commentId");
		}
		if(StringUtils.isNotBlank(reportUserName)){
			hql.append(" and reportUserName = :reportUserName");
		}
		if(handleStatus != null){
			hql.append(" and handleStatus = :handleStatus");
		}
		TypedQuery<RelievedAssistReportRecord> query = entityManager.createQuery(hql.toString(),RelievedAssistReportRecord.class);
		if(reportType != null){
			query.setParameter("reportType", reportType);
		}
		if(contentId != null){
			query.setParameter("contentId",contentId);
		}
		if(commentId != null){
			query.setParameter("commentId",commentId);
		}
		if(StringUtils.isNotBlank(reportUserName)){
			query.setParameter("reportUserName",reportUserName);
		}
		if(handleStatus != null){
			query.setParameter("handleStatus",handleStatus);
		}
		return query.getResultList();
	}

}
