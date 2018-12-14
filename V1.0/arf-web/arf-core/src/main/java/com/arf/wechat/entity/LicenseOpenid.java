package com.arf.wechat.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "p_license_openid",uniqueConstraints = {
		@UniqueConstraint(columnNames = { "license","openId"}, name = "unique_license_openId")})
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "p_license_openid_sequence")
public class LicenseOpenid extends BaseEntity<Long>{

	private static final long serialVersionUID = 6885086561004368514L;

	public static final String WECHAT = "WECHAT";//redis配置参数父节点
	public static final String LICENSE_BIND_FREQUENT_LIMIT = "WECHAT_LICENSE_BIND_FREQUENT_LIMIT";//微信公众号绑定车牌验证频率
	
	private String license;//车牌号
	private String openId;//微信openid
	private String nickName;//微信昵称
	private Date boundDate;//绑定日期
	private String axdUserName;//安心点用户名,如果该车辆被安心点用户绑定,自动同步到这个字段
	private Date lastWxUsed;//最近一次微信缴费时间
	private String chassisNumber;//车架号
	private String engineNumber;//发动机号
	private Status status;//状态枚举:NORMAL 0 正常 UNBOUND 1 解绑
	private Verified verified;//验证状态枚举:NOT 0 未验证通过 SUCCESS 1 验证通过
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum Status{
		NORMAL,// 0 正常
		UNBOUND,// 1 解绑
	}
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum Verified{
		NOT,// 0 未验证通过
		SUCCESS,// 1 验证通过
	}
	@Column(length = 10,nullable = false)
	public String getLicense() {
		return license;
	}
	@Column(length = 40,nullable = false)
	public String getOpenId() {
		return openId;
	}
	@Column(length = 20)
	public String getNickName() {
		return nickName;
	}
	@Column(nullable = false)
	public Date getBoundDate() {
		return boundDate;
	}
	@Column(length = 20)
	public String getAxdUserName() {
		return axdUserName;
	}

	public Date getLastWxUsed() {
		return lastWxUsed;
	}
	@Column(length = 20)
	public String getChassisNumber() {
		return chassisNumber;
	}
	@Column(length = 20)
	public String getEngineNumber() {
		return engineNumber;
	}
	@Column(nullable = false)
	public Status getStatus() {
		return status;
	}
	@Column(nullable = false)
	public Verified getVerified() {
		return verified;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public void setBoundDate(Date boundDate) {
		this.boundDate = boundDate;
	}

	public void setAxdUserName(String axdUserName) {
		this.axdUserName = axdUserName;
	}

	public void setLastWxUsed(Date lastWxUsed) {
		this.lastWxUsed = lastWxUsed;
	}

	public void setChassisNumber(String chassisNumber) {
		this.chassisNumber = chassisNumber;
	}

	public void setEngineNumber(String engineNumber) {
		this.engineNumber = engineNumber;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void setVerified(Verified verified) {
		this.verified = verified;
	}
	
}
