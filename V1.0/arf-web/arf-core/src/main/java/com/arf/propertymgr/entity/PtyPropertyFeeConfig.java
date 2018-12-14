package com.arf.propertymgr.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.arf.propertymgr.entity.PropertyRoomInfo.RoomType;
import com.fasterxml.jackson.annotation.JsonFormat;

/*
 * 物业费用配置表
 */
@Entity
@Table(name = "pty_property_fee_config")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "pty_property_fee_config_sequence")
public class PtyPropertyFeeConfig extends BaseEntity<Long>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3609768298681881389L;
	private String communityNumber	;//varchar(50)	否		普通	小区编号
	private BigDecimal price	;//decimal	否		普通	单价（分）
	private RoomType roomType	;//byte	否		普通	0住户1商铺
	private String building	;//varchar(20）	是		普通	楼栋
	private String floor	;//varchar(20）	是		普通	楼层
	private Compare compare	;//int	否		普通0大于等于1等于2小于等于
	private ChargeType chargeType	;//int	否		普通	0:ACCORD_ACREAGE按照面积1:FIXED_PRICE 一口价
	private BigDecimal energyFee	;//decimal	否		普通	能耗
	private BigDecimal greenFee	;//decimal	否		普通	绿化费
	private BigDecimal hygieneFee	;//decimal	否		普通	卫生费
	private BigDecimal otherFee	;//decimal	否		普通	其他费用（json格式）
	private BigDecimal totalPrice	;//一口价物业费
	private Status status	;//int	否		普通	0close关闭 1:open开启
	private BigDecimal ordinaryRule ;//普通规则价格
	
	
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum Status{
		close,open
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum ChargeType{
		ACCORD_ACREAGE,FIXED_PRICE
	}
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum Compare{
		greater,equal,less
	}
	
	@Column(precision=10,scale=2)
	public BigDecimal getOrdinaryRule() {
		return ordinaryRule;
	}

	public void setOrdinaryRule(BigDecimal ordinaryRule) {
		this.ordinaryRule = ordinaryRule;
	}

	@Column(precision=10,scale=2)
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	@Column(length=50)
	public String getCommunityNumber() {
		return communityNumber;
	}

	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}

	@Column(precision=10,scale=2)
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public RoomType getRoomType() {
		return roomType;
	}

	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
	}
	@Column(length=20)
	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}
	@Column(length=20)
	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public Compare getCompare() {
		return compare;
	}

	public void setCompare(Compare compare) {
		this.compare = compare;
	}

	public ChargeType getChargeType() {
		return chargeType;
	}

	public void setChargeType(ChargeType chargeType) {
		this.chargeType = chargeType;
	}
	@Column(precision=10,scale=2)
	public BigDecimal getEnergyFee() {
		return energyFee;
	}

	public void setEnergyFee(BigDecimal energyFee) {
		this.energyFee = energyFee;
	}
	@Column(precision=10,scale=2)
	public BigDecimal getGreenFee() {
		return greenFee;
	}

	public void setGreenFee(BigDecimal greenFee) {
		this.greenFee = greenFee;
	}
	@Column(precision=10,scale=2)
	public BigDecimal getHygieneFee() {
		return hygieneFee;
	}

	public void setHygieneFee(BigDecimal hygieneFee) {
		this.hygieneFee = hygieneFee;
	}
	@Column(precision=10,scale=2)
	public BigDecimal getOtherFee() {
		return otherFee;
	}

	public void setOtherFee(BigDecimal otherFee) {
		this.otherFee = otherFee;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	
	
	
}
