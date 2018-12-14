package com.arf.silverleopard.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "p_store")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "p_store_sequence")
public class Store extends BaseEntity<Long>{

	private static final long serialVersionUID = -1474059575784244477L;

	private String account;		//门店账号(银豹平台)
	private String storeName;		//门店名称
	private String industry;		//门店经营行业
	private String appId;		//门店凭证
	private String appKey;		//门店key 生成签证
	private String urlPreFix;	//接口地址前缀
	private String userArea;	//门店所在区域
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getAppKey() {
		return appKey;
	}
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	public String getUrlPreFix() {
		return urlPreFix;
	}
	public void setUrlPreFix(String urlPreFix) {
		this.urlPreFix = urlPreFix;
	}
	public String getUserArea() {
		return userArea;
	}
	public void setUserArea(String userArea) {
		this.userArea = userArea;
	}
}
