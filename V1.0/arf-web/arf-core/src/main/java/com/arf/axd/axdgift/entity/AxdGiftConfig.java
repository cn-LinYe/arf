package com.arf.axd.axdgift.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "s_axd_gift_config")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "s_axd_gift_config_sequence")
public class AxdGiftConfig extends BaseEntity<Long>  {

	private static final long serialVersionUID = 1L;
	
	/**
	 * (0-积分,1-现金 ,2-积分现金混合)
	 *
	 */
	public enum GiftType{
		Point,Cash,PointAndCash
	}
	
	private String communityNo;//小区编号
	private BigDecimal giftCashAmount;//点击安心点返还的现金额度,为0表示不赠送
	private Integer giftPointAmount;//点击安心点返还的积分额度, 为0表示不赠送
	private Integer giftCountLimitOfDay;//每天点击安心点有效赠送次数限制,为Integer. MAX_VALUE则不存在该限制
	private BigDecimal giftAmountLimitOfDay;//每天点击安心点有效赠送金额总数限制, 为Integer. MAX_VALUE则不存在该限制
	private Integer giftPointLimitOfDay;//每天点击安心点有效赠送积分总数限制, 为Integer. MAX_VALUE则不存在该限制
	private Integer giftType;//返还类型:0-积分,1-现金 ,2-积分现金混合,等
	private BigDecimal amountPool;//赠送现金金额池,为Integer. MAX_VALUE则不对小区做总额度限制
	private Integer pointPool;//赠送积分池, 为Integer. MAX_VALUE则不对小区做总积分数量限制 
	private Integer checkedPeriod;//签到周期（天）
	private Integer rewardPoints;//签到奖励积分为0表示不赠送
	private Integer rewardSource;//签到奖励来源 0平台赠送1小区赠送积分池
	
	public enum RewardSource{
		Platform,IntegralPool
	}
	
	public String getCommunityNo() {
		return communityNo;
	}
	@Column(precision=12,scale=2)
	public BigDecimal getGiftCashAmount() {
		return giftCashAmount;
	}
	public Integer getGiftPointAmount() {
		return giftPointAmount;
	}
	public Integer getGiftCountLimitOfDay() {
		return giftCountLimitOfDay;
	}
	@Column(precision=12,scale=2)
	public BigDecimal getGiftAmountLimitOfDay() {
		return giftAmountLimitOfDay;
	}
	public Integer getGiftPointLimitOfDay() {
		return giftPointLimitOfDay;
	}
	public Integer getGiftType() {
		return giftType;
	}
	@Column(precision=12,scale=2)
	public BigDecimal getAmountPool() {
		return amountPool;
	}
	public Integer getPointPool() {
		return pointPool;
	}
	public void setCommunityNo(String communityNo) {
		this.communityNo = communityNo;
	}
	public void setGiftCashAmount(BigDecimal giftCashAmount) {
		this.giftCashAmount = giftCashAmount;
	}
	public void setGiftPointAmount(Integer giftPointAmount) {
		this.giftPointAmount = giftPointAmount;
	}
	public void setGiftCountLimitOfDay(Integer giftCountLimitOfDay) {
		this.giftCountLimitOfDay = giftCountLimitOfDay;
	}
	public void setGiftAmountLimitOfDay(BigDecimal giftAmountLimitOfDay) {
		this.giftAmountLimitOfDay = giftAmountLimitOfDay;
	}
	public void setGiftPointLimitOfDay(Integer giftPointLimitOfDay) {
		this.giftPointLimitOfDay = giftPointLimitOfDay;
	}
	public void setGiftType(Integer giftType) {
		this.giftType = giftType;
	}
	public void setAmountPool(BigDecimal amountPool) {
		this.amountPool = amountPool;
	}
	public void setPointPool(Integer pointPool) {
		this.pointPool = pointPool;
	}
	public Integer getCheckedPeriod() {
		return checkedPeriod;
	}
	public Integer getRewardPoints() {
		return rewardPoints;
	}
	public Integer getRewardSource() {
		return rewardSource;
	}
	public void setCheckedPeriod(Integer checkedPeriod) {
		this.checkedPeriod = checkedPeriod;
	}
	public void setRewardPoints(Integer rewardPoints) {
		this.rewardPoints = rewardPoints;
	}
	public void setRewardSource(Integer rewardSource) {
		this.rewardSource = rewardSource;
	}
	
}
