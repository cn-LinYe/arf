package com.arf.carbright.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "r_engineering_box_synch_info")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "r_engineering_box_synch_info_sequence")
public class REngineeringBoxSynchInfo extends BaseEntity<Long>{

	/**
	 * 工程机蓝牙箱子同步信息表
	 */
	private static final long serialVersionUID = -665327959002615221L;
	private String fullName;//用户姓名
	private String userName;//登录用户名
	private String cabinetNum;//柜子编号
	private String boxNum;//箱子编号
	private String communityNumber;//小区编号(管理小区编号)
	private String phoneImei;//手机imei地址
	private String cabinetAddress;//柜子地址
	private Date synchDate;//同步时间
	private Integer synchCount;//同步次数
	private Integer machineSynchCount;//设备同步次数
	private Integer status;//状态（0成功 1失败 2故障）
	
	public enum Status{
		success,failure,fault;
	}
	
	@Column(length = 40)
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	@Column(length = 40)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(length = 40)
	public String getCabinetNum() {
		return cabinetNum;
	}
	public void setCabinetNum(String cabinetNum) {
		this.cabinetNum = cabinetNum;
	}
	@Column(length = 400)
	public String getBoxNum() {
		return boxNum;
	}
	public void setBoxNum(String boxNum) {
		this.boxNum = boxNum;
	}
	@Column(length = 40)
	public String getCommunityNumber() {
		return communityNumber;
	}
	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
	@Column(length = 40)
	public String getPhoneImei() {
		return phoneImei;
	}
	public void setPhoneImei(String phoneImei) {
		this.phoneImei = phoneImei;
	}
	@Column(length = 100)
	public String getCabinetAddress() {
		return cabinetAddress;
	}
	public void setCabinetAddress(String cabinetAddress) {
		this.cabinetAddress = cabinetAddress;
	}
	public Date getSynchDate() {
		return synchDate;
	}
	public void setSynchDate(Date synchDate) {
		this.synchDate = synchDate;
	}
	public Integer getSynchCount() {
		return synchCount;
	}
	public void setSynchCount(Integer synchCount) {
		this.synchCount = synchCount;
	}
	public Integer getMachineSynchCount() {
		return machineSynchCount;
	}
	public void setMachineSynchCount(Integer machineSynchCount) {
		this.machineSynchCount = machineSynchCount;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
