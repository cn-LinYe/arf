package com.arf.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "r_parkingrecord")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "r_parkingrecord_sequence")
public class RParkingrecord extends OrderEntity<Long> {

	private static final long serialVersionUID = 1L;

	private Integer parkingid;

	private String parkingLotNum;

	private Integer mepId;

	private String mepPersonnelNumber;

	private String mepName;

	private String checkinTime;

	private String startTimeNumber;

	private Integer startTimeID;

	private String endTimeNumber;

	private Integer endTimeID;

	private Integer parkingDuration;

	private Integer codeID;

	private Integer positionID;

	private String positionNumber;

	private String license;

	private Integer matchTime;

	private Integer chargeParkDuration;

	private Integer chargeMoney;

	private Integer parkingType;

	@Column(name = "parkingid")
	public Integer getParkingid() {
		return parkingid;
	}

	public void setParkingid(Integer parkingid) {
		this.parkingid = parkingid;
	}

	@Column(name = "parkingLotNum", length = 10)
	public String getParkingLotNum() {
		return parkingLotNum;
	}

	public void setParkingLotNum(String parkingLotNum) {
		this.parkingLotNum = parkingLotNum;
	}

	@Column(name = "mepId")
	public Integer getMepId() {
		return mepId;
	}

	public void setMepId(Integer mepId) {
		this.mepId = mepId;
	}

	@Column(name = "mepPersonnelNumber", length = 20)
	public String getMepPersonnelNumber() {
		return mepPersonnelNumber;
	}

	public void setMepPersonnelNumber(String mepPersonnelNumber) {
		this.mepPersonnelNumber = mepPersonnelNumber;
	}

	@Column(name = "mepName", length = 20)
	public String getMepName() {
		return mepName;
	}

	public void setMepName(String mepName) {
		this.mepName = mepName;
	}

	@Column(name = "checkinTime", length = 20)
	public String getCheckinTime() {
		return checkinTime;
	}

	public void setCheckinTime(String checkinTime) {
		this.checkinTime = checkinTime;
	}

	@Column(name = "startTimeNumber", length = 20)
	public String getStartTimeNumber() {
		return startTimeNumber;
	}

	public void setStartTimeNumber(String startTimeNumber) {
		this.startTimeNumber = startTimeNumber;
	}

	@Column(name = "startTimeID")
	public Integer getStartTimeID() {
		return startTimeID;
	}

	public void setStartTimeID(Integer startTimeID) {
		this.startTimeID = startTimeID;
	}

	@Column(name = "endTimeNumber", length = 20)
	public String getEndTimeNumber() {
		return endTimeNumber;
	}

	public void setEndTimeNumber(String endTimeNumber) {
		this.endTimeNumber = endTimeNumber;
	}

	@Column(name = "endTimeID")
	public Integer getEndTimeID() {
		return endTimeID;
	}

	public void setEndTimeID(Integer endTimeID) {
		this.endTimeID = endTimeID;
	}

	@Column(name = "parkingDuration")
	public Integer getParkingDuration() {
		return parkingDuration;
	}

	public void setParkingDuration(Integer parkingDuration) {
		this.parkingDuration = parkingDuration;
	}

	@Column(name = "codeID")
	public Integer getCodeID() {
		return codeID;
	}

	public void setCodeID(Integer codeID) {
		this.codeID = codeID;
	}

	@Column(name = "positionID")
	public Integer getPositionID() {
		return positionID;
	}

	public void setPositionID(Integer positionID) {
		this.positionID = positionID;
	}

	@Column(name = "positionNumber")
	public String getPositionNumber() {
		return positionNumber;
	}

	public void setPositionNumber(String positionNumber) {
		this.positionNumber = positionNumber;
	}

	@Column(name = "license", length = 10)
	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	@Column(name = "matchTime")
	public Integer getMatchTime() {
		return matchTime;
	}

	public void setMatchTime(Integer matchTime) {
		this.matchTime = matchTime;
	}

	@Column(name = "chargeParkDuration")
	public Integer getChargeParkDuration() {
		return chargeParkDuration;
	}

	public void setChargeParkDuration(Integer chargeParkDuration) {
		this.chargeParkDuration = chargeParkDuration;
	}

	@Column(name = "chargeMoney")
	public Integer getChargeMoney() {
		return chargeMoney;
	}

	public void setChargeMoney(Integer chargeMoney) {
		this.chargeMoney = chargeMoney;
	}

	@Column(name = "parkingType")
	public Integer getParkingType() {
		return parkingType;
	}

	public void setParkingType(Integer parkingType) {
		this.parkingType = parkingType;
	}

}
