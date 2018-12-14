package com.arf.relievedassist.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "relieved_assist_report_record")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "relieved_assist_report_record_sequence")
public class RelievedAssistReportRecord extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3919188389891679142L;
	
	private String reportUserName;//举报人
	private String userName;//被举报人
	private ReportType reportType;/*举报类型(0、人身攻击 Personal_Attacks 1、垃圾营销 Garbage_Marketing 2、淫秽色情 Obscene_Pornography 
									3、不实信息 Inaccurate_Information 4、违法信息 Illegal_Information5、政治内容 Political_Content)*/
	private Long contentId;//帮帮帖ID
	private Long commentId;//评论ID（子ID）
	private HandleStatus handleStatus;//处理状态(0未处理 Not_Processed1、已处理Processed 2、不处理 Not_Dealt_With)
	private String handleResult;//处理结果（备注）
	private String reportDescription;//举报说明
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum ReportType{
		Personal_Attacks,//人身攻击 
		Garbage_Marketing,//垃圾营销
		Obscene_Pornography,//淫秽色情
		Inaccurate_Information,//不实信息 
		Illegal_Information,//违法信息
		Political_Content;//政治内容 
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum HandleStatus{
		Not_Processed,//未处理
		Processed,//已处理
		Not_Dealt_With;//不处理
	}
	
	@Column(length=20)
	public String getReportUserName() {
		return reportUserName;
	}
	public void setReportUserName(String reportUserName) {
		this.reportUserName = reportUserName;
	}
	@Column(length=20)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public ReportType getReportType() {
		return reportType;
	}
	public void setReportType(ReportType reportType) {
		this.reportType = reportType;
	}
	public Long getContentId() {
		return contentId;
	}
	public void setContentId(Long contentId) {
		this.contentId = contentId;
	}
	public Long getCommentId() {
		return commentId;
	}
	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}
	public HandleStatus getHandleStatus() {
		return handleStatus;
	}
	public void setHandleStatus(HandleStatus handleStatus) {
		this.handleStatus = handleStatus;
	}
	@Column(length=50)
	public String getHandleResult() {
		return handleResult;
	}
	public void setHandleResult(String handleResult) {
		this.handleResult = handleResult;
	}
	@Column(length=100)
	public String getReportDescription() {
		return reportDescription;
	}
	public void setReportDescription(String reportDescription) {
		this.reportDescription = reportDescription;
	}

}
