package com.arf.axdshopkeeper.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.axdshopkeeper.entity.UserAccount.IdentityType;
import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "bi_reward_record")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "bi_reward_record_sequence")
public class RewardRecord extends BaseEntity<Long> {

	private static final long serialVersionUID = 1L;

	private String userName;//店主用户名/渠道编号/联营公司编号
	private RewardType rewardType;//奖励类型枚举:REWARD 0 奖励;STIMULATE 1 激励;CHANNEL_MGR_FEE 2 渠道管理费
	private BigDecimal amount;//金额
	private String rewardFrom;//奖励来源店主,即成功邀请的下级
	private WithdrawType withdrawType;//提现类型枚举:DIRECT 0 直提;ADDTION 补提
	private IdentityType identityType;//身份类型枚举:SHOPKEEPER 0 店主;BRANCH 1 联营公司;BIZ_CHANNEL 2 渠道;ANERFA 3 安尔发
	private Long ruleId;//规则表主键id
	private Long inviteId;//邀请记录表主键id
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum RewardType{
		REWARD,
		STIMULATE,
		CHANNEL_MGR_FEE;
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum WithdrawType{
		DIRECT,
		ADDTION;
	}
	
	@Column(length=20,nullable=false)
	public String getUserName() {
		return userName;
	}
	@Column(nullable=false)
	public RewardType getRewardType() {
		return rewardType;
	}
	@Column(precision = 10, scale = 2,nullable = false)
	public BigDecimal getAmount() {
		return amount;
	}
	@Column(length=20,nullable=false)
	public String getRewardFrom() {
		return rewardFrom;
	}
	@Column(nullable=false)
	public WithdrawType getWithdrawType() {
		return withdrawType;
	}
	public IdentityType getIdentityType() {
		return identityType;
	}
	public Long getRuleId() {
		return ruleId;
	}
	public Long getInviteId() {
		return inviteId;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setRewardType(RewardType rewardType) {
		this.rewardType = rewardType;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public void setRewardFrom(String rewardFrom) {
		this.rewardFrom = rewardFrom;
	}
	public void setWithdrawType(WithdrawType withdrawType) {
		this.withdrawType = withdrawType;
	}
	public void setIdentityType(IdentityType identityType) {
		this.identityType = identityType;
	}
	public void setRuleId(Long ruleId) {
		this.ruleId = ruleId;
	}
	public void setInviteId(Long inviteId) {
		this.inviteId = inviteId;
	}
	
	
}
