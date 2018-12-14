package com.arf.core.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 临时车订单表
 * @author Administrator
 */

@Entity
@Table(name = "temporary_car_order")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "temporary_car_order_sequence")
public class TemporaryCarOrder extends BaseEntity<Long>{

	private static final long serialVersionUID = 5693333920756437237L;
	/** 订单号*/
	private String out_trade_no;
	/** 支付金额*/
	private BigDecimal total_fee;
	/** 支付用途*/
	private String subject;
	/** 车牌号*/
	private String licensePlateNumber;
	/***用户名 */
	private String userName;
	/** 小区id*/
	private String communityNumber;
	/** 订单状态*/
	private String tradeStatus;
	
	 /** 开始日期*/
	private long startTime;
	/** 结束日期*/
	private long endTime;
	private Integer isSubmitSuccess;
	
	
	@Column(name = "isSubmitSuccess")
	public Integer getIsSubmitSuccess() {
		return isSubmitSuccess;
	}
	public void setIsSubmitSuccess(Integer isSubmitSuccess) {
		this.isSubmitSuccess = isSubmitSuccess;
	}
	@Column(name = "out_trade_no", nullable = true)
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	
	@Column(name = "total_fee", nullable = true)
	public BigDecimal getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(BigDecimal total_fee) {
		this.total_fee = total_fee;
	}
	
	@Column(name = "subject", nullable = true)
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	@Column(name = "licensePlateNumber", nullable = true)
	public String getLicensePlateNumber() {
		return licensePlateNumber;
	}
	public void setLicensePlateNumber(String licensePlateNumber) {
		this.licensePlateNumber = licensePlateNumber;
	}
	@Column(name = "userName", nullable = true)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(name = "tradeStatus", nullable = true)
	public String getTradeStatus() {
		return tradeStatus;
	}
	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}
	
	@Column(name = "communityNumber",length=32, nullable = true)
	public String getCommunityNumber() {
		return communityNumber;
	}
	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
	@Column(name = "startTime",nullable = true)
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	@Column(name = "endTime", nullable = true)
	public long getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	
	
	
	
	
	
}
