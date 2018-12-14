package com.arf.gift.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "gift_rebate_ratio",
	uniqueConstraints={@UniqueConstraint(columnNames={"rebateType","communityNumber"})})
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "gift_rebate_ratio_sequence")
public class RebateRatio extends BaseEntity<Long>{

	private static final long serialVersionUID = 8737841147587384036L;

	private Integer OneMonthRebate;//一个月折扣百分比
	private Integer ThreeMonthRebate;//三个月折扣百分比
	private Integer SixMonthRebate;//六个月折扣百分比
	private Integer OneYearRebate;//一年折扣百分比
	private Integer TwoYearRebate;//两年折扣百分比
	private RebateType rebateType;//折扣类型：ECC_MONTH_PAY 0 ECC月租缴费
	private String communityNumber;//小区信息
	private Integer effectiveDate;//有效期（分钟）
	private Date startOfferDate;//开启优惠时间
	private Date endOfferDate;//结束优惠时间
	private ExpressDelivery expressDelivery;// 快递枚举ALLOW  0允许NOT_ALLOWED 1 不允许 
	private SinceMention sinceMention;//  自提枚举ALLOW  0允许NOT_ALLOWED 1 不允许 
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum RebateType{
		ECC_MONTH_PAY;//ECC月租缴费
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum ExpressDelivery{
		ALLOW,NOT_ALLOWED;//快递枚举
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum SinceMention{
		ALLOW,NOT_ALLOWED;// 自提枚举
	}
	@Column(nullable=false)
	public Integer getOneMonthRebate() {
		return OneMonthRebate;
	}
	@Column(nullable=false)
	public Integer getThreeMonthRebate() {
		return ThreeMonthRebate;
	}
	@Column(nullable=false)
	public Integer getSixMonthRebate() {
		return SixMonthRebate;
	}
	@Column(nullable=false)
	public Integer getOneYearRebate() {
		return OneYearRebate;
	}
	@Column(nullable=false)
	public Integer getTwoYearRebate() {
		return TwoYearRebate;
	}
	@Column(nullable=false)
	public RebateType getRebateType() {
		return rebateType;
	}
	@Column(nullable=false,length=40)
	public String getCommunityNumber() {
		return communityNumber;
	}
	@Column(nullable=false)
	public Integer getEffectiveDate() {
		return effectiveDate;
	}

	public Date getStartOfferDate() {
		return startOfferDate;
	}

	public Date getEndOfferDate() {
		return endOfferDate;
	}
	@Column(nullable=false)
	public ExpressDelivery getExpressDelivery() {
		return expressDelivery;
	}
	@Column(nullable=false)
	public SinceMention getSinceMention() {
		return sinceMention;
	}
	public void setExpressDelivery(ExpressDelivery expressDelivery) {
		this.expressDelivery = expressDelivery;
	}
	public void setSinceMention(SinceMention sinceMention) {
		this.sinceMention = sinceMention;
	}
	public void setOneMonthRebate(Integer oneMonthRebate) {
		OneMonthRebate = oneMonthRebate;
	}

	public void setThreeMonthRebate(Integer threeMonthRebate) {
		ThreeMonthRebate = threeMonthRebate;
	}

	public void setSixMonthRebate(Integer sixMonthRebate) {
		SixMonthRebate = sixMonthRebate;
	}

	public void setOneYearRebate(Integer oneYearRebate) {
		OneYearRebate = oneYearRebate;
	}

	public void setTwoYearRebate(Integer twoYearRebate) {
		TwoYearRebate = twoYearRebate;
	}

	public void setRebateType(RebateType rebateType) {
		this.rebateType = rebateType;
	}

	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}

	public void setEffectiveDate(Integer effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public void setStartOfferDate(Date startOfferDate) {
		this.startOfferDate = startOfferDate;
	}

	public void setEndOfferDate(Date endOfferDate) {
		this.endOfferDate = endOfferDate;
	}

}
