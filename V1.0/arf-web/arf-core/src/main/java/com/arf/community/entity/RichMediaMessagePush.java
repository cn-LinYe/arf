package com.arf.community.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="msg_rich_media_message_push")
@SequenceGenerator(name="sequenceGenerator",sequenceName="msg_rich_media_message_push_sequence")
public class RichMediaMessagePush extends BaseEntity<Long> {

	private static final long serialVersionUID = 3591694697873267213L;

	private String creator;//创建人
	private Long messageId;//外键msg_rich_media_message.id
	/**
	 * 目标类型枚举:
		小区 COMMUNITY
		物业公司 PROPERTY_COMP
		子公司BRANCH_COMP
		地区城市CITY_DISTRICT

	 */
	private TargetType targetType;
	private String uniqueIdentifier;//小区编号/物业编号/子公司编号/地区城市代码
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum TargetType{
		COMMUNITY,//小区 
		PROPERTY_COMP,//物业公司 
		BRANCH_COMP,//子公司
		CITY_DISTRICT,//地区城市
		ALL_SAW;//所有人可见
	}

	@Column(length=30,nullable=false)
	public String getCreator() {
		return creator;
	}
	@Column(nullable=false)
	public Long getMessageId() {
		return messageId;
	}
	@Column(nullable=false)
	public TargetType getTargetType() {
		return targetType;
	}
	@Column(length=30,nullable=false)
	public String getUniqueIdentifier() {
		return uniqueIdentifier;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	public void setTargetType(TargetType targetType) {
		this.targetType = targetType;
	}

	public void setUniqueIdentifier(String uniqueIdentifier) {
		this.uniqueIdentifier = uniqueIdentifier;
	}

}
