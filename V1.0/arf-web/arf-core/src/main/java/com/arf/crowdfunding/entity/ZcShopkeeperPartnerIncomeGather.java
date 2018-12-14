package com.arf.crowdfunding.entity;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "r_zc_shopkeeper_partner_income_gather")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "r_zc_shopkeeper_partner_income_gather")
public class ZcShopkeeperPartnerIncomeGather extends BaseEntity<Long> {
	
	private static final long serialVersionUID = 1L;
	// 小区id
	private String communityNumber;
	// 项目ID
	private String projectId;
	// 合伙人账号
	private String shopkeeperPartnerUser;
	// 店主合伙人
	private Integer shopkeeperPartner;
	//收益条件（0周，1月，2年）
	private Integer incomeInquire;
	// 收益金额
	private BigDecimal incomeAmount;
	// 收益起始日期
	private Date incomeTimeBegin;
	// 收益截止日期
	private Date incomeTimeEnd;
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
	
	public String getCommunityNumber() {
		return communityNumber;
	}
	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
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
	public Integer getIncomeInquire() {
		return incomeInquire;
	}
	public void setIncomeInquire(Integer incomeInquire) {
		this.incomeInquire = incomeInquire;
	}
	public BigDecimal getIncomeAmount() {
		return incomeAmount;
	}
	public void setIncomeAmount(BigDecimal incomeAmount) {
		this.incomeAmount = incomeAmount;
	}
	public Date getIncomeTimeBegin() {
		return incomeTimeBegin;
	}
	public void setIncomeTimeBegin(Date incomeTimeBegin) {
		this.incomeTimeBegin = incomeTimeBegin;
	}
	public Date getIncomeTimeEnd() {
		return incomeTimeEnd;
	}
	public void setIncomeTimeEnd(Date incomeTimeEnd) {
		this.incomeTimeEnd = incomeTimeEnd;
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
