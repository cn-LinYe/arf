package com.arf.carbright.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "r_wash_temporary_user_information")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "r_wash_temporary_user_information_sequence")
public class RWashTemporaryInformation extends BaseEntity<Long>{

	/**
	 * 点滴洗临时用户信息（商户版APP申请或后天添加后可直接进行点滴洗操作）
	 */
	private static final long serialVersionUID = -4856599786032340716L;
	private String  userName;//  用户账号
	private String  communityNumber;//小区编号
	private String  communityName;// 小区名称
	private String  licensePlateNumber;//车牌号码
	private String  loginName;// 商户手机号码
	private Integer businessNum;//服务点名称
	private Date    operatingDate;// 操作时间
	private Date    effectiveDate;// 有效截止时间
	private Integer isMember;//  是否是会员（0非会员,1会员）
	private Integer isImport;//  是否导入白名单(0导入 1未导入)
	private Integer operatingType;// 操作类型（0商户 1系统）
	private String  systemUser;//系统用户账号
	
	public enum IsMember{
		Non_Members,Member;
	}

	public enum IsImport{
		Import,Non_Import;
	}
	
	public enum OperatingType{
		Is_Business,Is_System;
	}
	@Column(length = 40)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(length = 40)
	public String getCommunityNumber() {
		return communityNumber;
	}

	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
	@Column(length = 40)
	public String getCommunityName() {
		return communityName;
	}

	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}
	@Column(length = 40)
	public String getLicensePlateNumber() {
		return licensePlateNumber;
	}

	public void setLicensePlateNumber(String licensePlateNumber) {
		this.licensePlateNumber = licensePlateNumber;
	}
	@Column(length = 40)
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public Integer getBusinessNum() {
		return businessNum;
	}

	public void setBusinessNum(Integer businessNum) {
		this.businessNum = businessNum;
	}

	public Date getOperatingDate() {
		return operatingDate;
	}

	public void setOperatingDate(Date operatingDate) {
		this.operatingDate = operatingDate;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Integer getIsMember() {
		return isMember;
	}

	public void setIsMember(Integer isMember) {
		this.isMember = isMember;
	}

	public Integer getIsImport() {
		return isImport;
	}

	public void setIsImport(Integer isImport) {
		this.isImport = isImport;
	}

	public Integer getOperatingType() {
		return operatingType;
	}

	public void setOperatingType(Integer operatingType) {
		this.operatingType = operatingType;
	}
	@Column(length = 40)
	public String getSystemUser() {
		return systemUser;
	}

	public void setSystemUser(String systemUser) {
		this.systemUser = systemUser;
	}
}
