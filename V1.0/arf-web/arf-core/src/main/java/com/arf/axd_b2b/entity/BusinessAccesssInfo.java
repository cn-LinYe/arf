package com.arf.axd_b2b.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;


@Entity
@Table(name = "b2b_business_accesss_info")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "b2b_business_accesss_info_sequence")
public class BusinessAccesssInfo extends BaseEntity<Long> {
	private static final long serialVersionUID = 1L;
	private String businessNum;//商户编号,来自安心点商户编号
	private String webappEntrance;//普通	H5应用入口地址URL
	private Status status;//状态枚举
	private String whiteList;//支付结果通知白名单,多个使用英文逗号分隔
	private String appid;//安心点平台生成,全局唯一,一次生成后不可改变
	private String appsecret;//安心点平台生成,全局唯一,一次生成后可改变
	
	private Double lat;//维度
	private Double lng;//经度
	
	public enum Status{
		OFFLINE,//下线
		ONLINE,//上线
		;
	}

	public String getBusinessNum() {
		return businessNum;
	}

	public void setBusinessNum(String businessNum) {
		this.businessNum = businessNum;
	}

	public String getWebappEntrance() {
		return webappEntrance;
	}

	public void setWebappEntrance(String webappEntrance) {
		this.webappEntrance = webappEntrance;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getWhiteList() {
		return whiteList;
	}

	public void setWhiteList(String whiteList) {
		this.whiteList = whiteList;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getAppsecret() {
		return appsecret;
	}

	public void setAppsecret(String appsecret) {
		this.appsecret = appsecret;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}
	
	
}
