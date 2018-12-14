package com.arf.axdshopkeeper.entity;

import java.io.Serializable;
import java.util.Date;

public class BizChannelRefBranch implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int id;
	private Date createDate;
	private Date modifyDate;
	private int version;
	private String branchId;//联营公司编号
	private String bizChannelNumber;//渠道编号
	
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
	public String getBranchId() {
		return branchId;
	}
	public String getBizChannelNumber() {
		return bizChannelNumber;
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
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	public void setBizChannelNumber(String bizChannelNumber) {
		this.bizChannelNumber = bizChannelNumber;
	}

}
