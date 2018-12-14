package com.arf.base;

import java.util.Date;

import com.arf.carbright.entity.CarWashingRecord.Status;

public class AppLog{
	
	/**
     * APP日志表名前缀
     */
	public static final String APPLOG_TABLENAMEPRE="app_log_";
	
	public enum LogType{
		
		/**
		 * 其它杂项日志记录
		 */
		APPLOG_OTHER,
		
		/**
	     * APP日志记录：登录APP
	     */
	    APPLOG_LOGIN_APP,
	    /**
	     * APP日志记录：退出APP
	     */
	     APPLOG_EXIT_APP,
	    /**
	     * APP日志记录：月租车出闸
	     */
	     APPLOG_PARKRATE_OUT,
	    /**
	     * APP日志记录：月租车入闸
	     */
	     APPLOG_PARKRATE_OVER,
	    /**
	     * APP日志记录：临时车入闸
	     */
	     APPLOG_TEMP_OUT,
	    /**
	     * APP日志记录：临时车出闸
	     */
	     APPLOG_TEMP_OVER,
	    /**
	     * APP日志记录：解锁
	     */
	     APPLOG_UNLOCK_LICENSE,
	    /**
	     * APP日志记录：锁闸
	     */
	     APPLOG_LOCK_LICENSE,
	    /**
	     * APP日志记录：点击安心点
	     */
	     APPLOG_CLICK_AXD,
	    /**
	     * APP日志记录：信用卡申请
	     */
	     APPLOG_BUSINESS_CREDITCARD,
	    /**
	     * APP日志记录：新用户注册
	     */
	     APPLOG_REGISTER_APP,
	    /**
	     * APP日志记录：用户点滴洗下订单成功
	     */
	     APPLOG_CAR_BRIGHTER,
	    /**
	     * APP日志记录：用户添加车牌
	     */
	     APPLOG_ADD_LICENSE,
	    /**
	     * APP日志记录：用户删除车牌
	     */
	     APPLOG_DEL_LICENSE,
	    /**
	     * APP日志记录：用户24小时自由出入配置
	     */
	     APPLOG_AXD_CONFIG,
	     /**
	      * APP日志记录:token校验失败
	      */
	     APPLOG_TOKEN_FAILED,
	     ;
		
		public static LogType get(Integer ordinal){
			if(ordinal == null || ordinal < 0){
				return null;
			}
			LogType types[] = LogType.values();
			if(ordinal > types.length - 1){
				return null;
			}else{
				return types[ordinal];
			}
		}
	}
	
	private String licensePlateNumber;
	private String communityNumber;
	private String userName;
	private LogType type;
	private String descript;
	private String version;
	private String deviceType;
	private String deviceNumber;
	private Integer propertyNumber;
	private Integer branchId;
	
	public AppLog(){
		super();
	}
	
	public AppLog(AppBaseInfo appBaseInfo){
		super();
		this.version = appBaseInfo.getAppVersionName();
		this.deviceType = appBaseInfo.getOsName() == null ? "" : (appBaseInfo.getOsName() + "");
		this.deviceNumber = appBaseInfo.getRegistrationId();
	}
	
	public AppLog(AppBaseInfo appBaseInfo,String userName,LogType type,String descript){
		super();
		if(appBaseInfo != null){
			this.version = appBaseInfo.getAppVersionName();
			this.deviceType = appBaseInfo.getOsName() == null ? "" : (appBaseInfo.getOsName() + "");
			this.deviceNumber = appBaseInfo.getRegistrationId();
		}
		this.userName = userName;
		this.type = type;
		this.descript = descript;
	}
	
	public String getLicensePlateNumber() {
		return licensePlateNumber;
	}
	public String getCommunityNumber() {
		return communityNumber;
	}
	public String getUserName() {
		return userName;
	}
	public LogType getType() {
		return type;
	}
	public String getDescript() {
		return descript;
	}
	public String getVersion() {
		return version;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public String getDeviceNumber() {
		return deviceNumber;
	}
	public Integer getPropertyNumber() {
		return propertyNumber;
	}
	public Integer getBranchId() {
		return branchId;
	}
	public void setLicensePlateNumber(String licensePlateNumber) {
		this.licensePlateNumber = licensePlateNumber;
	}
	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setType(LogType type) {
		this.type = type;
	}
	public void setDescript(String descript) {
		this.descript = descript;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public void setDeviceNumber(String deviceNumber) {
		this.deviceNumber = deviceNumber;
	}
	public void setPropertyNumber(Integer propertyNumber) {
		this.propertyNumber = propertyNumber;
	}
	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}
}
