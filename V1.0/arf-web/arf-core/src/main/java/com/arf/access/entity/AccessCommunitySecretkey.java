package com.arf.access.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Deprecated
@Entity
@Table(name = "access_community_secretkey")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "access_community_secretkey_sequence")
public class AccessCommunitySecretkey extends BaseEntity<Long> {
	
	private static final long serialVersionUID = 8358138609482353660L;
	
	private String communityNumber;//小区编号
	private String secretkey;//密钥
	//private Date lastSyncDate;//上次同步时间
	//private String lastSyncUser;//上次同步操作用户
	/**
		状态枚举:
		NOT_SYNC 0 未同步
		SUCCESS 1 成功
		FAILED 2 失败
	 */
	//private Status status;
	private Date setDate;//密钥设置/导入时间
	
//	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
//	public enum Status{
//		NOT_SYNC,
//		SUCCESS,
//		FAILED;
//	}

	@Column(length=20,nullable=false)
	public String getCommunityNumber() {
		return communityNumber;
	}

	@Column(length=512)
	public String getSecretkey() {
		return secretkey;
	}

//	public Date getLastSyncDate() {
//		return lastSyncDate;
//	}

//	@Column(length=20)
//	public String getLastSyncUser() {
//		return lastSyncUser;
//	}
//
//	public Status getStatus() {
//		return status;
//	}

	public Date getSetDate() {
		return setDate;
	}

	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}

	public void setSecretkey(String secretkey) {
		this.secretkey = secretkey;
	}

//	public void setLastSyncDate(Date lastSyncDate) {
//		this.lastSyncDate = lastSyncDate;
//	}

//	public void setLastSyncUser(String lastSyncUser) {
//		this.lastSyncUser = lastSyncUser;
//	}
//
//	public void setStatus(Status status) {
//		this.status = status;
//	}

	public void setSetDate(Date setDate) {
		this.setDate = setDate;
	}

}
