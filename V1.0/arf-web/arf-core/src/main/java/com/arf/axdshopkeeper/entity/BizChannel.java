package com.arf.axdshopkeeper.entity;

import java.io.Serializable;
import java.util.Date;

public class BizChannel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private Date createDate;
	private Date modifyDate;
	private int version;
	private String bizChannelName;//渠道名称
	private String bizChannelNumber;//渠道编号
	private String principalName;//负责人姓名
	private String principalMobile;//负责人手机号
	
	public int getId() {
		return id;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public int getVersion() {
		return version;
	}
	public String getBizChannelName() {
		return bizChannelName;
	}
	public String getBizChannelNumber() {
		return bizChannelNumber;
	}
	public String getPrincipalName() {
		return principalName;
	}
	public String getPrincipalMobile() {
		return principalMobile;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public void setBizChannelName(String bizChannelName) {
		this.bizChannelName = bizChannelName;
	}
	public void setBizChannelNumber(String bizChannelNumber) {
		this.bizChannelNumber = bizChannelNumber;
	}
	public void setPrincipalName(String principalName) {
		this.principalName = principalName;
	}
	public void setPrincipalMobile(String principalMobile) {
		this.principalMobile = principalMobile;
	}

}
