package com.arf.installment.smartlock.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table( name = "lock_installment_refunds_record" )
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "lock_installment_refunds_record_sequence")
public class LockInstallmentRefundsRecord extends BaseEntity<Long> {

	private static final long serialVersionUID = -4582108316696585349L;
	
	private String orderNo;//购买记录编号
	private String reason;//退款理由
	private BigDecimal refundAmount;//退款金额
	private Installed installed;//是否已经安装 0 NOT_INSTALLED, 1 INSTALLED
	private String contactName;//
	private String contactPhone;//
	private Status status;//状态 0 新申请/审核中,1 审核通过,2 审核不通过,3 收到退货,4 完成
	private Date confirmDate;//确认时间
	private Date receivedDate;//收到退货时间
	private Date completedDate;//完成时间(退款完成时间)
	private String confirmUser;//确认人
	private String remark;//操作备注
	private RefundType refundType;//0客户发起退货/退款要求,1款项预期门锁被回收
	private String userName;//用户名
	private String serialNumber;//退款流水号
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum Installed{
		NOT_INSTALLED,
		INSTALLED
	}
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum Status{
		NEW_APPLY,
		AUDIT_PASS,
		AUDIT_FAIlED,
		RECEIVED_GOODS,
		COMPLETED,
	}
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum RefundType{
		CUSTOMER_CLAIM,
		OVER_DUE_RECLAIM,
	}

	@Column(length=40)
	public String getOrderNo() {
		return orderNo;
	}

	@Column(length=500)
	public String getReason() {
		return reason;
	}

	@Column(precision = 10, scale = 2)
	public BigDecimal getRefundAmount() {
		return refundAmount;
	}

	public Installed getInstalled() {
		return installed;
	}

	@Column(length=40)
	public String getContactName() {
		return contactName;
	}

	@Column(length=40)
	public String getContactPhone() {
		return contactPhone;
	}

	@Column(nullable = false)
	public Status getStatus() {
		return status;
	}

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	public Date getConfirmDate() {
		return confirmDate;
	}

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	public Date getReceivedDate() {
		return receivedDate;
	}

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	public Date getCompletedDate() {
		return completedDate;
	}

	@Column(length=30)
	public String getConfirmUser() {
		return confirmUser;
	}

	@Column(length=300)
	public String getRemark() {
		return remark;
	}

	public RefundType getRefundType() {
		return refundType;
	}

	@Column(length=30,nullable = false)
	public String getUserName() {
		return userName;
	}

	@Column(length=20)
	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}

	public void setInstalled(Installed installed) {
		this.installed = installed;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}

	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}

	public void setCompletedDate(Date completedDate) {
		this.completedDate = completedDate;
	}

	public void setConfirmUser(String confirmUser) {
		this.confirmUser = confirmUser;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setRefundType(RefundType refundType) {
		this.refundType = refundType;
	}
	
}
