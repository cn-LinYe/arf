package com.arf.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

@Entity(name = "redrecordmodel")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "redrecordmodel_sequence")
public class RedRecordModel extends BaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6068511676597106010L;

	
	private String user_name;
	/**
	 * 红包记录（唯一标示）
	 */
	private String redRecord;
	
	private int amount;
	
	private String  openId;
	
	/**
	 * 创建时间
	 */
	private String createTime;
	/**
	 * 领取红包时间
	 */
	private String updateTime;
	
	/**
	 * 红包状态
	 * @return
	 */
	private String status;
	
	/**
	 * vip支付订单号
	 * @return
	 */
	private String out_trade_no;
	
	private String communityNumber;
	private Integer propertyNumber;
	private Integer branchId;
	
	
	@Column(name = "user_name", length = 64, nullable = false)
	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	
	@Column(name = "redRecord", length = 64, nullable = false)
	public String getRedRecord() {
		return redRecord;
	}

	public void setRedRecord(String redRecord) {
		this.redRecord = redRecord;
	}

	@Column(name = "amount", length = 64, nullable = false)
	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	@Column(name = "openId", length = 64, nullable = false)
	public String getOpenId() {
		return openId;
	}

	
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
	
	@Column(name = "createTime")
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	@Column(name = "updateTime")
	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	@Column(name = "status",length=16)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name = "out_trade_no", length = 64)
	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	
	
	public String getCommunityNumber() {
		return communityNumber;
	}

	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}

	public Integer getPropertyNumber() {
		return propertyNumber;
	}

	public void setPropertyNumber(Integer propertyNumber) {
		this.propertyNumber = propertyNumber;
	}

	public Integer getBranchId() {
		return branchId;
	}

	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}

	public RedRecordModel(String user_name, String redRecord, int amount, String openId) {
		super();
		this.user_name = user_name;
		this.redRecord = redRecord;
		this.amount = amount;
		this.openId = openId;
	}

	public RedRecordModel() {
		super();
	}

	public RedRecordModel(String user_name, String redRecord, int amount, String openId, String createTime) {
		super();
		this.user_name = user_name;
		this.redRecord = redRecord;
		this.amount = amount;
		this.openId = openId;
		this.createTime = createTime;
	}

	public RedRecordModel(String user_name, String redRecord, int amount, String openId, String createTime,
			String out_trade_no) {
		super();
		this.user_name = user_name;
		this.redRecord = redRecord;
		this.amount = amount;
		this.openId = openId;
		this.createTime = createTime;
		this.out_trade_no = out_trade_no;
	}

	public RedRecordModel(String user_name, String redRecord, int amount, String openId, String createTime,
			String updateTime, String status, String out_trade_no) {
		super();
		this.user_name = user_name;
		this.redRecord = redRecord;
		this.amount = amount;
		this.openId = openId;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.status = status;
		this.out_trade_no = out_trade_no;
	}
	
	
	
	
	
	

}
