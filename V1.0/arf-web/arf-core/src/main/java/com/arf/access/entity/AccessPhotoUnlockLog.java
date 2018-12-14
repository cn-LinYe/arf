package com.arf.access.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "access_photo_unlock_log")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "access_photo_unlock_log_sequence")
public class AccessPhotoUnlockLog extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6710525139962059115L;
	
	private String communityNumber;//小区编号
	private String bluetoothMac;//蓝牙Mac
	private Type type;//开门类型（0 、APP开门记录1 、密码开门记录/临时密码2 、门禁卡开门记录3、呼叫留影 4防拆报警 5防震报警）
	private String chipNum;//门禁卡芯片编号
	private String passwd;//开门密码
	private String userName;//用户名
	private Date openTime;//开门时间
	private String imageUrl;//留影照片
	private String temPasswd;//临时密码
	private String faceInfoJson;//人脸识别JSON
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum Type{
		APP,PWD,CARD,Call,TamperAlarm,ShockproofAlarm;
	}

	@Column(length=20)
	public String getCommunityNumber() {
		return communityNumber;
	}

	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}

	@Column(length=20)
	public String getBluetoothMac() {
		return bluetoothMac;
	}

	public void setBluetoothMac(String bluetoothMac) {
		this.bluetoothMac = bluetoothMac;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	@Column(length=10)
	public String getChipNum() {
		return chipNum;
	}

	public void setChipNum(String chipNum) {
		this.chipNum = chipNum;
	}

	@Column(length=10)
	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	@Column(length=20)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getOpenTime() {
		return openTime;
	}

	public void setOpenTime(Date openTime) {
		this.openTime = openTime;
	}

	@Column(length=100)
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@Column(length=10)
	public String getTemPasswd() {
		return temPasswd;
	}

	public void setTemPasswd(String temPasswd) {
		this.temPasswd = temPasswd;
	}

	@Column(length=5000)
	public String getFaceInfoJson() {
		return faceInfoJson;
	}

	public void setFaceInfoJson(String faceInfoJson) {
		this.faceInfoJson = faceInfoJson;
	}

}
