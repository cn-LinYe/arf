package com.arf.office.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "office_company_info")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "office_company_info_sequence")
public class OfficeCompanyInfo  extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6515940702908885191L;
	
	private String companyNumber;//企业编号
	private String companyName;//企业名称
	private String cityCode;//所在城市编号
	private String companySignUrl;//公司头像
	private String phone;//联系电话
	private String contactName;//联系人姓名
	private String companyTrade;//	所属行业
	private Integer companyScale;//规模（0、49人及以下;1、50~99人;2、100~499人;3、500~999人;4、1000~4999人；5、5000~9999人；6、10000人及以上）
	private String address;//地址
	private String descriptption;//	公司描述
	private String communityNumber;//小区编号
	private AuditStatus auditStatus;//审核状态（0 New新建 1 Pass审核通过 2 Refuse审核驳回）
	private Boolean needFirstAlert;//首次查询部门是否需要alert提示
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum AuditStatus{
		New,Pass,Refuse;
	}
	@Column(length = 20)
	public String getCompanyNumber() {
		return companyNumber;
	}

	public void setCompanyNumber(String companyNumber) {
		this.companyNumber = companyNumber;
	}
	@Column(length = 32)
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	@Column(length = 10)
	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	@Column(length = 100)
	public String getCompanySignUrl() {
		return companySignUrl;
	}

	public void setCompanySignUrl(String companySignUrl) {
		this.companySignUrl = companySignUrl;
	}
	@Column(length = 20)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Column(length = 30)
	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	@Column(length = 30)
	public String getCompanyTrade() {
		return companyTrade;
	}

	public void setCompanyTrade(String companyTrade) {
		this.companyTrade = companyTrade;
	}

	public Integer getCompanyScale() {
		return companyScale;
	}

	public void setCompanyScale(Integer companyScale) {
		this.companyScale = companyScale;
	}
	@Column(length = 100)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	@Column(length = 500)
	public String getDescriptption() {
		return descriptption;
	}

	public void setDescriptption(String descriptption) {
		this.descriptption = descriptption;
	}
	@Column(length = 2000)
	public String getCommunityNumber() {
		return communityNumber;
	}

	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}

	public AuditStatus getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(AuditStatus auditStatus) {
		this.auditStatus = auditStatus;
	}

	public Boolean getNeedFirstAlert() {
		return needFirstAlert;
	}

	public void setNeedFirstAlert(Boolean needFirstAlert) {
		this.needFirstAlert = needFirstAlert;
	}

}
