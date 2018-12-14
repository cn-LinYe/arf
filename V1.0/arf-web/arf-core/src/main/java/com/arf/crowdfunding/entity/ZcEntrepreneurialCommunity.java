package com.arf.crowdfunding.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

/**
 * 小区众筹店铺表
 * 
 * @author LW on 2016-06-18
 * @version 1.0
 */
@Entity
@Table(name = "zc_entrepreneurial_community")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "zc_entrepreneurial_community_sequence")
public class ZcEntrepreneurialCommunity extends BaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 小区id
	private String communityNumber;
	// 项目ID
	private String projectId;
	// 小区规模
	private Integer communitySize;
	// 车位数
	private Integer parkingNumber;
	// 店铺名称
	private String shopName;
	// 开店时间
	private Date shopDate;
	// 众筹开始时间
	private Date crowdfundingStartDate;
	// 店主金额
	private BigDecimal shopkeeperAmount;
	// 合伙人众筹金额
	private BigDecimal partnerAmount;
	// 最小众筹金额
	private BigDecimal leastPartnerAmount;
	// 合伙人最少能购买股数
	private Integer partnerCountMin;

	// 合伙人最多能购买股数
	private Integer partnerCountMax;
	// 筹款时间（10-60）天
	private Integer crowdfundingDays;
	// 平台服务费（10%）
	private Integer platformServiceFees;
	// 耗材成本（辆）
	private BigDecimal consumablesCosts;

	private BigDecimal laborCost;
	// 设备折旧费(月)
	private BigDecimal slottingAllowances;
	// 设备折旧费(月)
	private BigDecimal depreciationCosts;
	// 股权比例（默认51%）
	private Integer equityRatio;
	// 店铺推荐人
	private String recommended;
	// 推荐人奖金 （元）
	private BigDecimal recommendedAmount;
	// 推荐人联系方式
	private String recommendedMobile;
	// 店铺状态（0未开店1招募中2开店中3正常营业4停业整修5停业整顿6关停7众筹失败已取消）
	private Integer shopStatus;
	// 区域权限(权限控制，本区域运营人员仅可见自己本区域信息)
	private Integer regionalAuthority;
	// 状态（0、禁用 1、可用）
	private Integer status;
	//物业编号 
	private Integer propertyNumber;
	//子公司编号 
	private Integer branchId;

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

	public enum ShopStatus {
		NotOpenShop, Recruiting, SetUpShop, NormalBusiness, ClosureRepair, SuspendBusinessRectification, ShutRown

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

	public Integer getCommunitySize() {
		return communitySize;
	}

	public void setCommunitySize(Integer communitySize) {
		this.communitySize = communitySize;
	}

	public Integer getParkingNumber() {
		return parkingNumber;
	}

	public void setParkingNumber(Integer parkingNumber) {
		this.parkingNumber = parkingNumber;
	}

	@Column(length = 32)
	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	@Column(precision = 10, scale = 2)
	public BigDecimal getShopkeeperAmount() {
		return shopkeeperAmount;
	}

	public void setShopkeeperAmount(BigDecimal shopkeeperAmount) {
		this.shopkeeperAmount = shopkeeperAmount;
	}

	public BigDecimal getPartnerAmount() {
		return partnerAmount;
	}

	public void setPartnerAmount(BigDecimal partnerAmount) {
		this.partnerAmount = partnerAmount;
	}

	public BigDecimal getLeastPartnerAmount() {
		return leastPartnerAmount;
	}

	public void setLeastPartnerAmount(BigDecimal leastPartnerAmount) {
		this.leastPartnerAmount = leastPartnerAmount;
	}

	public Integer getCrowdfundingDays() {
		return crowdfundingDays;
	}

	public void setCrowdfundingDays(Integer crowdfundingDays) {
		this.crowdfundingDays = crowdfundingDays;
	}

	public Integer getEquityRatio() {
		return equityRatio;
	}

	public void setEquityRatio(Integer equityRatio) {
		this.equityRatio = equityRatio;
	}

	public Integer getShopStatus() {
		return shopStatus;
	}

	public void setShopStatus(Integer shopStatus) {
		this.shopStatus = shopStatus;
	}

	public Integer getRegionalAuthority() {
		return regionalAuthority;
	}

	public void setRegionalAuthority(Integer regionalAuthority) {
		this.regionalAuthority = regionalAuthority;
	}

	public Integer getPartnerCountMin() {
		return partnerCountMin;
	}

	public void setPartnerCountMin(Integer partnerCountMin) {
		this.partnerCountMin = partnerCountMin;
	}

	public Integer getPartnerCountMax() {
		return partnerCountMax;
	}

	public void setPartnerCountMax(Integer partnerCountMax) {
		this.partnerCountMax = partnerCountMax;
	}

	public Date getCrowdfundingStartDate() {
		return crowdfundingStartDate;
	}

	public void setCrowdfundingStartDate(Date crowdfundingStartDate) {
		this.crowdfundingStartDate = crowdfundingStartDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getPlatformServiceFees() {
		return platformServiceFees;
	}

	public void setPlatformServiceFees(Integer platformServiceFees) {
		this.platformServiceFees = platformServiceFees;
	}

	public Date getShopDate() {
		return shopDate;
	}

	public void setShopDate(Date shopDate) {
		this.shopDate = shopDate;
	}

	@Column(precision = 10, scale = 2)
	public BigDecimal getConsumablesCosts() {
		return consumablesCosts;
	}

	public void setConsumablesCosts(BigDecimal consumablesCosts) {
		this.consumablesCosts = consumablesCosts;
	}

	@Column(precision = 10, scale = 2)
	public BigDecimal getLaborCost() {
		return laborCost;
	}

	public void setLaborCost(BigDecimal laborCost) {
		this.laborCost = laborCost;
	}

	@Column(precision = 10, scale = 2)
	public BigDecimal getSlottingAllowances() {
		return slottingAllowances;
	}

	public void setSlottingAllowances(BigDecimal slottingAllowances) {
		this.slottingAllowances = slottingAllowances;
	}

	@Column(precision = 10, scale = 2)
	public BigDecimal getDepreciationCosts() {
		return depreciationCosts;
	}

	public void setDepreciationCosts(BigDecimal depreciationCosts) {
		this.depreciationCosts = depreciationCosts;
	}

	@Column(length = 32)
	public String getRecommended() {
		return recommended;
	}

	public void setRecommended(String recommended) {
		this.recommended = recommended;
	}

	@Column(precision = 10, scale = 2)
	public BigDecimal getRecommendedAmount() {
		return recommendedAmount;
	}

	public void setRecommendedAmount(BigDecimal recommendedAmount) {
		this.recommendedAmount = recommendedAmount;
	}

	@Column(length = 32)
	public String getRecommendedMobile() {
		return recommendedMobile;
	}

	public void setRecommendedMobile(String recommendedMobile) {
		this.recommendedMobile = recommendedMobile;
	}

}
