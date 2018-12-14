package com.arf.promotions.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
@Entity
@Table(name = "property_monthly_payment_promotions")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "property_monthly_payment_promotions_sequence")
public class PropertyMonthlyPaymentPromotions extends BaseEntity<Long> {
	
	private static final long serialVersionUID = 1L;
	
	private String communityNumber;//小区信息
	private PaymentType paymentType;//缴费类型（0、Property 物业缴费 1、Monthly 月租缴费）
	private MonthLimit monthLimit;//月份限制（0、不限制 1、Not_Limited 1、限制预缴Limit_Prepaid）
	private Date activityStartDate;//活动起始日期
	private Date activityEndDate;//活动结束日期
	private NoticeType noticeType;//消息通知方式（0、SMS 短消息 1、推送 Push）
	private ParticipateLimit participateLimit;//参与次数限制（0、不限制 Not_Limited 1、Once 一次 2、Rule_Once 每个规则一次）
	private String promotionsChild;//活动管理子项表ID,多个“，”分隔
	private Long advertisementsId;//广告表
	private String couponNum;//优惠编号（用于标记某一个活动）
	private String promotionsName;//活动名称
	private String promotionsDescription;//活动描述
	private PromotionsStatus promotionsStatus;//活动状态（0、正常 normal 1、Not_Available 下架 2 过期）
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum PaymentType{
		Property,
		Monthly;
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum MonthLimit{
		Not_Limited,
		Limit_Prepaid;
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum NoticeType{
		SMS,
		Push;
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum ParticipateLimit{
		Not_Limited,
		Once,
		Rule_Once;
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum PromotionsStatus{
		normal,
		Not_Available,
		deadline;
	}
	
	@Column(length = 20)
	public String getCommunityNumber() {
		return communityNumber;
	}
	public PaymentType getPaymentType() {
		return paymentType;
	}
	public MonthLimit getMonthLimit() {
		return monthLimit;
	}
	public Date getActivityStartDate() {
		return activityStartDate;
	}
	public Date getActivityEndDate() {
		return activityEndDate;
	}
	public NoticeType getNoticeType() {
		return noticeType;
	}
	public ParticipateLimit getParticipateLimit() {
		return participateLimit;
	}
	@Column(length = 20)
	public String getPromotionsChild() {
		return promotionsChild;
	}
	public Long getAdvertisementsId() {
		return advertisementsId;
	}
	@Column(length = 20)
	public String getCouponNum() {
		return couponNum;
	}
	@Column(length = 20)
	public String getPromotionsName() {
		return promotionsName;
	}
	@Column(length = 200)
	public String getPromotionsDescription() {
		return promotionsDescription;
	}
	public PromotionsStatus getPromotionsStatus() {
		return promotionsStatus;
	}
	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}
	public void setMonthLimit(MonthLimit monthLimit) {
		this.monthLimit = monthLimit;
	}
	public void setActivityStartDate(Date activityStartDate) {
		this.activityStartDate = activityStartDate;
	}
	public void setActivityEndDate(Date activityEndDate) {
		this.activityEndDate = activityEndDate;
	}
	public void setNoticeType(NoticeType noticeType) {
		this.noticeType = noticeType;
	}
	public void setParticipateLimit(ParticipateLimit participateLimit) {
		this.participateLimit = participateLimit;
	}
	public void setPromotionsChild(String promotionsChild) {
		this.promotionsChild = promotionsChild;
	}
	public void setAdvertisementsId(Long advertisementsId) {
		this.advertisementsId = advertisementsId;
	}
	public void setCouponNum(String couponNum) {
		this.couponNum = couponNum;
	}
	public void setPromotionsName(String promotionsName) {
		this.promotionsName = promotionsName;
	}
	public void setPromotionsDescription(String promotionsDescription) {
		this.promotionsDescription = promotionsDescription;
	}
	public void setPromotionsStatus(PromotionsStatus promotionsStatus) {
		this.promotionsStatus = promotionsStatus;
	}

}
