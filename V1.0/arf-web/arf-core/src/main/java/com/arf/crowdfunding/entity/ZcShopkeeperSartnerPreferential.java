package com.arf.crowdfunding.entity;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.carbright.entity.RPackageRecord;
import com.arf.core.entity.BaseEntity;

/**
 * 创业众筹优惠表
 * @author LW on 2016-06-20
 * @version 1.0
 *
 */
@Entity
@Table(name = "zc_shopkeeper_partner_preferential")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "zc_shopkeeper_partner_preferential_sequence")
public class ZcShopkeeperSartnerPreferential extends BaseEntity<Long> {

	public enum ShopkeeperPartner {

		/**店主**/
		shopKeeper,
		/**合伙人**/
		partner;
		
		public static ZcShopkeeperSartnerPreferential.ShopkeeperPartner get(int ordinal){
			ZcShopkeeperSartnerPreferential.ShopkeeperPartner statuss[] = ZcShopkeeperSartnerPreferential.ShopkeeperPartner.values();
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
	// 项目ID
	private String projectId;
	// 店主合伙人 0 店主 1 合伙人
	private int shopkeeperPartner;
	//每股金额数
	private BigDecimal leastPartnerAmount;
	//洗车券套餐ID
	private int packageTypeId;
	// 区域权限
	private int regionalAuthority;
	
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
	
	@Column(precision = 10, scale = 2)
	public BigDecimal getLeastPartnerAmount() {
		return leastPartnerAmount;
	}
	public void setLeastPartnerAmount(BigDecimal leastPartnerAmount) {
		this.leastPartnerAmount = leastPartnerAmount;
	}
	
	public int getPackageTypeId() {
		return packageTypeId;
	}
	public void setPackageTypeId(int packageTypeId) {
		this.packageTypeId = packageTypeId;
	}
	public int getRegionalAuthority() {
		return regionalAuthority;
	}
	public void setRegionalAuthority(int regionalAuthority) {
		this.regionalAuthority = regionalAuthority;
	}
	public int getShopkeeperPartner() {
		return shopkeeperPartner;
	}
	public void setShopkeeperPartner(int shopkeeperPartner) {
		this.shopkeeperPartner = shopkeeperPartner;
	}

	

}
