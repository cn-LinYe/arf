package com.arf.core.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 安心点小区赠送参数配置缓存,缓存有效期永久有效
 * @author Caolibao
 * 2016年8月26日 下午3:57:46
 *
 */
public class AxdGiftConfig implements Serializable{
	
	private static final long serialVersionUID = 5327456163386290655L;
	public static final String Key_Cached_AxdGiftConfig = "AxdGiftConfig.Map";//配置项参数KEY
	
	private String communityNo;//小区编号
	
	private Integer giftType;//返还类型:0-积分,1-现金 ,2-积分现金混合,等
	private Integer giftCountLimitOfDay;//每天点击安心点有效赠送次数限制,为Integer. MAX_VALUE则不存在该限制
	private BigDecimal giftCashAmount;//点击安心点返还的现金额度,为0表示不赠送
	private BigDecimal giftAmountLimitOfDay;//每天点击安心点有效赠送金额总数限制, 为Integer. MAX_VALUE则不存在该限制
	private BigDecimal amountPool;//赠送现金金额池,为Integer. MAX_VALUE则不对小区做总额度限制
	private Integer giftPointAmount;//点击安心点返还的积分额度, 为0表示不赠送
	private Integer giftPointLimitOfDay;//每天点击安心点有效赠送积分总数限制, 为Integer. MAX_VALUE则不存在该限制
	private Integer pointPool;//赠送积分池, 为Integer. MAX_VALUE则不对小区做总积分数量限制 
	
	/**
	 * 
	 * @return
	 */
	private Integer rewardPoints;//签到奖励积分
	private Integer rewardSource;//签到奖励来源0平台赠送1小区赠送积分池
	
	public Integer getRewardPoints() {
		return rewardPoints;
	}
	public void setRewardPoints(Integer rewardPoints) {
		this.rewardPoints = rewardPoints;
	}
	public Integer getRewardSource() {
		return rewardSource;
	}
	public void setRewardSource(Integer rewardSource) {
		this.rewardSource = rewardSource;
	}
	public String getCommunityNo() {
		return communityNo;
	}
	public BigDecimal getGiftCashAmount() {
		return giftCashAmount;
	}
	public Integer getGiftPointAmount() {
		return giftPointAmount;
	}
	public Integer getGiftCountLimitOfDay() {
		return giftCountLimitOfDay;
	}
	public BigDecimal getGiftAmountLimitOfDay() {
		return giftAmountLimitOfDay;
	}
	public Integer getGiftPointLimitOfDay() {
		return giftPointLimitOfDay;
	}
	public Integer getGiftType() {
		return giftType;
	}
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

}
