package com.arf.propertymgr.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.RandomStringUtils;

import com.arf.core.entity.BaseEntity;
import com.arf.core.util.MD5Util;
import com.arf.propertymgr.entity.PropertyRoomUserBind.EnableApplyResp;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "pty_property_room_subuser_bind")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "pty_property_room_subuser_bind_sequence")
public class PropertyRoomSubuserBind extends BaseEntity<Long> {

	private static final long serialVersionUID = -3552893859302798337L;
	
	private String userName;//用户名
	private String realName;//真实姓名
	private Status status;//NEW 0 新建/等待接受,  ACCEPT 1 已接收,REJECT 2 已拒绝,UNBOUND 3已解绑
	/**
	 * 从用户类型枚举: -1 房产证业主（此值没有对应枚举值，仅供后台页面显示使用，实际对应记录在PropertyRoomUserBind中；
	 * 注意2.2.5之后将"户主"称之为"房产证业主"，实际权限就是之前版本的"户主"） 
	 * FAMILY_MEMBER 0 家属 ,
	 * TENANT 1 租客 
	 * MANAGER 2 管理员 
	 * UNKNOWN 3 未知
	 * PROPRIETOR 4 业主
	 */
	private UserType userType;
	private String houseHolder;//户主(房产证业主手机号码)
	/**
	绑定唯一编号
	编号规则:
	(user_name+room_number)的MD5转大写
	 */
	private String boundNumber;
	private String roomNumber;//房间唯一编号
	private Date boundDate;//绑定时间
	private String sponsor;//绑定发起人,如果是用户添加就是user_name,否则就是住户自己
	private Integer propertyAudit = 0;//是否需要物业审核,0 不需要;1 需要(为null默认为0)
	
	private EnableApplyResp enableApplyResp;//应答设置 DISABLE 关闭 ENABLE 开启;
	private ManagerStatus managerStatus;//设置管理员状态：1 邀请成为管理员，2 已接收， 3 已拒绝, 4 取消设置为管理员
	private String houseHolderShortMobile;//用户输入的业主手机后四位
	
	private Boolean needFirstAlert;//首次查询到该记录是否需要alert提示
	private Date unboundDate;//解绑时间
	private String unboundUser;//解绑人
	
	public Date getUnboundDate() {
		return unboundDate;
	}
	@Column(length = 40)
	public String getUnboundUser() {
		return unboundUser;
	}
	public void setUnboundDate(Date unboundDate) {
		this.unboundDate = unboundDate;
	}
	public void setUnboundUser(String unboundUser) {
		this.unboundUser = unboundUser;
	}
	public Integer getPropertyAudit() {
		return propertyAudit;
	}
	public void setPropertyAudit(Integer propertyAudit) {
		this.propertyAudit = propertyAudit;
	}

	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public EnableApplyResp getEnableApplyResp() {
		if(enableApplyResp == null){ //默认所有人都是开启的
			enableApplyResp = EnableApplyResp.ENABLE;
		}
		return enableApplyResp;
	}
	public void setEnableApplyResp(EnableApplyResp enableApplyResp) {
		this.enableApplyResp = enableApplyResp;
	}
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum ManagerStatus{
		nouse,//占位无意义
		invite,//邀请成为管理员
		accept,//已接收
		refuse,//已拒绝
		cancel;//取消设置为管理员
	}

	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum Status{
		NEW,//新建
		ACCEPT,//已接收
		REJECT,//已拒绝
		UNBOUND; //已解绑
	}
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum UserType{
		FAMILY_MEMBER,
		TENANT,
		MANAGER,
		UNKNOWN,
		PROPRIETOR;
	}

	@Column(length = 20)
	public String getUserName() {
		return userName;
	}
	@Column(length = 20)
	public String getRealName() {
		return realName;
	}
	public Status getStatus() {
		return status;
	}
	public UserType getUserType() {
		return userType;
	}
	@Column(length = 20)
	public String getHouseHolder() {
		return houseHolder;
	}
	@Column(length = 40)
	public String getBoundNumber() {
		return boundNumber;
	}
	@Column(length = 40)
	public String getRoomNumber() {
		return roomNumber;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	public Date getBoundDate() {
		return boundDate;
	}
	@Column(length = 20)
	public String getSponsor() {
		return sponsor;
	}
	@Column(length=20)
	public String getHouseHolderShortMobile() {
		return houseHolderShortMobile;
	}
	public void setHouseHolderShortMobile(String houseHolderShortMobile) {
		this.houseHolderShortMobile = houseHolderShortMobile;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public void setHouseHolder(String houseHolder) {
		this.houseHolder = houseHolder;
	}

	public void setBoundNumber(String boundNumber) {
		this.boundNumber = boundNumber;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	public void setBoundDate(Date boundDate) {
		this.boundDate = boundDate;
	}

	public void setSponsor(String sponsor) {
		this.sponsor = sponsor;
	}
	
	public ManagerStatus getManagerStatus() {
		return managerStatus;
	}
	public void setManagerStatus(ManagerStatus managerStatus) {
		this.managerStatus = managerStatus;
	}
	
	public Boolean getNeedFirstAlert() {
		return needFirstAlert;
	}
	public void setNeedFirstAlert(Boolean needFirstAlert) {
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
