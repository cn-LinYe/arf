package com.arf.eparking.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "p_bolin_Kad")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "bolin_Kad_sequence")
public class BolinKadModel extends BaseEntity<Long> {

	private static final long serialVersionUID = 6181232285559307891L;

	/* 用户账号 */
	private String userName;
	/* 订单号 */
	private String orderNo;
	/* 业务类型 0 月租缴费 1临停缴费 */
	private Integer businessType;	
	/* 车牌号 */
	private String license;
	/* 小区编号 */
	private String communityNumber;
	/* 物业编号*/
	private Integer propertyNumber;
	/* 子公司编号 */
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public Integer getBusinessType() {
		return businessType;
	}
	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}
	public String getLicense() {
		return license;
	}
	public void setLicense(String license) {
		this.license = license;
	}
	public String getCommunityNumber() {
		return communityNumber;
	}
	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
}
