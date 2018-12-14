package com.arf.crowdfunding.entity;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.arf.core.entity.BaseEntity;

/**
 * 创业众筹订单表
 * @author LW on 2016-06-20
 * @version 1.0
 */
@Entity
@Table(name = "zc_shopkeeper_partner_order")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "zc_shopkeeper_partner_order_sequence")
public class ZcShopkeeperPartnerOrder extends BaseEntity<Long> {
	
	/**
	 * 
	 */
	public enum IsRewardStatus {

		/**待发放**/
		stay,
		/**已发放**/
		already;
		public static ZcShopkeeperPartnerOrder.IsRewardStatus get(Integer ordinal){
			ZcShopkeeperPartnerOrder.IsRewardStatus statuss[] = ZcShopkeeperPartnerOrder.IsRewardStatus.values();
			if(ordinal > statuss.length - 1){
				return null;
			}else{
				return statuss[ordinal];
			}
		}
	}
	
	public enum Status {

		/**已提交**/
		submit,
		/**已取消**/
		cancel,	
		/**超时取消**/
		overtimeCancel,
		/**已失败**/
		fail,
		/**已完成**/
		complete;
		public static ZcShopkeeperPartnerOrder.Status get(Integer ordinal){
			ZcShopkeeperPartnerOrder.Status statuss[] = ZcShopkeeperPartnerOrder.Status.values();
			if(ordinal > statuss.length - 1){
				return null;
			}else{
				return statuss[ordinal];
			}
		}
	}
	
	public enum PayStatus{
		
		/**未支付**/
		no,
		/**已支付**/
		yes,
		/**支付失败**/
		fail;
		public static ZcShopkeeperPartnerOrder.PayStatus get(Integer ordinal){
			ZcShopkeeperPartnerOrder.PayStatus statuss[] = ZcShopkeeperPartnerOrder.PayStatus.values();
			if(ordinal > statuss.length - 1){
				return null;
			}else{
				return statuss[ordinal];
			}
		}
	}
	
	public enum Paymode{
		/**钱包支付**/
		account,
		/**微信支付**/
		weixin,
		/**支付宝支付**/
		alipay,
		/**银行卡**/
		bank,
		/**现金**/
		cash;
		public static ZcShopkeeperPartnerOrder.Paymode get(Integer ordinal){
			ZcShopkeeperPartnerOrder.Paymode statuss[] = ZcShopkeeperPartnerOrder.Paymode.values();
			if(ordinal > statuss.length - 1){
				return null;
			}else{
				return statuss[ordinal];
			}
		}
	}
	
	public enum ShopkeeperPartner{
		/**店主**/
		shopkeeper,
		/**合伙人**/
		partner;
		public static ZcShopkeeperPartnerOrder.ShopkeeperPartner get(Integer ordinal){
			ZcShopkeeperPartnerOrder.ShopkeeperPartner statuss[] = ZcShopkeeperPartnerOrder.ShopkeeperPartner.values();
			if(ordinal > statuss.length - 1){
				return null;
			}else{
				return statuss[ordinal];
			}
		}
	}
	private static final long serialVersionUID = 1L;
	// 小区id
	private String communityNumber;
	//订单编号
	private String orderNo;
	// 项目ID
	private String projectId;
	// 合伙人账号
	private String shopkeeperPartnerUser;
	//合伙人姓名
	private String shopkeeperPartnerName;
	//车牌
	private String license;
	//手机号
	private String mobile;
	//最小众筹金额
	private BigDecimal leastPartnerAmount=BigDecimal.ZERO;
	//众筹金额
	private BigDecimal crowdfundingAmount;
	//超时时间
	private Integer overTime = 0;
	
	//订单状态 :0.已提交  1.已取消 2.超时取消 3.已失败  4.已完成
	private Integer status;	
	//支付状态:0.未支付 1.已支付  2.支付失败
	private Integer payStatus = -1;	
	//支付类型 0.钱包支付 1.微信支付 2.支付宝支付 3. 银行卡  4.现金
	private Integer paymode;	
	//店主合伙人 0.店主 1.合伙人
	private Integer shopkeeperPartner;
	//支付订单编号
	private String outTradeNo;
	// 区域权限
	private Integer regionalAuthority = 0;	
	// 发放时间
	private Date releaseDate;
	//物业编号 
	private Integer propertyNumber;
	//子公司编号 
	private Integer branchId;

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


	public Integer getRegionalAuthority() {
		return regionalAuthority;
	}

	public void setRegionalAuthority(Integer regionalAuthority) {
		this.regionalAuthority = regionalAuthority;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	
	@Column(length = 40)
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	@Column(length = 32)
	public String getShopkeeperPartnerName() {
		return shopkeeperPartnerName;
	}

	public void setShopkeeperPartnerName(String shopkeeperPartnerName) {
		this.shopkeeperPartnerName = shopkeeperPartnerName;
	}

	@Column(length = 32)
	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	@Column(length = 32)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(precision = 10, scale = 2)
	public BigDecimal getLeastPartnerAmount() {
		return leastPartnerAmount;
	}

	public void setLeastPartnerAmount(BigDecimal leastPartnerAmount) {
		this.leastPartnerAmount = leastPartnerAmount;
	}

	@Column(precision = 10, scale = 2)
	public BigDecimal getCrowdfundingAmount() {
		return crowdfundingAmount;
	}

	public void setCrowdfundingAmount(BigDecimal crowdfundingAmount) {
		this.crowdfundingAmount = crowdfundingAmount;
	}

	public Integer getOverTime() {
		return overTime;
	}

	public void setOverTime(Integer overTime) {
		this.overTime = overTime;
	}

	@Column(length = 40)
	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public Integer getPaymode() {
		return paymode;
	}

	public void setPaymode(Integer paymode) {
		this.paymode = paymode;
	}

	public Integer getShopkeeperPartner() {
		return shopkeeperPartner;
	}

	public void setShopkeeperPartner(Integer shopkeeperPartner) {
		this.shopkeeperPartner = shopkeeperPartner;
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
