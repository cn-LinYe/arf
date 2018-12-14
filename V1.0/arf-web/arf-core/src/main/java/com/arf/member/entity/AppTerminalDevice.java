package com.arf.member.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

/**
 * 用户终端设备记录表
 * @author Caolibao
 *
 */
@Entity
@Table(name = "app_terminal_device")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "app_terminal_device_sequence")
public class AppTerminalDevice extends BaseEntity<Long> {
	private static final long serialVersionUID = 1L;
	
	private String userName;//用户名
	private String deviceName;//设备名称
	private Integer osName;//手机操作系统:Android:0;IOS :1
    private String osVersion;//手机os版本
    private String uniqueCode;//设备唯一标识,可以是手机串号或者第三方唯一识别号(极光SDK registrationId)
    private Date lastLoginDate;
    
    public AppTerminalDevice(){
    	super();
    }
    
    public AppTerminalDevice(String userName, String deviceName, Integer osName, String osVersion, String uniqueCode,
			Date lastLoginDate) {
		super();
		this.userName = userName;
		this.deviceName = deviceName;
		this.osName = osName;
		this.osVersion = osVersion;
		this.uniqueCode = uniqueCode;
		this.lastLoginDate = lastLoginDate;
	}
	@Column(length=30)
    public String getUserName() {
		return userName;
	}
	@Column(length=30)
	public String getDeviceName() {
		return deviceName;
	}
	public Integer getOsName() {
		return osName;
	}
	 @Column(length=20)
	public String getOsVersion() {
		return osVersion;
	}
	@Column(length=50)
	public String getUniqueCode() {
		return uniqueCode;
	}
	public Date getLastLoginDate() {
		return lastLoginDate;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public void setOsName(Integer osName) {
		this.osName = osName;
	}
	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}
	public void setUniqueCode(String uniqueCode) {
		this.uniqueCode = uniqueCode;
	}
	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
}
