package com.arf.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

/**
 * 用户意见反馈
 */
@Entity(name="user_advice_model")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "user_advice_model_sequence")
public class UserAdviceModel extends BaseEntity<Long> {

	/**
     * 
     */
    private static final long serialVersionUID = 5421830082515027363L;
    
    /**
     * 用户意见
     */
    private String user_advice; 
    /**
     * 用户类型：
     * 1:安心点会员 
     * 2:商户 
     * 9:安心点社区店长招募用户
     */
    private Byte user_type;
   
	/**
	 * 用户名（用户表外键）
	 */
	private String user_name;
	/**
	 * 状态：0:未处理；1已处理,
	 * 2正在处理中
	 */
	private Byte status;
	/**
	 * 用户手机号
	 */
	private String phone;
	/**
	 * 反馈时间
	 */
	private long advice_time;
	
	/**
	 * 小区编码
	 */
	private String communityNumber;
	
	/**
	 * 小区名称
	 */
	private String communityName;
	/**
	 * 备注
	 */
	private String informationMessage;
	 /**
	  * 车牌号
	  */
	private String licensePlateNumber;
	
	/**
	 * 意见反馈类型
	 */
	private Long typeId;
	/**
	 * 发送消息内容
	 */
	private String messageContent;
	
	
	
	public Long getTypeId() {
		return typeId;
	}
	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}
	
	@Column(name = "messageContent", length = 2048)
	public String getMessageContent() {
		return messageContent;
	}
	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
	
	
	@Column(name = "communityNumber", length = 2000)
	public String getCommunityNumber() {
		return communityNumber;
	}
	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
	@Column(name = "communityName", length = 2000)
	public String getCommunityName() {
		return communityName;
	}
	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}
	@Column(name = "informationMessage", length = 255)
	public String getInformationMessage() {
		return informationMessage;
	}
	public void setInformationMessage(String informationMessage) {
		this.informationMessage = informationMessage;
	}
	@Column(name = "licensePlateNumber", length = 2048)
	public String getLicensePlateNumber() {
		return licensePlateNumber;
	}
	public void setLicensePlateNumber(String licensePlateNumber) {
		this.licensePlateNumber = licensePlateNumber;
	}
	@Column(name = "user_name", length = 64, nullable = false)
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	@Column(name = "user_advice", length = 2000, nullable = false)
	public String getUser_advice() {
		return user_advice;
	}
	public void setUser_advice(String user_advice) {
		this.user_advice = user_advice;
	}

	@Column(name = "advice_time", length = 32, nullable = false)
	public long getAdvice_time() {
		return advice_time;
	}
	public void setAdvice_time(long advice_time) {
		this.advice_time = advice_time;
	}
	@Column(name = "user_type", length = 4, nullable = false)
	public Byte getUser_type() {
		return user_type;
	}
	@Column(name = "status", length = 4, nullable = false)
	public Byte getStatus() {
		return status;
	}
	@Column(name = "phone", length = 32)
	public String getPhone() {
		return phone;
	}
	public void setUser_type(Byte user_type) {
		this.user_type = user_type;
	}
	public void setStatus(Byte status) {
		this.status = status;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public UserAdviceModel(String user_name, String user_advice, long advice_time) {
		super();
		this.user_name = user_name;
		this.user_advice = user_advice;
		this.advice_time = advice_time;
	}
	public UserAdviceModel() {
		super();
	}
}
