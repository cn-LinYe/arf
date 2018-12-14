package com.arf.carbright.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

/**
 * 店长信息记录表
 * 
 * @author dg
 *
 */

@Entity
@Table(name = "r_shopkeeper_data_management")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "r_shopkeeper_data_management_sequence")
public class ShopkeeperDataManagement extends BaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4198209598576273976L;

	// 店主姓名
	private String fullName;
	// 店主电话
	private String phone;
	// 店主邮箱
	private String mailbox;
	// 店主地址
	private String contactAddress;
	// 小区编号
	private String communityNumber;
	// 小区名
	private String communityName;
	// 物业id
	private Integer propertyNumber;
	// 子公司id
	private Integer branchId;
	// 推荐人姓名
	private String referrerName;
	// 推荐人手机
	private String referrerPhone;
	// 留言
	private String message;
	// 报名时间
	private Date registrationDate;
	// 推荐状态 0未采纳1已采纳
	private Integer referrerStatus;
	// 服务点名称
	private Integer businessNum;
	// 安心点会员(0会员1非会员)
	private Integer isMember;
	// 车牌号码
	private String licensePlateNumber;
	// 手机号码归属地
	private String attributionPhone;

	public enum referrerStatus {
		NotAdopted, Adopted;
	}

	public enum IsMember {
		Member, Non_members;
	}
	
	public Integer getIsMember() {
		return isMember;
	}

	public void setIsMember(Integer isMember) {
		this.isMember = isMember;
	}
	@Column(name = "licensePlateNumber", length = 200)
	public String getLicensePlateNumber() {
		return licensePlateNumber;
	}

	public void setLicensePlateNumber(String licensePlateNumber) {
		this.licensePlateNumber = licensePlateNumber;
	}

	@Column(name = "attributionPhone", length = 40)
	public String getAttributionPhone() {
		return attributionPhone;
	}

	public void setAttributionPhone(String attributionPhone) {
		this.attributionPhone = attributionPhone;
	}

	@Column(name = "fullName", length = 40)
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@Column(name = "phone", length = 40)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "mailbox", length = 40)
	public String getMailbox() {
		return mailbox;
	}

	public void setMailbox(String mailbox) {
		this.mailbox = mailbox;
	}

	@Column(name = "contactAddress", length = 200)
	public String getContactAddress() {
		return contactAddress;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

	@Column(name = "communityNumber", length = 40)
	public String getCommunityNumber() {
		return communityNumber;
	}

	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}

	@Column(name = "communityName", length = 40)
	public String getCommunityName() {
		return communityName;
	}

	public void setCommunityName(String communityName) {
		this.communityName = communityName;
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

	@Column(name = "referrerName", length = 40)
	public String getReferrerName() {
		return referrerName;
	}

	public void setReferrerName(String referrerName) {
		this.referrerName = referrerName;
	}

	@Column(name = "referrerPhone", length = 40)
	public String getReferrerPhone() {
		return referrerPhone;
	}

	public void setReferrerPhone(String referrerPhone) {
		this.referrerPhone = referrerPhone;
	}

	@Column(name = "message", length = 200)
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public Integer getReferrerStatus() {
		return referrerStatus;
	}

	public void setReferrerStatus(Integer referrerStatus) {
		this.referrerStatus = referrerStatus;
	}

	public Integer getBusinessNum() {
		return businessNum;
	}

	public void setBusinessNum(Integer businessNum) {
		this.businessNum = businessNum;
	}

}
