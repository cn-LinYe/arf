package com.arf.repair.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="repair_distribution_record")
@SequenceGenerator(name="sequenceGenerator",sequenceName="repair_distribution_record_sequence")
public class RepairDistributionRecord extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7470063132533114109L;
	
	private Long contentId;//报修记录id
	private String userName;//维修人手机号 以”,”分割
	private Status status;//状态 0 Not_Repair 未处理 1 Repaired 已处理
	private String msgContent;//短信内容
	private String actualRepairUser;//实际维修人员 以”,”分割
	private String remark;//备注
	
	@JsonFormat(shape=JsonFormat.Shape.NUMBER)
	public enum Status{
		Not_Repair,//未处理
		Repaired;// 已处理
	}

	public Long getContentId() {
		return contentId;
	}

	public void setContentId(Long contentId) {
		this.contentId = contentId;
	}
	@Column(length=200)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	@Column(length=500)
	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
	@Column(length=200)
	public String getActualRepairUser() {
		return actualRepairUser;
	}

	public void setActualRepairUser(String actualRepairUser) {
		this.actualRepairUser = actualRepairUser;
	}
	@Column(length=200)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
