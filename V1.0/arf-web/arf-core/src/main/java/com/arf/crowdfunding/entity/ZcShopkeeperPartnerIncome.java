package com.arf.crowdfunding.entity;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

/**
 * 创业众筹收益表
 * @author LW on 2016-06-20
 * @version 1.0
 *
 */
@Entity
@Table(name = "r_zc_shopkeeper_partner_income")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "r_zc_shopkeeper_partner_income_sequence")
public class ZcShopkeeperPartnerIncome extends BaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 小区id
	private String communityNumber;
	// 项目ID
	private String projectId;
	// 合伙人账号
	private String shopkeeperPartnerUser;
	// 店主合伙人
	private Integer shopkeeperPartner;
	// 收益金额
	private BigDecimal incomeAmount;
	// 收益日期
	private Date incomeTime;
	// 区域权限
	private Integer regionalAuthority;
	// 是否已发放：0待发放，1已发放
	private Integer isRewardStatus;
	// 发放时间
	private Date releaseDate;
	//物业编号 
	private Integer propertyNumber;
	//子公司编号 
	private Integer branchId;
	
	public enum isRewardStatus {
		NotIssued, AlreadyIssued
	}

	@Column(length = 32)
	public String getCommunityNumber() {
		return communityNumber;
	}

	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}

	@Column(length = 32)
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	@Column(length = 32)
	public String getShopkeeperPartnerUser() {
		return shopkeeperPartnerUser;
	}

	public void setShopkeeperPartnerUser(String shopkeeperPartnerUser) {
		this.shopkeeperPartnerUser = shopkeeperPartnerUser;
	}

	public Integer getShopkeeperPartner() {
		return shopkeeperPartner;
	}

	public void setShopkeeperPartner(Integer shopkeeperPartner) {
		this.shopkeeperPartner = shopkeeperPartner;
	}

	@Column(precision = 10, scale = 2)
	public BigDecimal getIncomeAmount() {
		return incomeAmount;
	}

	public void setIncomeAmount(BigDecimal incomeAmount) {
		this.incomeAmount = incomeAmount;
	}

	public Date getIncomeTime() {
		return incomeTime;
	}

	public void setIncomeTime(Date incomeTime) {
		this.incomeTime = incomeTime;
	}

	public Integer getRegionalAuthority() {
		return regionalAuthority;
	}

	public void setRegionalAuthority(Integer regionalAuthority) {
		this.regionalAuthority = regionalAuthority;
	}

	public Integer getIsRewardStatus() {
		return isRewardStatus;
	}

	public void setIsRewardStatus(Integer isRewardStatus) {
		this.isRewardStatus = isRewardStatus;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
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

}
