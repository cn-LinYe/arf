package com.arf.smart.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "r_smart_voice_record")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "r_smart_voice_record_sequence")
public class SmartVoiceRecord extends BaseEntity<Long> {

	private static final long serialVersionUID = -7635376679491037908L;
	
	private DeviceType deviceType;//设备类型
	private String identifyRef;//设备唯一标识,对应车辆的车牌,门禁系统的授权记录id,智能门锁系统的用户-门锁关联id
	private Integer duration;//语音时长,单位:秒
	private Type type;//开-关类型(仅针对车辆有效)
	private String resourceUrl;//语音文件资源FTP相对路径
	private Status status;//状态
	private String text;//语音文本
	private String userName;//所属用户名
	private String voiceName;//文件名称
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum DeviceType{
		USER_CAR, //0 用户车辆,
		DEVOLUTE_CAR, //1 授权车辆
		ACCESS_USER, //2 用户门禁,
		ACCESS_AUTHORIZATION, //3用户门禁授权,
		SMART_LOCK; //4 智能门锁,
	}
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum Type{
		OPEN, //0 开启
		CLOSE; //1 关闭
	}
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum Status{
		NORMAL, //0 正常
		DELETED;  //1 删除
	}

	@Column(length=30)
	public String getVoiceName() {
		return voiceName;
	}

	public void setVoiceName(String voiceName) {
		this.voiceName = voiceName;
	}

	public DeviceType getDeviceType() {
		return deviceType;
	}

	public String getIdentifyRef() {
		return identifyRef;
	}

	public Integer getDuration() {
		return duration;
	}

	public Type getType() {
		return type;
	}

	public String getResourceUrl() {
		return resourceUrl;
	}

	public Status getStatus() {
		return status;
	}

	public String getText() {
		return text;
	}

	public String getUserName() {
		return userName;
	}

	public void setDeviceType(DeviceType deviceType) {
		this.deviceType = deviceType;
	}

	public void setIdentifyRef(String identifyRef) {
		this.identifyRef = identifyRef;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public void setResourceUrl(String resourceUrl) {
		this.resourceUrl = resourceUrl;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
