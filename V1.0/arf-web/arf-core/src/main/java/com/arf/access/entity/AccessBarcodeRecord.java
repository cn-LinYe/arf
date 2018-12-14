package com.arf.access.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "access_barcode_record")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "access_barcode_record_sequence")
public class AccessBarcodeRecord extends BaseEntity<Long> {

	private static final long serialVersionUID = -7232792372756080239L;
	public static final String BARCODE_EXPIRATION_HOURS = "BARCODE_EXPIRATION_HOURS";
	
	public static final Integer TEMP_PASSWORD_EXPIRATION_HOURS = 1;//临时密码过期时间

	private String userName;//发送人用户名
	private Date sendDate;//发送时间
	/**
		访客类型枚举
		WECHAT 0 微信端用户
		QICQ 1 QQ端用户
	 */
	private SendType sendType;
	private String accessBarcode;//开门条码
	/**
		类型枚举:
		GUEST_APPLY 0 访客主动申请
		HOUSE_HOLDER_SEND 1 户主主动外发
	 */
	private Type type;
	private Long guestRecordId;//来访记录表id, type=0时存在该字段
	private int expirationHours;//有效时间(单位:小时)
	private Long accessUserId;//门禁用户表/门禁授权表关联id
	
	private Long tempPasswdId;//临时密码记录关联ID
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum SendType{
		WECHAT,
		QICQ;
	}
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum Type{
		GUEST_APPLY,
		HOUSE_HOLDER_SEND,
		OTHER_TENEMENT_SEND,
		;
	}

	@Column(length=30)
	public String getUserName() {
		return userName;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public SendType getSendType() {
		return sendType;
	}

	@Column(length=30)
	public String getAccessBarcode() {
		return accessBarcode;
	}

	public Type getType() {
		return type;
	}

	public Long getGuestRecordId() {
		return guestRecordId;
	}

	public int getExpirationHours() {
		return expirationHours;
	}

	public Long getAccessUserId() {
		return accessUserId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public void setSendType(SendType sendType) {
		this.sendType = sendType;
	}

	public void setAccessBarcode(String accessBarcode) {
		this.accessBarcode = accessBarcode;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public void setGuestRecordId(Long guestRecordId) {
		this.guestRecordId = guestRecordId;
	}

	public void setExpirationHours(int expirationHours) {
		this.expirationHours = expirationHours;
	}

	public void setAccessUserId(Long accessUserId) {
		this.accessUserId = accessUserId;
	}

	public Long getTempPasswdId() {
		return tempPasswdId;
	}

	public void setTempPasswdId(Long tempPasswdId) {
		this.tempPasswdId = tempPasswdId;
	}
}
