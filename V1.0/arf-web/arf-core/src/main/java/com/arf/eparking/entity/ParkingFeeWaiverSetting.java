package com.arf.eparking.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "p_parking_fee_waiver_setting")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "p_parking_fee_waiver_setting_sequence")
public class ParkingFeeWaiverSetting extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1714855916522318732L;
	
	private String communityNumber;//小区编号
	private String businessNum;//商户编号
	private Integer discountTime;//优惠时长（分钟）
	private BigDecimal discountedPrice;//优惠金额
	private BigDecimal fullAmount;//满减金额
	private Status status;//停车减免设置状态
	
	public enum Status{
		Open,//0开启
		Close;//1关闭
	}
	
	@Column(length=20)
	public String getCommunityNumber() {
		return communityNumber;
	}
	@Column(length=40)
	public String getBusinessNum() {
		return businessNum;
	}
	public Integer getDiscountTime() {
		return discountTime;
	}
	@Column(precision=10,scale=2)
	public BigDecimal getDiscountedPrice() {
		return discountedPrice;
	}
	@Column(precision=10,scale=2)
	public BigDecimal getFullAmount() {
		return fullAmount;
	}
	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
	public void setBusinessNum(String businessNum) {
		this.businessNum = businessNum;
	}
	public void setDiscountTime(Integer discountTime) {
		this.discountTime = discountTime;
	}
	public void setDiscountedPrice(BigDecimal discountedPrice) {
		this.discountedPrice = discountedPrice;
	}
	public void setFullAmount(BigDecimal fullAmount) {
		this.fullAmount = fullAmount;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	
	
}
