package com.arf.carbright.dto;

import java.io.Serializable;
import java.util.Date;

public class BusinessCachedDto implements Serializable{
	
	private static final long serialVersionUID = -2587338060580155623L;
	
	/**
	 * 新 商户鉴权缓存前缀
	 */
	public static final String Prefix_Cache_Key_Business_UserName = "PBusiness.bisinessUserName:";
	public static final String Prefix_Cache_Key_Business_RegistrationId = "PBusiness.bisinessRegistrationId:";

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
	
	public BusinessCachedDto(){
		super();
	}
	
	public BusinessCachedDto(String username, String documentCode, Date cachedSince,String registrationId) {
		super();
		this.username = username;
		this.documentCode = documentCode;
		this.cachedSince = cachedSince;
		this.registrationId = registrationId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDocumentCode() {
		return documentCode;
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

	
}
