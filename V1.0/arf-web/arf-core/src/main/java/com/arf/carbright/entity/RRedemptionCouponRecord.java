package com.arf.carbright.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.arf.core.util.GetClassAttributes;

@Entity
@Table(name = "r_redemption_coupon_record")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "r_redemption_coupon_record_sequence")
public class RRedemptionCouponRecord extends BaseEntity<Long> {

	/*
	 * 优惠券兑换记录表
	 */
	private static final long serialVersionUID = 1L;
	private String redemptionCode;// 兑换码
	private String userName;// 兑换用户
	private Date redemptionTime;// 兑换时间
	private String packageTypeNum;//套餐编号（优惠券）
	private Integer redemptionType;// 兑换方式(0、扫二维码1、兑换码)
	private Integer paymentMethod;// 支付方式（0、电子钱包1、微信2、支付宝）
	private BigDecimal paymentPrice;// 支付金额
	private Integer times;// 服务次数
	private Integer businessNum;// 服务点名称
	private BigDecimal packagePrice;// 套餐价格
	private Integer redemptionStatus;// 兑换状态(0、未兑换1、已兑换)
	private String communityNumber;// 小区编号
	private Integer propertyNumber;// 物业ID
	private Integer branchId;// 子公司ID
	private Integer getCouponId;//赠送记录表外键

	public enum RedemptionStatus {
		undone, completed;
	}
	@Column(length = 40)
	public String getRedemptionCode() {
		return redemptionCode;
	}

	public void setRedemptionCode(String redemptionCode) {
		this.redemptionCode = redemptionCode;
	}
	@Column(length = 40)
	public String getPackageTypeNum() {
		return packageTypeNum;
	}
	public void setPackageTypeNum(String packageTypeNum) {
		this.packageTypeNum = packageTypeNum;
	}
	@Column(length = 40)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getRedemptionTime() {
		return redemptionTime;
	}

	public void setRedemptionTime(Date redemptionTime) {
		this.redemptionTime = redemptionTime;
	}

	public Integer getRedemptionType() {
		return redemptionType;
	}

	public void setRedemptionType(Integer redemptionType) {
		this.redemptionType = redemptionType;
	}

	public Integer getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(Integer paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	@Column(name = "paymentPrice", precision = 10, scale = 2)
	public BigDecimal getPaymentPrice() {
		return paymentPrice;
	}

	public void setPaymentPrice(BigDecimal paymentPrice) {
		this.paymentPrice = paymentPrice;
	}

	public Integer getTimes() {
		return times;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}

	public Integer getBusinessNum() {
		return businessNum;
	}

	public void setBusinessNum(Integer businessNum) {
		this.businessNum = businessNum;
	}
	@Column(name = "packagePrice", precision = 10, scale = 2)
	public BigDecimal getPackagePrice() {
		return packagePrice;
	}

	public void setPackagePrice(BigDecimal packagePrice) {
		this.packagePrice = packagePrice;
	}

	public Integer getRedemptionStatus() {
		return redemptionStatus;
	}

	public void setRedemptionStatus(Integer redemptionStatus) {
		this.redemptionStatus = redemptionStatus;
	}
	@Column(length = 40)
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

	public Integer getGetCouponId() {
		return getCouponId;
	}

	public void setGetCouponId(Integer getCouponId) {
		this.getCouponId = getCouponId;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return GetClassAttributes.toString(this);
	}

	
}
