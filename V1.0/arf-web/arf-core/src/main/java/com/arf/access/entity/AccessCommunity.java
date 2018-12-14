package com.arf.access.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "access_community")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "access_community_sequence")
public class AccessCommunity extends BaseEntity<Long>{
	private static final long serialVersionUID = 5744641462037811430L;
	
	private Integer status;//状态:0正常1关闭
	private String communityName;//小区名称
	private String communityNumber;//小区编号
	
	private String secretkey;//密钥
	private Date setSecretDate;//密钥设置/导入时间
	
	private String midCommunityNumber;//设备小区编号（新门禁）
	
	private Type type;//小区安装的门禁类型 0蓝牙门禁、1网络门禁、2可视化门禁
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	 public enum Status{
		 Normal,//0正常
		 Close//1关闭
		;
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	 public enum Type{
		 BlueTooth,//0蓝牙门禁
		 Wifi,//1网络门禁
		 Visualization,//2可视化门禁
		 BlueToothAndVisual,//蓝牙+可视门禁
		;
	}
	@Column(nullable = false)
	public Integer getStatus() {
		return status;
	}
	@Column(length=32)
	public String getCommunityName() {
		return communityName;
	}
	@Column(length=20)
	public String getCommunityNumber() {
		return communityNumber;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}

	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
	public String getSecretkey() {
		return secretkey;
	}
	public void setSecretkey(String secretkey) {
		this.secretkey = secretkey;
	}
	@JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss")
	@Column(length = 40)
	public Date getSetSecretDate() {
		return setSecretDate;
	}
	public void setSetSecretDate(Date setSecretDate) {
		this.setSecretDate = setSecretDate;
	}
	public String getMidCommunityNumber() {
		return midCommunityNumber;
	}
	public void setMidCommunityNumber(String midCommunityNumber) {
		this.midCommunityNumber = midCommunityNumber;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
}
