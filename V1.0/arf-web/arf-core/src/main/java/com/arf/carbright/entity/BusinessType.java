package com.arf.carbright.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "p_business_type",uniqueConstraints={@UniqueConstraint(columnNames={"businessTypeNum"},name="unique_business_type_num")})
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "business_type_sequence")
public class BusinessType extends BaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 商户类型编码
	private String businessTypeNum;
	// 商户类型名称
	private String businessTypeName;
	// 商户名称
	private String businessName;
	// 商户类型状态（0、启用 1、禁用）
	private Integer status;
	// 创建时间
	private Date createTime;
	// 修改时间
	private Date modifyTime;
	
	//小区编号
	private String communityNumber;
	//物业编号
	private Integer propertyNumber;
	//子公司编号
	private Integer branchId;
	
	private Integer businessNum;

	public Integer getBusinessNum() {
		return businessNum;
	}

	public void setBusinessNum(Integer businessNum) {
		this.businessNum = businessNum;
	}

	@Column(length = 20)
	public String getBusinessTypeNum() {
		return businessTypeNum;
	}

	public void setBusinessTypeNum(String businessTypeNum) {
		this.businessTypeNum = businessTypeNum;
	}

	@Column(length = 32)
	public String getBusinessTypeName() {
		return businessTypeName;
	}

	public void setBusinessTypeName(String businessTypeName) {
		this.businessTypeName = businessTypeName;
	}

	@Column(length = 100)
	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getCommunityNumber() {
		return communityNumber;
	}

	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
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
