package com.arf.gas.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "refuel_gas_reward_card_conf")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "refuel_gas_reward_card_conf_sequence")
public class RefuelGasRewardCardConf extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1933656635640858872L;
	
	private String policyName;//政策名称
	private String policyCopy;//政策文案，APP显示用
	private Integer cardAmount;//	开卡金额
	private Integer rewardAmount;//奖励金额（商户）批量开卡时是比例
	private Integer rewardTotalAmount;//商户奖励总共金额
	private Integer userRewardTotalAmount;//用户总共金额
	private Integer businessNum;//商户编号
	private Integer gasNum;//油站编号
	private Date effectiveStart;//有效起始日期
	private Date effectiveEnd;//有效结束日期
	private Status status;//状态信息(0、未生效 Not_Active 1、生效 Active 2、已冻结 Frozen)
	private PolicyType policyType;//奖励类型 0、APP申请 App_Apply 1、零售办卡 Retail_Card 2、批量办卡 Batch_Card
	private Integer userRewardAmount;//用户奖励金额
	private Integer cardRangeAmount;//批量办卡开卡金额
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum Status{
		Not_Active,//未生效
		Active,//生效
		Frozen;// 已冻结
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum PolicyType{
		App_Apply,//0、APP申请
		Retail_Card,//1、零售办卡 
		Batch_Card;// 2、批量办卡
	}

	
	public Integer getUserRewardTotalAmount() {
		return userRewardTotalAmount;
	}

	public void setUserRewardTotalAmount(Integer userRewardTotalAmount) {
		this.userRewardTotalAmount = userRewardTotalAmount;
	}

	@Column(length=100)
	public String getPolicyName() {
		return policyName;
	}

	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}

	@Column(length=100)
	public String getPolicyCopy() {
		return policyCopy;
	}

	public void setPolicyCopy(String policyCopy) {
		this.policyCopy = policyCopy;
	}

	public Integer getCardAmount() {
		return cardAmount;
	}

	public void setCardAmount(Integer cardAmount) {
		this.cardAmount = cardAmount;
	}

	public Integer getRewardAmount() {
		return rewardAmount;
	}

	public void setRewardAmount(Integer rewardAmount) {
		this.rewardAmount = rewardAmount;
	}

	public Integer getRewardTotalAmount() {
		return rewardTotalAmount;
	}

	public void setRewardTotalAmount(Integer rewardTotalAmount) {
		this.rewardTotalAmount = rewardTotalAmount;
	}

	public Integer getBusinessNum() {
		return businessNum;
	}

	public void setBusinessNum(Integer businessNum) {
		this.businessNum = businessNum;
	}

	public Integer getGasNum() {
		return gasNum;
	}

	public void setGasNum(Integer gasNum) {
		this.gasNum = gasNum;
	}

	public Date getEffectiveStart() {
		return effectiveStart;
	}

	public void setEffectiveStart(Date effectiveStart) {
		this.effectiveStart = effectiveStart;
	}

	public Date getEffectiveEnd() {
		return effectiveEnd;
	}

	public void setEffectiveEnd(Date effectiveEnd) {
		this.effectiveEnd = effectiveEnd;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public PolicyType getPolicyType() {
		return policyType;
	}

	public void setPolicyType(PolicyType policyType) {
		this.policyType = policyType;
	}

	public Integer getUserRewardAmount() {
		return userRewardAmount;
	}

	public void setUserRewardAmount(Integer userRewardAmount) {
		this.userRewardAmount = userRewardAmount;
	}

	public Integer getCardRangeAmount() {
		return cardRangeAmount;
	}

	public void setCardRangeAmount(Integer cardRangeAmount) {
		this.cardRangeAmount = cardRangeAmount;
	}

}
