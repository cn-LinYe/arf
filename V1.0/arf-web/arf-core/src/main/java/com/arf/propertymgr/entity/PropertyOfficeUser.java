package com.arf.propertymgr.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.RandomStringUtils;

import com.arf.core.entity.BaseEntity;
import com.arf.core.util.MD5Util;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "property_office_user")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "property_office_user")
public class PropertyOfficeUser extends BaseEntity<Long> {

	private static final long serialVersionUID = 3983005632677437633L;
	
	
	private String userName;//用户名
	private String realName;//真实姓名
	private String communityNumber;//小区编号
	/**
	绑定状态:
		0 NEW新建 
		1 SUCCESS绑定成功
		2 UNBOUND已解绑
	 */
	private Status status;
	/**
	身份
		0 MANAGER物业经理
		1 CHARGE物业主管
		2 SECURITY安保人员
		3 REPAIR维修人员
		4 SERVICE客服人员
		5 ORTHER其他人员
	 */
	private Type type;
	private Date bindingDate;//绑定时间
	private String bingdingUser;//绑定发起人手机号
	private String unboundUser;//解绑人手机号
	private Date unboundDate;//解绑时间
	/**
	绑定方式
		0 SYSTEM后台绑定
	 */
	private BindingType bindingType;
	
	private String boundNumber;
	private String roomNumber;//房间唯一编号
	/**
	是否弹窗提示
		0 TRUE 是
		1 FALSE否
	 */
	private NeedFirstAlert needFirstAlert;
	
	/**
	绑定状态:
		0 NEW新建 
		1 SUCCESS绑定成功
		2 UNBOUND已解绑
	 */
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum Status{
		 NEW,//新建 
		 SUCCESS,//绑定成功
		 UNBOUND,//已解绑
	}
	/**
	身份
		0 MANAGER物业经理
		1 CHARGE物业主管
		2 SECURITY安保人员
		3 REPAIR维修人员
		4 SERVICE客服人员
		5 ORTHER其他人员
	 */
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum Type{
		 MANAGER,//物业经理
		 CHARGE,//物业主管
		 SECURITY,//安保人员
		 REPAIR,//维修人员
		 SERVICE,//客服人员
		 ORTHER,//其他人员
	}
	/**
	绑定方式
		0 SYSTEM后台绑定
	 */
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum BindingType{
		SYSTEM
	}
	/**
	是否弹窗提示
		0 TRUE 是
		1 FALSE否
	 */
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum NeedFirstAlert{
		 TRUE,// 是
		 FALSE,//否
	}
	@Column(length = 20)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(length = 20)
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	@Column(length = 20)
	public String getCommunityNumber() {
		return communityNumber;
	}
	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	public Date getBindingDate() {
		return bindingDate;
	}
	public void setBindingDate(Date bindingDate) {
		this.bindingDate = bindingDate;
	}
	@Column(length = 20)
	public String getBingdingUser() {
		return bingdingUser;
	}
	public void setBingdingUser(String bingdingUser) {
		this.bingdingUser = bingdingUser;
	}
	@Column(length = 20)
	public String getUnboundUser() {
		return unboundUser;
	}
	public void setUnboundUser(String unboundUser) {
		this.unboundUser = unboundUser;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	public Date getUnboundDate() {
		return unboundDate;
	}
	public void setUnboundDate(Date unboundDate) {
		this.unboundDate = unboundDate;
	}
	public BindingType getBindingType() {
		return bindingType;
	}
	public void setBindingType(BindingType bindingType) {
		this.bindingType = bindingType;
	}
	@Column(length = 40)
	public String getBoundNumber() {
		return boundNumber;
	}
	public void setBoundNumber(String boundNumber) {
		this.boundNumber = boundNumber;
	}
	@Column(length = 40)
	public String getRoomNumber() {
		return roomNumber;
	}
	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}
	public NeedFirstAlert getNeedFirstAlert() {
		return needFirstAlert;
	}
	public void setNeedFirstAlert(NeedFirstAlert needFirstAlert) {
		this.needFirstAlert = needFirstAlert;
	}
	
	
	/**
	绑定唯一编号
	编号规则:
	(user_name+room_number)的MD5转大写
	 */
	public static String genBoundNumber(String userName,String roomNumber){
		return MD5Util.getMD5(userName + roomNumber + RandomStringUtils.randomAlphanumeric(16)).toUpperCase();
	}
	
	
	
}
