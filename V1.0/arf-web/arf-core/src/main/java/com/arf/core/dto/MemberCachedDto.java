package com.arf.core.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 缓存用户Member对象的基础信息,主要适用于登录鉴权的拦截器等
 * @author Caolibao
 * 2016年7月15日 下午12:07:49
 *
 */
public class MemberCachedDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 用户鉴权documentCode缓存前缀
	 */
	public static final String Prefix_Cache_Key_Member_Username = "Member.Username:";
	
	/**
	 * 用户鉴权documentCode缓存前缀
	 */
	public static final String Prefix_Cache_Key_Member_RegistrationId = "Member.RegistrationId:";
	
	/** 用户名 */
	private String username;
	
	/** token凭证码  */
	private String documentCode;

	/** 开始缓存的时间点 */
	private Date cachedSince;
	
	/** 推送设备Id */
	private String registrationId;
	
	/** 登录的设备名称 */
	private String deviceName;
	
	/** 办公室门禁app推送设备Id */
	private String accessRegistrationId;
	
	public MemberCachedDto(){
		super();
	}
	
	public MemberCachedDto(String username, String documentCode, Date cachedSince,String registrationId,String accessRegistrationId) {
		super();
		this.username = username;
		this.documentCode = documentCode;
		this.cachedSince = cachedSince;
		this.registrationId = registrationId;
		this.accessRegistrationId = accessRegistrationId;
	}

	public String getUsername() {
		return username;
	}

	public String getDocumentCode() {
		return documentCode;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setDocumentCode(String documentCode) {
		this.documentCode = documentCode;
	}

	public Date getCachedSince() {
		return cachedSince;
	}

	public void setCachedSince(Date cachedSince) {
		this.cachedSince = cachedSince;
	}

	public String getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getAccessRegistrationId() {
		return accessRegistrationId;
	}

	public void setAccessRegistrationId(String accessRegistrationId) {
		this.accessRegistrationId = accessRegistrationId;
	}
	
}
