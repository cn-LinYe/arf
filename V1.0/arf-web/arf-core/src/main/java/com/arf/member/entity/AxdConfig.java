package com.arf.member.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "axd_config")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "axd_config_sequence")
public class AxdConfig extends BaseEntity<Long> {

	private static final long serialVersionUID = 1L;
	
	public enum AuthorizedType{
		times,duration;
	}
	
	private String license;//车牌号
	private String userName;//用户id
	private String parkingId;//停车场ID/小区ID
	private Integer userLevel;//用户等级
	private Byte authorizedType;//授权类型 0;次数1;时间
	private String authorizedTime;//授权时间，如果授权类型为1，则授权时间为固定时长，如果授权类型为0，则授权时间为次数 
	private Integer branchId;//子公司或物业特有信息子公司唯一标识id
	private Integer headOfficeId;//总公司id
	private Integer popertyNumber;//物业唯一标识id
	
	@Column(name="license",length=20)
	public String getLicense() {
		return license;
	}
	@Column(name="user_name",length=20)
	public String getUserName() {
		return userName;
	}
	@Column(name="parking_id",length=50)
	public String getParkingId() {
		return parkingId;
	}
	@Column(name="user_level",length=11)
	public Integer getUserLevel() {
		return userLevel;
	}
	@Column(name="authorized_type",length=4)
	public Byte getAuthorizedType() {
		return authorizedType;
	}
	@Column(name="authorized_time",length=20)
	public String getAuthorizedTime() {
		return authorizedTime;
	}
	@Column(name="branch_id",length=11)
	public Integer getBranchId() {
		return branchId;
	}
	@Column(name="head_office_id",length=11)
	public Integer getHeadOfficeId() {
		return headOfficeId;
	}
	@Column(name="poperty_number",length=11)
	public Integer getPopertyNumber() {
		return popertyNumber;
	}
	public void setLicense(String license) {
		this.license = license;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setParkingId(String parkingId) {
		this.parkingId = parkingId;
	}
	public void setUserLevel(Integer userLevel) {
		this.userLevel = userLevel;
	}
	public void setAuthorizedType(Byte authorizedType) {
		this.authorizedType = authorizedType;
	}
	public void setAuthorizedTime(String authorizedTime) {
		this.authorizedTime = authorizedTime;
	}
	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}
	public void setHeadOfficeId(Integer headOfficeId) {
		this.headOfficeId = headOfficeId;
	}
	public void setPopertyNumber(Integer popertyNumber) {
		this.popertyNumber = popertyNumber;
	}
	
	
}
