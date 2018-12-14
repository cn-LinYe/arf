package com.arf.access.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "access_temp_passwd")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "access_temp_passwd_sequence")
public class AccessTempPasswd extends BaseEntity<Long>{

	private static final long serialVersionUID = -4810027978052333471L;
	
	private String bluetoothMac	;//	设备蓝牙
	private String passwd	;//		密码
	private Status status	;//		NOT_USED 0 未使用;1USED 已使用
	private Date usedDate	;//	   	使用时间
	private String usedBy	;//		使用人
	private String communityNumber	;//	小区编号
	private Integer building	;//		楼栋编号
	
	public enum Status{
			NOT_USED ,USED;
	}
	@Column(length=20)
	public String getBluetoothMac() {
		return bluetoothMac;
	}

	public void setBluetoothMac(String bluetoothMac) {
		this.bluetoothMac = bluetoothMac;
	}
	@Column(length=20)
	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Date getUsedDate() {
		return usedDate;
	}

	public void setUsedDate(Date usedDate) {
		this.usedDate = usedDate;
	}
	@Column(length=20)
	public String getUsedBy() {
		return usedBy;
	}

	public void setUsedBy(String usedBy) {
		this.usedBy = usedBy;
	}
	@Column(length=32)
	public String getCommunityNumber() {
		return communityNumber;
	}

	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}

	public Integer getBuilding() {
		return building;
	}

	public void setBuilding(Integer building) {
		this.building = building;
	}

}
