package com.arf.carbright.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "r_push_message_service_record")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "r_push_message_service_record_sequence")
public class RPushMessageServiceRecord extends BaseEntity<Long>{
	/**
	 * 客服信息推送记录表
	 */
	private static final long serialVersionUID = -2096610331173157552L;	
	private String userName;//用户名(登录名)
	private Date  feedbackDate;//反馈时间
	private String fullName;//用户姓名
	private String phone;//联系电话
	private Integer messageType;//消息类型
	private Date pushDate ;//推送时间
	private String pushServiceFullName;//推送客服名称
	private String pushServicePhone;//推送客服联系方式
	private String pushMessage;//推送内容
	private Integer pushSmsStatus;//短信推送(0未推送1成功2失败)
	private Integer pushWechatStatus;//微信推送(0未推送1成功2失败)
	private Integer pushNailsStatus;//钉钉推送(0未推送1成功2失败)
	
	@Column(length = 40)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getFeedbackDate() {
		return feedbackDate;
	}
	public void setFeedbackDate(Date feedbackDate) {
		this.feedbackDate = feedbackDate;
	}
	@Column(length = 40)
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	@Column(length = 40)
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getMessageType() {
		return messageType;
	}
	public void setMessageType(Integer messageType) {
		this.messageType = messageType;
	}
	public Date getPushDate() {
		return pushDate;
	}
	public void setPushDate(Date pushDate) {
		this.pushDate = pushDate;
	}
	@Column(length = 40)
	public String getPushServiceFullName() {
		return pushServiceFullName;
	}
	public void setPushServiceFullName(String pushServiceFullName) {
		this.pushServiceFullName = pushServiceFullName;
	}
	@Column(length = 40)
	public String getPushServicePhone() {
		return pushServicePhone;
	}
	public void setPushServicePhone(String pushServicePhone) {
		this.pushServicePhone = pushServicePhone;
	}
	@Column(length = 1000)
	public String getPushMessage() {
		return pushMessage;
	}
	public void setPushMessage(String pushMessage) {
		this.pushMessage = pushMessage;
	}
	public Integer getPushSmsStatus() {
		return pushSmsStatus;
	}
	public void setPushSmsStatus(Integer pushSmsStatus) {
		this.pushSmsStatus = pushSmsStatus;
	}
	public Integer getPushWechatStatus() {
		return pushWechatStatus;
	}
	public void setPushWechatStatus(Integer pushWechatStatus) {
		this.pushWechatStatus = pushWechatStatus;
	}
	public Integer getPushNailsStatus() {
		return pushNailsStatus;
	}
	public void setPushNailsStatus(Integer pushNailsStatus) {
		this.pushNailsStatus = pushNailsStatus;
	}	
	
}
