package com.arf.office.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "office_clock_record_new")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "office_clock_record_new_sequence")
public class OfficeClockRecordNew extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 317468764700945925L;
	
	private String userName;//用户名
	private String companyNumber;//公司编号
	private Date clockDate;//打卡日期
	private Long attendenceGroupId;//所属考勤组id
	private Integer workDuration;//工作时长（秒）
	private Status status;//状态（0.normal正常 1.late迟到、2.advance早退、3.lack缺卡、4.absenteeism旷工、5.overtime加班）
	private SignType signType;//签到类型（0 two 一天两次1four一天四次）
	private String workTime;//打卡时间
	private ClockModel clockModel;//打卡模式（0 Speed 极速打卡 1 Manual手动打卡）
	private ClockType clockType;//打卡类型（0 Wifi WiFi打卡 1 Gps 手机定位打卡 2 bluetooth 蓝牙门禁打卡）
	private Double lat;//纬度
	private Double lng;//经度
	private String wifiMac;//wifi Mac地址
	private String bluetoothMac;//蓝牙Mac地址
	private String equipmentIdentifier;//设备标识符
	private TimeType timeType;//打卡时间类型（0 amBegin 上午上班打卡 1 amEnd 上午下班打卡 2 pmBegin 下午上班打卡 3 pmEnd 下午下班打卡）
	private Long locationId;//考勤定位设置id
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum Status{
		location,wifi;
	}
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum SignType{
		location,wifi;
	}
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum ClockModel{
		location,wifi;
	}
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum ClockType{
		location,wifi;
	}
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum TimeType{
		location,wifi;
	}
	@Column(length = 20)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(length = 20)
	public String getCompanyNumber() {
		return companyNumber;
	}

	public void setCompanyNumber(String companyNumber) {
		this.companyNumber = companyNumber;
	}

	public Date getClockDate() {
		return clockDate;
	}

	public void setClockDate(Date clockDate) {
		this.clockDate = clockDate;
	}

	public Long getAttendenceGroupId() {
		return attendenceGroupId;
	}

	public void setAttendenceGroupId(Long attendenceGroupId) {
		this.attendenceGroupId = attendenceGroupId;
	}

	public Integer getWorkDuration() {
		return workDuration;
	}

	public void setWorkDuration(Integer workDuration) {
		this.workDuration = workDuration;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public SignType getSignType() {
		return signType;
	}

	public void setSignType(SignType signType) {
		this.signType = signType;
	}
	@Column(length = 10)
	public String getWorkTime() {
		return workTime;
	}

	public void setWorkTime(String workTime) {
		this.workTime = workTime;
	}

	public ClockModel getClockModel() {
		return clockModel;
	}

	public void setClockModel(ClockModel clockModel) {
		this.clockModel = clockModel;
	}

	public ClockType getClockType() {
		return clockType;
	}

	public void setClockType(ClockType clockType) {
		this.clockType = clockType;
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
	@Column(length = 20)
	public String getWifiMac() {
		return wifiMac;
	}

	public void setWifiMac(String wifiMac) {
		this.wifiMac = wifiMac;
	}
	@Column(length = 20)
	public String getBluetoothMac() {
		return bluetoothMac;
	}

	public void setBluetoothMac(String bluetoothMac) {
		this.bluetoothMac = bluetoothMac;
	}
	@Column(length = 50)
	public String getEquipmentIdentifier() {
		return equipmentIdentifier;
	}

	public void setEquipmentIdentifier(String equipmentIdentifier) {
		this.equipmentIdentifier = equipmentIdentifier;
	}

	public TimeType getTimeType() {
		return timeType;
	}

	public void setTimeType(TimeType timeType) {
		this.timeType = timeType;
	}

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

}
