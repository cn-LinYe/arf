package com.arf.propertymgr.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "community_room_config")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "community_room_config_sequence")
public class CommunityRoomConfig extends BaseEntity<Long>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5903111916193769048L;

	private String communityNumber;//小区编号
	private PropertyUpload propertyUpload;//物业上传 (0、允许 Allow 1、禁止 Not_Allowed)
	private PropertyAudit propertyAudit;//物业审核(0、允许 Allow 1、禁止 Not_Allowed)
	private PropertyReview householdReview;//住户审核(0、允许 Allow 1、禁止 Not_Allowed)
	private PropertyAdded householdsAdded;//住户添加(0、允许 Allow 1、禁止 Not_Allowed)
	private AccessDevices accessDevices;//门禁设备(0、允许 allow 1、禁止 Not_Allowed)
	private AccessCard accessCard;//门禁卡(0、允许 allow 1、禁止 Not_Allowed)
	private ReceiveVisit receiveVisit;//接受来访(0、允许 allow 1、禁止 Not_Allowed)
	private InviteVisit inviteVisit;//邀请来访(0、允许 allow 1、禁止 Not_Allowed)
	private AccessPass accessPass;//门禁密码(0、允许 allow 1、禁止 Not_Allowed)
	private VisualAccessPass visualAccessPass;//可视门禁密码(0、允许 allow 1、禁止 Not_Allowed)
	private Integer hours;//临时密码有效时长
	
	public Integer getHours() {
		return hours;
	}
	public void setHours(Integer hours) {
		this.hours = hours;
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum PropertyUpload{
		Allow,Not_Allowed
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum PropertyAudit{
		Allow,Not_Allowed
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum PropertyReview{
		Allow,Not_Allowed
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum PropertyAdded{
		Allow,Not_Allowed
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum AccessDevices{
		Allow,Not_Allowed
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum AccessCard{
		Allow,Not_Allowed
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum ReceiveVisit{
		Allow,Not_Allowed
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum InviteVisit{
		Allow,Not_Allowed
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum AccessPass{
		Allow,Not_Allowed
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum VisualAccessPass{
		Allow,Not_Allowed
	}
	
	@Column(length=20)
	public String getCommunityNumber() {
		return communityNumber;
	}
	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
	public PropertyUpload getPropertyUpload() {
		return propertyUpload;
	}
	public void setPropertyUpload(PropertyUpload propertyUpload) {
		this.propertyUpload = propertyUpload;
	}
	public PropertyAudit getPropertyAudit() {
		return propertyAudit;
	}
	public void setPropertyAudit(PropertyAudit propertyAudit) {
		this.propertyAudit = propertyAudit;
	}
	public PropertyReview getHouseholdReview() {
		return householdReview;
	}
	public void setHouseholdReview(PropertyReview householdReview) {
		this.householdReview = householdReview;
	}
	public PropertyAdded getHouseholdsAdded() {
		return householdsAdded;
	}
	public void setHouseholdsAdded(PropertyAdded householdsAdded) {
		this.householdsAdded = householdsAdded;
	}
	public AccessDevices getAccessDevices() {
		return accessDevices;
	}
	public void setAccessDevices(AccessDevices accessDevices) {
		this.accessDevices = accessDevices;
	}
	public AccessCard getAccessCard() {
		return accessCard;
	}
	public void setAccessCard(AccessCard accessCard) {
		this.accessCard = accessCard;
	}
	public ReceiveVisit getReceiveVisit() {
		return receiveVisit;
	}
	public void setReceiveVisit(ReceiveVisit receiveVisit) {
		this.receiveVisit = receiveVisit;
	}
	public InviteVisit getInviteVisit() {
		return inviteVisit;
	}
	public void setInviteVisit(InviteVisit inviteVisit) {
		this.inviteVisit = inviteVisit;
	}
	public AccessPass getAccessPass() {
		return accessPass;
	}
	public void setAccessPass(AccessPass accessPass) {
		this.accessPass = accessPass;
	}
	public VisualAccessPass getVisualAccessPass() {
		return visualAccessPass;
	}
	public void setVisualAccessPass(VisualAccessPass visualAccessPass) {
		this.visualAccessPass = visualAccessPass;
	}

}
