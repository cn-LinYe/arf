package com.arf.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 用户推送消息记录
 * @author Caolibao
 *
 */
@Entity
@Table(name = "r_message_pushed",
		indexes={
			@Index(name="index_user_name",columnList="userName"),
			@Index(name="index_msg_id",columnList="msgId"),
			@Index(name="index_alias",columnList="alias"),
			@Index(name="index_registration_id",columnList="registrationId")
		}
)
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "message_pushed_sequence")
public class MessagePushed  extends BaseEntity<Long>{
	private static final long serialVersionUID = 7397599533756371518L;
	
	public static final String AUDIENCE_ALL = "AUDIENCE_ALL";
	
	private String userName;
	private String alias;//userName的MD5加密形式
	private String registrationId;//手机设备号
	private String msgId;
	private String sendno;
	
	
	private String title;
	private String contentType;
	private String msgContent;
	private String extras; //额外的业务字段,即自定义消息中的PushMessage JSON序列化后的字符串
	
	private PushType pushType;
	
	private Long timeToLive;//存活时间,单位:秒
	
	private MsgStatus msgStatus;//消息状态
	
	private UserType userType;//用户类型 0 推送给普通用户，1 推送给商户
	
	private String msgUUID;//消息唯一编号
	private String transactionId;//业务字段id,和contentType配合可以根据业务字段找到消息
	
	private AppType appType;//app类型 0或null 安心点app，1 办公室门禁app
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum UserType{
		USER,//用户
		MERCHANT,//商户
		OFFICEUSER,//办公室门禁用户
		;
	}
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum PushType{
		NOTIFICATION, //通知消息
		CUSTOM_MESSAGE, //自定义消息
		;
	}
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum MsgStatus{
		UNREAD, //未读
		READ, //已读
		DELETED,//已删除
		;
	}
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum AppType{
		AXD, //安心点app
		OFFICE, //办公室门禁app
		;
	}
	
	@Column(length = 20)
	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getMsgUUID() {
		return msgUUID;
	}

	public void setMsgUUID(String msgUUID) {
		this.msgUUID = msgUUID;
	}

	@Column(length = 30)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(length = 40)
	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
	@Column(length = 40)
	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	@Column(length = 40)
	public String getSendno() {
		return sendno;
	}

	public void setSendno(String sendno) {
		this.sendno = sendno;
	}
	@Column(length = 100)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	@Column(length = 30)
	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	@Column(length=3000)
	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	public PushType getPushType() {
		return pushType;
	}

	public void setPushType(PushType pushType) {
		this.pushType = pushType;
	}

	public Long getTimeToLive() {
		return timeToLive;
	}

	public void setTimeToLive(Long timeToLive) {
		this.timeToLive = timeToLive;
	}
	@Column(length=50)
	public String getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}

	public MsgStatus getMsgStatus() {
		return msgStatus;
	}

	public void setMsgStatus(MsgStatus msgStatus) {
		this.msgStatus = msgStatus;
	}
	@Column(length=5000)
	public String getExtras() {
		return extras;
	}

	public void setExtras(String extras) {
		this.extras = extras;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public AppType getAppType() {
		return appType;
	}

	public void setAppType(AppType appType) {
		this.appType = appType;
	}
	
}


