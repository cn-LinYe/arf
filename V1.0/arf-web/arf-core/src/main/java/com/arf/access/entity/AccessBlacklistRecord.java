package com.arf.access.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "access_blacklist_record",
		uniqueConstraints={
		@UniqueConstraint(columnNames={"guestIdentifyId","guestType","userName"},
				name="guestIdentifyId_guestType_userName"),})
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "access_blacklist_record_sequence")
public class AccessBlacklistRecord extends BaseEntity<Long> {

	private static final long serialVersionUID = 9193647951841889770L;
	
	private String guestIdentifyId;//访客识别id(微信open_id)
	private AccessGuestRecord.GuestType guestType;//访客类型
	private String userName;//户主用户名
	private String nickname;//(微信)昵称
	private String avatarUrl;//(微信)头像
	/**
		SHIELDING 0 拉黑中
		CANCEL 1 已取消
	 */
	private Status status;//状态
	private Date shieldsDate;//拉黑/取消拉黑时间
	private Long guestRecordId;//被拉黑的关联访问申请记录
	
	private String oprateUsername;//操作人(户主、设置应答用户)

	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum Status{
		SHIELDING,
		CANCEL;
	}

	@Column(length=50)
	public String getGuestIdentifyId() {
		return guestIdentifyId;
	}

	public AccessGuestRecord.GuestType getGuestType() {
		return guestType;
	}

	@Column(length=30)
	public String getUserName() {
		return userName;
	}

	@Column(length=30)
	public String getNickname() {
		return nickname;
	}

	@Column(length=200)
	public String getAvatarUrl() {
		return avatarUrl;
	}

	public Status getStatus() {
		return status;
	}

	@Column(nullable = false)
	public Date getShieldsDate() {
		return shieldsDate;
	}

	public Long getGuestRecordId() {
		return guestRecordId;
	}

	public void setGuestIdentifyId(String guestIdentifyId) {
		this.guestIdentifyId = guestIdentifyId;
	}

	public void setGuestType(AccessGuestRecord.GuestType guestType) {
		this.guestType = guestType;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void setShieldsDate(Date shieldsDate) {
		this.shieldsDate = shieldsDate;
	}

	public void setGuestRecordId(Long guestRecordId) {
		this.guestRecordId = guestRecordId;
	}

	@Column(length=30)
	public String getOprateUsername() {
		return oprateUsername;
	}

	public void setOprateUsername(String oprateUsername) {
		this.oprateUsername = oprateUsername;
	}
	
}
