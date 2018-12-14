package com.arf.royalstar.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.search.annotations.DateBridge;
import org.hibernate.search.annotations.Resolution;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "rs_carwashing_device")
@SequenceGenerator(name = "sequenceGenerator",sequenceName = "carwashing_device_sequence")
public class RSCarwashingDevice extends BaseEntity<Long> {

	private static final long serialVersionUID = 1L;
	
	private String deviceCode; //varchar(20) 设备编号,唯一约束
	private Integer deviceStatus;// tinyint 设备状态，1：在线，2：离线
	private String address;//varchar(255) 设备安放地址
	private String geo;//varchar(100) 经纬度(lat,lng)
	private Date lastStatusUpdate;//datetime 上一次设备状态更新时间
	private BigDecimal coinCountOnce;//洗一次多少钱
	private Integer businessNum;//商户编码
	
	public enum Status{
		nouse,online,offline;
		public static Status get(int ordinal){
			Status status[] = Status.values();
			if(ordinal < 0 || ordinal > status.length-1){
				return null;
			}else{
				return status[ordinal];
			}
		}
	}
	
	
	
	public Integer getBusinessNum() {
		return businessNum;
	}
	public void setBusinessNum(Integer businessNum) {
		this.businessNum = businessNum;
	}
	@Column(length=20)
	public String getDeviceCode(){
		return deviceCode;
	}
	public Integer getDeviceStatus(){
		return deviceStatus;
	}
	@Column(length=255)
	public String getAddress(){
		return address;
	}
	@Column(length=100)
	public String getGeo(){
		return geo;
	}
	@DateBridge(resolution = Resolution.SECOND)
	public Date getLastStatusUpdate(){
		return lastStatusUpdate;
	}
	@Column(precision = 10, scale = 2)
	public BigDecimal getCoinCountOnce() {
		return coinCountOnce;
	}
	public void setCoinCountOnce(BigDecimal coinCountOnce) {
		this.coinCountOnce = coinCountOnce;
	}
	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}
	public void setDeviceStatus(Integer deviceStatus) {
		this.deviceStatus = deviceStatus;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setGeo(String geo) {
		this.geo = geo;
	}
	public void setLastStatusUpdate(Date lastStatusUpdate) {
		this.lastStatusUpdate = lastStatusUpdate;
	}
} 
