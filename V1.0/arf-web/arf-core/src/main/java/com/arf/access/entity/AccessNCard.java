package com.arf.access.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "access_n_card")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "access_n_card_sequence")
public class AccessNCard extends BaseEntity<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -84456867759263009L;
	
	private String cardNumber;//卡片编号（限制6位0-9或者A-F字母+数字）
	private CardType cardType;//卡片类型枚举（0、Households住户卡 1、Property 物管卡）
	private String communityNumber;//小区信息	
	private String midCommunityNumber;//设备小区信息	
	private String userName;//用户信息		
	private Date startEffective;//起始有效期				
	private Date endEffective;//结束有效期				
	private String phone;//业主联系电话	
	private Integer startPeriod;//起始时间段				
	private Integer endPeriod;//结束时间段				
	private Status status;//卡片状态（0正常 1挂失2过期）				
	private Date reportedLostDate;//挂失时间	
	private String roomNumber;//房屋编号
	private String roomBoundNumber;//绑定唯一编号
	private String operateUser;//操作员
	private IsActive isActive;//是否激活 0 No 未激活 、1 Yes 已激活

	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum CardType{
		Households,Property;
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum Status{
		Normal,Lost,Expired;
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum IsActive{
		No,Yes;
	}
	@Column(length=20)
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public CardType getCardType() {
		return cardType;
	}
	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}
	@Column(length=20)
	public String getCommunityNumber() {
		return communityNumber;
	}
	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
	@Column(length=20)
	public String getMidCommunityNumber() {
		return midCommunityNumber;
	}
	public void setMidCommunityNumber(String midCommunityNumber) {
		this.midCommunityNumber = midCommunityNumber;
	}
	@Column(length=20)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getStartEffective() {
		return startEffective;
	}
	public void setStartEffective(Date startEffective) {
		this.startEffective = startEffective;
	}
	public Date getEndEffective() {
		return endEffective;
	}
	public void setEndEffective(Date endEffective) {
		this.endEffective = endEffective;
	}
	@Column(length=20)
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getStartPeriod() {
		return startPeriod;
	}
	public void setStartPeriod(Integer startPeriod) {
		this.startPeriod = startPeriod;
	}
	public Integer getEndPeriod() {
		return endPeriod;
	}
	public void setEndPeriod(Integer endPeriod) {
		this.endPeriod = endPeriod;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public Date getReportedLostDate() {
		return reportedLostDate;
	}
	public void setReportedLostDate(Date reportedLostDate) {
		this.reportedLostDate = reportedLostDate;
	}
	@Column(length=40)
	public String getRoomNumber() {
		return roomNumber;
	}
	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}
	@Column(length=40)
	public String getRoomBoundNumber() {
		return roomBoundNumber;
	}
	public void setRoomBoundNumber(String roomBoundNumber) {
		this.roomBoundNumber = roomBoundNumber;
	}
	public String getOperateUser() {
		return operateUser;
	}
	public void setOperateUser(String operateUser) {
		this.operateUser = operateUser;
	}
	public IsActive getIsActive() {
		return isActive;
	}
	public void setIsActive(IsActive isActive) {
		this.isActive = isActive;
	}

}
