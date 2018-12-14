package com.arf.eparking.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "p_blackcar_info")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "p_blackcar_info_sequence")
public class PBlackcarInfo extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 812181281867280276L;
	
	private String communityNumber;//小区编号(车场编号 )
	private String licenseNumber;//车牌 
	private Integer carType;//车类型 (1 小型车2 中型车3 商务车4 大型车5其他 )
	private String carColor;//车颜色
	private Status status;//状态:0 black 拉黑中;1已解除
	private String userName;//车主手机号（用户名）
	
	public enum Status{
		Defriend,//拉黑中
		Remove;//已解除
	}
	
	@Column(length=20)
	public String getCommunityNumber() {
		return communityNumber;
	}

	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
	@Column(length=20)
	public String getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	public Integer getCarType() {
		return carType;
	}

	public void setCarType(Integer carType) {
		this.carType = carType;
	}
	@Column(length=20)
	public String getCarColor() {
		return carColor;
	}

	public void setCarColor(String carColor) {
		this.carColor = carColor;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	@Column(length=20)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
