package com.arf.access.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;

/**
 * 安心点APP开门禁记录表,用于安心点APP用户版
 * @author Caolibao
 *
 */
@Entity
@Table(name = "access_unlock_log",
	indexes={
			@Index(name="index_community_number",columnList="communityNumber")}
)
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "access_unlock_log_sequence")
public class AccessUnlockLog extends BaseEntity<Long>{
	private static final long serialVersionUID = 6271075877342654421L;
	
	private String userName; //用户名/open_id
	private UnlockDevice unlockDevice; 
	private String checkNum; //开门校验码
	private String cmdContent; //开门指令内容
	private String bluetoothMac; //蓝牙mac地址
	private String unlockResp; //开门响应
	private String exceptionMsg; //异常信息
	private Integer usedSecond;//开门耗时,单位:秒
	private String unlockTime;//开门时间
	
	private String buildName;//楼栋
	private String unitName;//单元
	private String communityNumber;//门禁所在小区
	private AccessManagement.AccessType accessType;//门禁类型,小区大门:0,楼栋门:1
	
	private Integer osName;//手机操作系统:Android:0;IOS :1
    private String osVersion;//手机os版本
    private String appVersionName;//安心点APP当前版本
    private Integer appVersionCode;//安心点APP当前版本号
	
	/**
	 * 开门设备枚举
	 * @author Caolibao
	 *
	 */
	public enum UnlockDevice{
		APP, //安心点APP
		WECHAT_GZH, //WECHAT_GZH 微信公众号
		WECHAT_APPLET, //WECHAT_APPLET 微信小程序
		ACCESS_CARD, //ACCESS_CARD 门禁卡开门
		PASSWD_USER,//用户密码开门
		PASSWD_TEMP,//临时密码开门
		APP_OFFICE,//办公门禁APP
		;
	}
	
	@Column(length = 20, nullable = false)
	public String getUserName() {
		return userName;
	}
	public UnlockDevice getUnlockDevice() {
		return unlockDevice;
	}
	@Column(length = 10)
	public String getCheckNum() {
		return checkNum;
	}
	@Column(length = 512, nullable = false)
	public String getCmdContent() {
		return cmdContent;
	}
	@Column(length = 20, nullable = false)
	public String getBluetoothMac() {
		return bluetoothMac;
	}
	@Column(length = 512)
	public String getUnlockResp() {
		return unlockResp;
	}
	@Column(length = 512)
	public String getExceptionMsg() {
		return exceptionMsg;
	}
	@Column(length = 20)
	public String getBuildName() {
		return buildName;
	}
	@Column(length = 20)
	public String getUnitName() {
		return unitName;
	}
	@Column(length = 30)
	public String getCommunityNumber() {
		return communityNumber;
	}
	@Column(length = 20)
	public String getUnlockTime() {
		return unlockTime;
	}
	public AccessManagement.AccessType getAccessType() {
		return accessType;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setUnlockDevice(UnlockDevice unlockDevice) {
		this.unlockDevice = unlockDevice;
	}
	public void setCheckNum(String checkNum) {
		this.checkNum = checkNum;
	}
	public void setCmdContent(String cmdContent) {
		this.cmdContent = cmdContent;
	}
	public void setBluetoothMac(String bluetoothMac) {
		this.bluetoothMac = bluetoothMac;
	}
	public void setUnlockResp(String unlockResp) {
		this.unlockResp = unlockResp;
	}
	public void setExceptionMsg(String exceptionMsg) {
		this.exceptionMsg = exceptionMsg;
	}
	public void setBuildName(String buildName) {
		this.buildName = buildName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
	public void setAccessType(AccessManagement.AccessType accessType) {
		this.accessType = accessType;
	}
	public Integer getUsedSecond() {
		return usedSecond;
	}
	public void setUsedSecond(Integer usedSecond) {
		this.usedSecond = usedSecond;
	}
	public void setUnlockTime(String unlockTime) {
		this.unlockTime = unlockTime;
	}
	public Integer getOsName() {
		return osName;
	}
	public void setOsName(Integer osName) {
		this.osName = osName;
	}
	@Column(length = 30)
	public String getOsVersion() {
		return osVersion;
	}
	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}
	@Column(length = 30)
	public String getAppVersionName() {
		return appVersionName;
	}
	public void setAppVersionName(String appVersionName) {
		this.appVersionName = appVersionName;
	}
	public Integer getAppVersionCode() {
		return appVersionCode;
	}
	public void setAppVersionCode(Integer appVersionCode) {
		this.appVersionCode = appVersionCode;
	}
}
