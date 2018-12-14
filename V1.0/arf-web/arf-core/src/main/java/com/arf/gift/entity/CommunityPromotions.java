package com.arf.gift.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "gift_community_promotions")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "gift_community_promotions_sequence")
public class CommunityPromotions extends BaseEntity<Long> {

	private static final long serialVersionUID = -4266009999501961114L;

	private String communityName;//小区名称
	private String communityNumber;//小区编号
	private Integer monthlyPaymentStatus;//月租缴费优惠状态枚举: PARTICIPATE 0 参与 NOT_PARTICIPATE 1不参与
	private String cityName;//城市名称
	private String cityNumber;//城市编码
	private Integer popertyNumber;//物业公司编号
	private String popertyName;//物业公司名称
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum MonthlyPaymentStatus{
		PARTICIPATE,//0 参与
		NOT_PARTICIPATE;//1不参与
	}
	@Column(nullable=false,length=40)
	public String getCommunityName() {
		return communityName;
	}
	@Column(nullable=false,length=40)
	public String getCommunityNumber() {
		return communityNumber;
	}
	@Column(nullable=false)
	public Integer getMonthlyPaymentStatus() {
		return monthlyPaymentStatus;
	}
	@Column(nullable=false,length=40)
	public String getCityName() {
		return cityName;
	}
	@Column(nullable=false,length=40)
	public String getCityNumber() {
		return cityNumber;
	}
	@Column(nullable=false)
	public Integer getPopertyNumber() {
		return popertyNumber;
	}
	@Column(nullable=false,length=40)
	public String getPopertyName() {
		return popertyName;
	}

	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}

	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}

	public void setMonthlyPaymentStatus(Integer monthlyPaymentStatus) {
		this.monthlyPaymentStatus = monthlyPaymentStatus;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public void setCityNumber(String cityNumber) {
		this.cityNumber = cityNumber;
	}

	public void setPopertyNumber(Integer popertyNumber) {
		this.popertyNumber = popertyNumber;
	}

	public void setPopertyName(String popertyName) {
		this.popertyName = popertyName;
	}

}
