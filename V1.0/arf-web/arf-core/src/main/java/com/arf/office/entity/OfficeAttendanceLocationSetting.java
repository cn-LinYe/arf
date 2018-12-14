package com.arf.office.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "office_attendance_location_setting")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "office_attendance_location_setting_sequence")
public class OfficeAttendanceLocationSetting extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5953017322747131109L;
	
	private String name;//考勤设置名称
	private Type type;//考勤设置类型（0 location 手机定位打卡 1 wifi wifi打卡）
	private String companyNumber;///公司编号
	private String address;//地址
	private Double lng;//经度
	private Double lat;//纬度
	private Integer locationRange;//考勤定位范围
	private String wifiMac;//wifi Mac地址
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum Type{
		location,wifi;
	}
	@Column(length = 40)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
	@Column(length = 20)
	public String getCompanyNumber() {
		return companyNumber;
	}

	public void setCompanyNumber(String companyNumber) {
		this.companyNumber = companyNumber;
	}
	@Column(length = 100)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Integer getLocationRange() {
		return locationRange;
	}

	public void setLocationRange(Integer locationRange) {
		this.locationRange = locationRange;
	}

	@Column(length = 20)
	public String getWifiMac() {
		return wifiMac;
	}

	public void setWifiMac(String wifiMac) {
		this.wifiMac = wifiMac;
	}

}
