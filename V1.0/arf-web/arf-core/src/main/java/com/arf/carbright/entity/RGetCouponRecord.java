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
@Table(name = "r_get_coupon_record")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "r_get_coupon_record_sequence")
public class RGetCouponRecord extends BaseEntity<Long>{

	/**优惠券赠送记录表 
	 */
	private static final long serialVersionUID = 1L;
	private Date giveStartTime;//赠送起始时间
	private Date giveEndTime;//赠送结束时间
	private String packageTypeNum;//套餐编号（优惠券）
	private BigDecimal packagePrice ;//套餐价格
	private BigDecimal  discountPrice;//折扣后价格
	private Integer packageCount;//优惠券数量
	private Integer exchangeCount;//兑换数量
	private BigDecimal totalMoney;//总价值
	private Integer businessNum;//商户编号（使用服务点）
	private String communityNumber;//小区编号
	private Integer propertyNumber;//物业编号
	private Integer branchId;//子公司
	private Integer redemptionStatus;//兑换完成标示(0、未完成1、已完成)
	
	public enum RedemptionStatus {
		undone, completed;
	}	
	public Date getGiveStartTime() {
		return giveStartTime;
	}
	public void setGiveStartTime(Date giveStartTime) {
		this.giveStartTime = giveStartTime;
	}
	public Date getGiveEndTime() {
		return giveEndTime;
	}
	public void setGiveEndTime(Date giveEndTime) {
		this.giveEndTime = giveEndTime;
	}
	@Column(length = 40)
	public String getPackageTypeNum() {
		return packageTypeNum;
	}
	public void setPackageTypeNum(String packageTypeNum) {
		this.packageTypeNum = packageTypeNum;
	}
	@Column(name = "packagePrice", precision = 10, scale = 2)
	public BigDecimal getPackagePrice() {
		return packagePrice;
	}
	public void setPackagePrice(BigDecimal packagePrice) {
		this.packagePrice = packagePrice;
	}
	@Column(name = "discountPrice", precision = 10, scale = 2)
	public BigDecimal getDiscountPrice() {
		return discountPrice;
	}
	public void setDiscountPrice(BigDecimal discountPrice) {
		this.discountPrice = discountPrice;
	}
	public Integer getPackageCount() {
		return packageCount;
	}
	public void setPackageCount(Integer packageCount) {
		this.packageCount = packageCount;
	}
	public Integer getExchangeCount() {
		return exchangeCount;
	}
	public void setExchangeCount(Integer exchangeCount) {
		this.exchangeCount = exchangeCount;
	}
	@Column(name = "totalMoney", precision = 10, scale = 2)
	public BigDecimal getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}
	public Integer getBusinessNum() {
		return businessNum;
	}
	public void setBusinessNum(Integer businessNum) {
		this.businessNum = businessNum;
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
	public Integer getRedemptionStatus() {
		return redemptionStatus;
	}
	public void setRedemptionStatus(Integer redemptionStatus) {
		this.redemptionStatus = redemptionStatus;
	}
	@Override
	public String toString() {
		
		return GetClassAttributes.toString(this);
	}
		
}
