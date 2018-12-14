package com.arf.parkrateconfig.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "parkrate_limit_info")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "parkrate_limit_info_sequence")
public class ParkrateLimitInfo extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3796510406150457326L;
	
	private String communityName;//小区名称
	private String communityNumber;//小区编号
	private Integer maxPayTime;//最大缴费时长（月）
	private String singlePayment;//单次缴费（月）月以,隔开（目前只能选APP上面已有的月份）
	private IsLimitMonth isLimitMonth;//状态:0开启1关闭
	private PayType payType;//0按范围缴费：1按月缴费（目前默认按月缴费）
	private Integer paymentRange;//缴费范围几个月以内
	
	public enum IsLimitMonth{
		Open,//0开启
		Close;//1关闭
	}
	public enum PayType{
		Range,//0按范围缴费
		Month;//1按月缴费
	}
	@Column(length=40)
	public String getCommunityName() {
		return communityName;
	}
	@Column(length=40,nullable=false)
	public String getCommunityNumber() {
		return communityNumber;
	}
	public Integer getMaxPayTime() {
		return maxPayTime;
	}
	@Column(length=64)
	public String getSinglePayment() {
		return singlePayment;
	}
	@Column(nullable=false)
	public IsLimitMonth getIsLimitMonth() {
		return isLimitMonth;
	}
	public PayType getPayType() {
		return payType;
	}
	public Integer getPaymentRange() {
		return paymentRange;
	}
	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}
	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
	public void setMaxPayTime(Integer maxPayTime) {
		this.maxPayTime = maxPayTime;
	}
	public void setSinglePayment(String singlePayment) {
		this.singlePayment = singlePayment;
	}
	public void setIsLimitMonth(IsLimitMonth isLimitMonth) {
		this.isLimitMonth = isLimitMonth;
	}
	public void setPayType(PayType payType) {
		this.payType = payType;
	}
	public void setPaymentRange(Integer paymentRange) {
		this.paymentRange = paymentRange;
	}

}
