package com.arf.carbright.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "p_customer_service_management")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "p_customer_service_management_sequence")
public class PCustomerServiceManagement extends BaseEntity<Long>{	
	/**
	 * 客服人员管理表
	 */
	private static final long serialVersionUID = 1L;
	public String fullName;//用户名
	public String phone;//手机号码
	public Department department;//部门名称
	public String wechatOpenid;//微信openid
	public String nailsManagerid;//钉钉managerid	
	public Integer smsNotice;//短信通知（默false,true）
	public Integer wechatNotice;//微信通知（默true,false）
	public Integer nailsNotice;//钉钉通知（默false,true）
	public Integer status;//状态(0启用1禁用);
	public Integer messageType;//消息类型(0全部、意见反馈1 2投诉)
	public String token;//融云token
	
	public String communities;//关联小区,通过英文逗号(,)分隔
	
	public enum Department{
		nouse,//占位，未使用
		CustomerService,//客服部
		OperationAndMaintenance,//运维部
		PropertyMGR,//物业管理处(物业缴费)
		FinanceDepartment,//财务部
	}
	public enum MessageType{
		ALL,
		Feedback,//意见反馈
		Complaints,//投诉
		CarWashingDeviceOffline,//洗车机离线
		Withdraw,//提现申请
	}
	
	public enum Status{
		Enable,Disable;
	}
	
	@Column(length = 128)
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	@Column(length = 40)
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	@Column(length = 100)
	public String getWechatOpenid() {
		return wechatOpenid;
	}
	public void setWechatOpenid(String wechatOpenid) {
		this.wechatOpenid = wechatOpenid;
	}
	@Column(length = 100)
	public String getNailsManagerid() {
		return nailsManagerid;
	}
	public void setNailsManagerid(String nailsManagerid) {
		this.nailsManagerid = nailsManagerid;
	}	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getMessageType() {
		return messageType;
	}
	public void setMessageType(Integer messageType) {
		this.messageType = messageType;
	}
	public Integer getSmsNotice() {
		return smsNotice;
	}
	public void setSmsNotice(Integer smsNotice) {
		this.smsNotice = smsNotice;
	}
	public Integer getWechatNotice() {
		return wechatNotice;
	}
	public void setWechatNotice(Integer wechatNotice) {
		this.wechatNotice = wechatNotice;
	}
	public Integer getNailsNotice() {
		return nailsNotice;
	}
	public void setNailsNotice(Integer nailsNotice) {
		this.nailsNotice = nailsNotice;
	}
	
	@Column(length=5000)
	public String getCommunities() {
		return communities;
	}
	public void setCommunities(String communities) {
		this.communities = communities;
	}
}
