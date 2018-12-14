package com.arf.access.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "access_device_status_info")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "access_device_status_info_sequence")
public class AccessDeviceStatusInfo extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4325909140265670963L;
	
	private String communityNumber;//小区编号
	private String accessNum;//门禁编号
	private String accessMac;//门禁MAC地址
	private Status status;//门禁状态（0 、normal正常 1、exception异常）
	private String exception;//异常数据json数组字符串,内部对象数据结构:{"exception ":"具体异常信息","retryConut":"重试次数","retryDate":"重试时间"}
	private String visualAccessVersion;//可视门禁版本信息
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum Status{
		normal,exception;
	}

	@Column(length=20)
	public String getCommunityNumber() {
		return communityNumber;
	}

	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}

	@Column(length=20)
	public String getAccessNum() {
		return accessNum;
	}

	public void setAccessNum(String accessNum) {
		this.accessNum = accessNum;
	}

	@Column(length=20)
	public String getAccessMac() {
		return accessMac;
	}

	public void setAccessMac(String accessMac) {
		this.accessMac = accessMac;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Column(length=300)
	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	@Column(length=10)
	public String getVisualAccessVersion() {
		return visualAccessVersion;
	}

	public void setVisualAccessVersion(String visualAccessVersion) {
		this.visualAccessVersion = visualAccessVersion;
	}

}
