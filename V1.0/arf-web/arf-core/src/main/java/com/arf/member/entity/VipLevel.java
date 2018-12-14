package com.arf.member.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(
		name="s_vip_level",
		uniqueConstraints={
				@UniqueConstraint(columnNames={"level"},name="uk_level")})
@SequenceGenerator(name="sequenceGenerator",sequenceName="s_vip_level_sequence")
public class VipLevel extends BaseEntity<Long> {

	private static final long serialVersionUID = 3162626089007725239L;
	
	private Integer level;//VIP等级,初始用户都为等级0,即注册会员
	private String levelName;//VIP等级名称,eg.黄金会员
	private Integer expirationDays;//有效天数
	private BigDecimal privilegeSmartLock;//特权-购智能门锁折扣
	private BigDecimal privilegeGoldCard;//特权-购买停车卡折扣
	private BigDecimal privilegeShopingMall;//特权-购买商城商品折扣
	private String privilegeLottery;//特权-大抽奖
	private String privilegeCarbrighter;//特权-点滴洗优惠及赠送券
	private long requiredExperience;//升级所需经验
	
	@Column(length=11, nullable=false)
	public Integer getLevel() {
		return level;
	}
	@Column(length=20, nullable=false)
	public String getLevelName() {
		return levelName;
	}
	@Column(length=11)
	public Integer getExpirationDays() {
		return expirationDays;
	}
	@Column(name="privilege_smartlock",precision=10,scale=2)
	public BigDecimal getPrivilegeSmartLock() {
		return privilegeSmartLock;
	}
	@Column(precision=10,scale=2)
	public BigDecimal getPrivilegeGoldCard() {
		return privilegeGoldCard;
	}
	@Column(precision=10,scale=2)
	public BigDecimal getPrivilegeShopingMall() {
		return privilegeShopingMall;
	}
	@Column(length=200)
	public String getPrivilegeLottery() {
		return privilegeLottery;
	}
	@Column(length=200)
	public String getPrivilegeCarbrighter() {
		return privilegeCarbrighter;
	}
	@Column(nullable=false)
	public long getRequiredExperience() {
		return requiredExperience;
	}
	public void setRequiredExperience(long requiredExperience) {
		this.requiredExperience = requiredExperience;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	public void setExpirationDays(Integer expirationDays) {
		this.expirationDays = expirationDays;
	}
	public void setPrivilegeSmartLock(BigDecimal privilegeSmartLock) {
		this.privilegeSmartLock = privilegeSmartLock;
	}
	public void setPrivilegeGoldCard(BigDecimal privilegeGoldCard) {
		this.privilegeGoldCard = privilegeGoldCard;
	}
	public void setPrivilegeShopingMall(BigDecimal privilegeShopingMall) {
		this.privilegeShopingMall = privilegeShopingMall;
	}
	public void setPrivilegeLottery(String privilegeLottery) {
		this.privilegeLottery = privilegeLottery;
	}
	public void setPrivilegeCarbrighter(String privilegeCarbrighter) {
		this.privilegeCarbrighter = privilegeCarbrighter;
	}
	
}
