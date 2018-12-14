package com.arf.relievedassist.service;

import java.util.List;

import com.arf.core.service.BaseService;
import com.arf.relievedassist.entity.RelievedAssistReportRecord;
import com.arf.relievedassist.entity.RelievedAssistReportRecord.HandleStatus;
import com.arf.relievedassist.entity.RelievedAssistReportRecord.ReportType;

public interface IRelievedAssistReportRecordService extends BaseService<RelievedAssistReportRecord, Long>{

	/**根据以下条件查询举报是否重复
	 * @param reportType 举报类型
	 * @param contentId  举报帖子ID
	 * @param commentId  举报的回复ID
	 * @param reportUserName 举报人
	 * @return
	 */
	List<RelievedAssistReportRecord> findReportByCondition(ReportType reportType,Long contentId,Long commentId,String reportUserName,HandleStatus handleStatus);
}
