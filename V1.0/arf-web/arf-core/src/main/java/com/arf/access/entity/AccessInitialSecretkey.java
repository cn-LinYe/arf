package com.arf.access.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "access_initial_secretkey")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "access_initial_secretkey_sequence")
public class AccessInitialSecretkey extends BaseEntity<Long> {

	private static final long serialVersionUID = 8226645547139252133L;
	
	private String bluetoothMac;//蓝牙mac
	private String communityNumber;//小区编号
	private String building;//楼栋编号（3位）
	private Integer region;//区（0-6）
	private String passwd;//密码用,隔开9组密码
	
	
	@Column(length=32)
	public String getBluetoothMac() {
		return bluetoothMac;
	}
	@Column(length=20)
	public String getCommunityNumber() {
		return communityNumber;
	}
	@Column(length=20)
	public String getBuilding() {
		return building;
	}
	public void setBuilding(String building) {
		this.building = building;
	}
	public Integer getRegion() {
		return region;
	}
	@Column(length=80)
	public String getPasswd() {
		return passwd;
	}
	public void setBluetoothMac(String bluetoothMac) {
		this.bluetoothMac = bluetoothMac;
	}
	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
	public void setRegion(Integer region) {
		this.region = region;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	
}
