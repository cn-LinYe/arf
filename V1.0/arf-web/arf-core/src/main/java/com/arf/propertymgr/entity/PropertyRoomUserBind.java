package com.arf.propertymgr.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang.RandomStringUtils;

import com.arf.core.entity.BaseEntity;
import com.arf.core.util.MD5Util;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "pty_property_room_user_bind",
	indexes={
			@Index(name="index_room_number",columnList="roomNumber"),
			@Index(name="index_bound_number",columnList="boundNumber")},
	uniqueConstraints = {
		@UniqueConstraint(columnNames = { "boundNumber" }, name = "unique_bound_number") })
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "pty_property_room_user_bind_sequence")
public class PropertyRoomUserBind extends BaseEntity<Long> {

	private static final long serialVersionUID = 3983005632677437633L;
	
	/**
     * 缓存错误输入户主手机号次数
     */
    public static final String ACCESS_TIME_FOR_ERROR_INPUT_MOBILE = "ACCESS_TIME_FOR_ERROR_INPUT_MOBILE:"; 
	
	private String userName;//用户名
	private String roomNumber;//房间唯一编号
	private String houseHolder;//户主(房产证业主)姓名
	private String houseHolderPhone;//户主(房产证业主)联系方式
	/**
	绑定唯一编号
	编号规则:
	(user_name+room_number)的MD5转大写
	 */
	private String boundNumber;
	private Date unboundDate;//解绑时间
	private Date boundDate;//绑定时间
	/**
	绑定状态:<br/>
	-1:UNBOUND 已解绑<br/>
	0:NEW 新建/新导入/未审核<br/>
	1:PASS 审核通过<br/>
	2:REJECT 审核驳回<br/>
	3:MODIFIED_PTY 被物业修改<br/>
	4:MODIFIED_USER 被用户修改
	 */
	private byte status;
	/**
	绑定方式:<br/>
	0:USER_ADD 用户添加<br/>
	1:MGR_UPLOAD 物业上传
	2:HOUSEHOLDER_ADD被邀请
	 */
	private byte boundType;
	private String sponsor;//绑定发起人,如果是用户添加就是user_name,否则就是物业人员登陆用户名
	private String auditor;//审核人 
	
	private EnableApplyResp enableApplyResp;//应答设置 DISABLE 关闭 ENABLE 开启;
	
	private Boolean needFirstAlert;//首次查询到该记录是否需要alert提示 
	
	private String unboundUser;//解绑人
	
	public String getUnboundUser() {
		return unboundUser;
	}
	@Column(length = 40)
	public void setUnboundUser(String unboundUser) {
		this.unboundUser = unboundUser;
	}
	public Boolean getNeedFirstAlert() {
		return needFirstAlert;
	}
	public void setNeedFirstAlert(Boolean needFirstAlert) {
		this.needFirstAlert = needFirstAlert;
	}
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum EnableApplyResp{
		DISABLE,//关闭
		ENABLE;//开启
	}
	
	@Column(length = 40)
	public String getAuditor() {
		return auditor;
	}
	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}

	/**
	绑定状态:<br/>
	-1:UNBOUND 已解绑<br/>
	0:NEW 新建/新导入/未审核<br/>
	1:PASS 审核通过<br/>
	2:REJECT 审核驳回<br/>
	3:MODIFIED_PTY 被物业修改<br/>
	4:MODIFIED_USER 被用户修改
	 * @author lenovo
	 */
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum Status{
		UNBOUND(-1),
		NEW(0),
		PASS(1),
		REJECT(2),
		MODIFIED_PTY(3),
		MODIFIED_USER(4);
		
		public int staus;
		
		private Status(int staus){
			this.staus = staus;
		};
		
		public int getStaus(){
			return staus;
		}
		
	}
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum BoundType{
		USER_ADD,
		MGR_UPLOAD;
		
		public static BoundType get(int ordinal){
			BoundType boundType[] = BoundType.values();
			if(ordinal<0 || ordinal>boundType.length-1){
				return null;
			}else{
				return boundType[ordinal];
			}
		}
	}
	
	@Column(length = 20)
	public String getUserName() {
		return userName;
	}
	@Column(length = 40)
	public String getRoomNumber() {
		return roomNumber;
	}
	@Column(length = 10)
	public String getHouseHolder() {
		return houseHolder;
	}
	@Column(length = 20)
	public String getHouseHolderPhone() {
		return houseHolderPhone;
	}
	@Column(length = 40)
	public String getBoundNumber() {
		return boundNumber;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	public Date getUnboundDate() {
		return unboundDate;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(nullable = false)
	public Date getBoundDate() {
		return boundDate;
	}
	public byte getStatus() {
		return status;
	}
	public byte getBoundType() {
		return boundType;
	}
	@Column(length = 20)
	public String getSponsor() {
		return sponsor;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}
	public void setHouseHolder(String houseHolder) {
		this.houseHolder = houseHolder;
	}
	public void setHouseHolderPhone(String houseHolderPhone) {
		this.houseHolderPhone = houseHolderPhone;
	}
	public void setBoundNumber(String boundNumber) {
		this.boundNumber = boundNumber;
	}
	public void setUnboundDate(Date unboundDate) {
		this.unboundDate = unboundDate;
	}
	public void setBoundDate(Date boundDate) {
		this.boundDate = boundDate;
	}
	public void setStatus(byte status) {
		this.status = status;
	}
	public void setBoundType(byte boundType) {
		this.boundType = boundType;
	}
	public void setSponsor(String sponsor) {
		this.sponsor = sponsor;
	}
	
	public EnableApplyResp getEnableApplyResp() {
		if(enableApplyResp == null){ //默认所有人都是开启的
			enableApplyResp = EnableApplyResp.ENABLE;
		}
		return enableApplyResp;
	}
	public void setEnableApplyResp(EnableApplyResp enableApplyResp) {
		this.enableApplyResp = enableApplyResp;
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
