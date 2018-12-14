package com.arf.axdshopkeeper.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.axdshopkeeper.entity.UserAccount.IdentityType;
import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "bi_shopkeeper_invite")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "bi_shopkeeper_invite_sequence")
public class ShopkeeperInvite extends BaseEntity<Long> {

	private static final long serialVersionUID = 1L;
	
	private IdentityType identityType;//身份类型枚举:SHOPKEEPER 0 店主;BRANCH 1 联营公司;BIZ_CHANNEL 2 渠道;ANERFA 3 安尔发
	private String inviterName;//邀请人(店主/渠道/联营公司)姓名
	private Integer inviterLevelBefor;//邀请人邀请成功前星级
	private Integer inviterLevelAfter;//邀请人邀请成功前总邀请人数
	private Integer inviterSumBefor;//邀请人邀请成功前总邀请人数
	private Integer inviterSumAfter;//邀请人邀请成功后总邀请人人数
	private Date inviterSuccessDate;//邀请成功时间
	private String inviterUserName;//邀请人用户名
	private String inviterNumber;//邀请人编号,如果是店主邀请,这里就是店主userName,如果是渠道邀请这里就是渠道编号,联营公司邀请则为联营公司编号
	private String invitedUserName;//被邀请人用户名(手机号)
	private String invitedRealName;//被邀请人姓名
	private String communityNumber;//邀请的小区编号
	
	private ReadStatus readStatus;//查看状态
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum ReadStatus{
		NEW,//新的未读
		READ,//已读
		;
	}
	
	@Column(nullable=false)
	public IdentityType getIdentityType() {
		return identityType;
	}
	@Column(length=10,nullable=false)
	public String getInviterName() {
		return inviterName;
	}
	@Column(length=11,nullable=false)
	public Integer getInviterLevelBefor() {
		return inviterLevelBefor;
	}
	@Column(length=11,nullable=false)
	public Integer getInviterLevelAfter() {
		return inviterLevelAfter;
	}
	@Column(length=11,nullable=false)
	public Integer getInviterSumBefor() {
		return inviterSumBefor;
	}
	@Column(length=11,nullable=false)
	public Integer getInviterSumAfter() {
		return inviterSumAfter;
	}
	public Date getInviterSuccessDate() {
		return inviterSuccessDate;
	}
	@Column(length=20,nullable=false)
	public String getInviterNumber() {
		return inviterNumber;
	}
	@Column(length=20,nullable=false)
	public String getInvitedUserName() {
		return invitedUserName;
	}
	@Column(length=20,nullable=false)
	public String getInvitedRealName() {
		return invitedRealName;
	}
	@Column(length=20,nullable=false)
	public String getCommunityNumber() {
		return communityNumber;
	}
	@Column(length=20,nullable=false)
	public String getInviterUserName() {
		return inviterUserName;
	}
	
	public void setIdentityType(IdentityType identityType) {
		this.identityType = identityType;
	}
	public void setInviterName(String inviterName) {
		this.inviterName = inviterName;
	}
	public void setInviterLevelBefor(Integer inviterLevelBefor) {
		this.inviterLevelBefor = inviterLevelBefor;
	}
	public void setInviterLevelAfter(Integer inviterLevelAfter) {
		this.inviterLevelAfter = inviterLevelAfter;
	}
	public void setInviterSumBefor(Integer inviterSumBefor) {
		this.inviterSumBefor = inviterSumBefor;
	}
	public void setInviterSumAfter(Integer inviterSumAfter) {
		this.inviterSumAfter = inviterSumAfter;
	}
	public void setInviterSuccessDate(Date inviterSuccessDate) {
		this.inviterSuccessDate = inviterSuccessDate;
	}
	public void setInviterNumber(String inviterNumber) {
		this.inviterNumber = inviterNumber;
	}
	public void setInvitedUserName(String invitedUserName) {
		this.invitedUserName = invitedUserName;
	}
	public void setInvitedRealName(String invitedRealName) {
		this.invitedRealName = invitedRealName;
	}
	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
	public void setInviterUserName(String inviterUserName) {
		this.inviterUserName = inviterUserName;
	}
	public ReadStatus getReadStatus() {
		return readStatus;
	}
	public void setReadStatus(ReadStatus readStatus) {
		this.readStatus = readStatus;
	}
}
