package com.arf.repair.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
@Entity
@Table(name="repair_content_record")
@SequenceGenerator(name="sequenceGenerator",sequenceName="repair_content_record_sequence")
public class RepairContentRecord extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -807134061786487319L;
	
	private String userName;//用户名
	private String realName;//真实姓名
	private String communityNumber;//小区编号
	private String roomNumber;//房屋编号
	private String repairContent;//报修内容
	private String repairImages;//报修图片["arf.test/image/769e28302d644698b4858a0cfc848034.jpg"]
	private Status status;//报修状态 0 New 申请中 1 Processed 已处理
	private ReserveType reserveType;//预约类型 0 Quickly 尽快上门 1 Book 预定时间
	private String expectDate;//期望处理时间日期["2018-03-09"]
	private String expectTime;//期望处理时间小时["9:00-10:00"]
	private Date applyDate;//申请时间
	private String processUser;//处理人
	private Date processDate;//处理时间
	private WarnStatus warnStatus;//预警状态 0 Normal正常 1 TimeOut已超时
	private String remark;//备注
	
	@JsonFormat(shape=JsonFormat.Shape.NUMBER)
	public enum Status{
		New,//申请中
		Processed;//已处理
	}
	@JsonFormat(shape=JsonFormat.Shape.NUMBER)
	public enum ReserveType{
		Quickly,//尽快上门
		Book;//预定时间
	}
	@JsonFormat(shape=JsonFormat.Shape.NUMBER)
	public enum WarnStatus{
		Normal,//正常
		TimeOut;//已超时
	}

	@Column(length=20)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(length=32)
	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	@Column(length=20)
	public String getCommunityNumber() {
		return communityNumber;
	}

	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
	@Column(length=40)
	public String getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}
	@Column(length=500)
	public String getRepairContent() {
		return repairContent;
	}

	public void setRepairContent(String repairContent) {
		this.repairContent = repairContent;
	}
	@Column(length=600)
	public String getRepairImages() {
		return repairImages;
	}

	public void setRepairImages(String repairImages) {
		this.repairImages = repairImages;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public ReserveType getReserveType() {
		return reserveType;
	}

	public void setReserveType(ReserveType reserveType) {
		this.reserveType = reserveType;
	}
	@Column(length=100)
	public String getExpectDate() {
		return expectDate;
	}

	public void setExpectDate(String expectDate) {
		this.expectDate = expectDate;
	}
	@Column(length=100)
	public String getExpectTime() {
		return expectTime;
	}

	public void setExpectTime(String expectTime) {
		this.expectTime = expectTime;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	@Column(length=20)
	public String getProcessUser() {
		return processUser;
	}

	public void setProcessUser(String processUser) {
		this.processUser = processUser;
	}

	public Date getProcessDate() {
		return processDate;
	}

	public void setProcessDate(Date processDate) {
		this.processDate = processDate;
	}

	public WarnStatus getWarnStatus() {
		return warnStatus;
	}

	public void setWarnStatus(WarnStatus warnStatus) {
		this.warnStatus = warnStatus;
	}
	@Column(length=200)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
