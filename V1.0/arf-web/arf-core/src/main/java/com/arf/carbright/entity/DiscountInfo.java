package com.arf.carbright.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 优惠信息表
 * @author dg
 *
 */
@Entity
@Table(name = "p_discount_info")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "p_discount_info_sequence")
public class DiscountInfo extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5132515239760373964L;
	
	private Integer businessNum;//business_num	int	商户编号
	private String businessName	;//Varchar(100)	商户名称
	private Discount discount	;//int	优惠类型（0：日常优惠1限时优惠）
	private DiscountType discountType	;//int	折扣类型（0:固定折扣1:满额折扣）
	private Float percentage	;//float	固定折扣百分比(%)
	private BigDecimal minAmount	;//decimal	满足多少开始折扣(满额折扣)
	private BigDecimal discountAmount	;//decimal	折扣多少(满额折扣)
	private IsRepeatDiscount isRepeatDiscount	;//int	是否可重复折扣（0否1：是）
	private DiscountFrequency discountFrequency	;//tinyint	限时优惠频率（0:按天1:按周2:按月）
	private Status status	;//int	优惠状态（0:关闭1:开启）
	private String description	;//Varchar(512)	描述
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum Discount{
		daily,timeLimit
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum DiscountType{
		changeless,fullAmount
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum IsRepeatDiscount{
		no,yes
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum DiscountFrequency{
		day,week,month
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum Status{
		close,open
	}
	public Integer getBusinessNum() {
		return businessNum;
	}
	public void setBusinessNum(Integer businessNum) {
		this.businessNum = businessNum;
	}
	@Column(length=40)
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public Discount getDiscount() {
		return discount;
	}
	public void setDiscount(Discount discount) {
		this.discount = discount;
	}
	public DiscountType getDiscountType() {
		return discountType;
	}
	public void setDiscountType(DiscountType discountType) {
		this.discountType = discountType;
	}
	public Float getPercentage() {
		return percentage;
	}
	public void setPercentage(Float percentage) {
		this.percentage = percentage;
	}
	@Column(precision=10,scale=2)
	public BigDecimal getMinAmount() {
		return minAmount;
	}
	public void setMinAmount(BigDecimal minAmount) {
		this.minAmount = minAmount;
	}
	@Column(precision=10,scale=2)
	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(BigDecimal discountAmount) {
		this.discountAmount = discountAmount;
	}
	public IsRepeatDiscount getIsRepeatDiscount() {
		return isRepeatDiscount;
	}
	public void setIsRepeatDiscount(IsRepeatDiscount isRepeatDiscount) {
		this.isRepeatDiscount = isRepeatDiscount;
	}
	public DiscountFrequency getDiscountFrequency() {
		return discountFrequency;
	}
	public void setDiscountFrequency(DiscountFrequency discountFrequency) {
		this.discountFrequency = discountFrequency;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	
	@Column(length=512)
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
	

}
