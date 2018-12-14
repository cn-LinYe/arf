package com.arf.access.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "access_management",
uniqueConstraints={@UniqueConstraint(columnNames={"accessNum"},name="UK_access_num")})
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "access_management_sequence")
public class AccessManagement extends BaseEntity<Long>{

	private static final long serialVersionUID = 6558145682981779026L;

	private String accessName;//门禁名称
	private String sn;//设备序列号
	private String tempPassword;//临时密码
	private String managerPassword;//管理员密码
	private String initialManagerPassword;//初始管理员密码
	private String unlockPassword;//开锁密码
	private String initialUnlockPassword;//初始开锁密码
	private Date lastSynchTime;//上一次同步时间
	private String productModel;//产品型号
	private String firmwareVersion;//固件版本号
	private String bluetoothMac;//锁蓝牙Mac
	private Integer status;//状态:负1-未完成锁初始化设置,0-正常
	private String certificateCode;//开锁验证码
	private String communityNumber;//小区编号
	private Integer accessType;//门禁类型0 大门门禁1 楼门门禁 2办公室门禁3 梯控门禁-外挂式4 梯控门禁-内置式
	private Integer accessIndex;//门禁顺序
	private String accessInstallAddress;//门禁安装地址
	
	private String lastSyncUser;//上次同步操作用户
	
	private String buildingName; //楼栋门禁对应的大楼名
	private String unitName; //楼栋门禁对应的单元名
	private Integer building; //楼栋门禁对应的楼栋编号,后台自动生成
	private Integer unit; //楼栋门禁对应的单元编号,后台自动生成
	
	private Long accessNum; // 门禁编号,8位数字编号
	
	private String qrcodeUrl; //门禁二维码
	
	private String features = "ACCESS_CARD,ACCESS_PASSWD,TEMP_PASSWD,BLUETOOTH";//支持的门禁特性,例如:ACCESS_CARD,BAR_CODE 则支持门禁卡和条形码开门
	
	private AuthencateAccessType authencateAccessType;//门禁设备类型0 可视门禁1 蓝牙门禁
	
	private String accessVersion;//可视门禁版本信息
	private String visualAccessVersion;//可视门禁版本信息
	private String visualKernelVersion;//可视门禁内核版本信息
	private String accessHardwareVersion;//可视门禁硬件版本信息
	private String visualAccessHardwareVersion;//可视门禁硬件版本信息
	private CustomerType customerType;//客户类型（0 community 小区 1 company 公司）
	private String iccid;//simboss卡编号
	private String imsi;//simboss卡编号
	private NetStatus netStatus;//联网状态, ACTIVATED_NAME 表示当前联网中, DEACTIVATED_NAME 表示当前断网中,TEST_READY_NAME 表示当前处于测试期  
	private StopStatus stopStatus;//停复机状态, ACTIVATED_NAME 表示当前正常, DEACTIVATED_NAME 表示当前已停机 
	private DeviceStatus deviceStatus;//开关机状态, ACTIVATED_NAME 表示当前开机, DEACTIVATED_NAME 表示当前已关机
	
	private String productVersion;//产品版本型号
	
	
	private Integer openTime; //开启梯控门禁时长，单位：秒
	private ElevatorMode elevatorMode; //梯控模式 0开启 1关闭
	private Integer volume; //音量

	private String deviceSoftwareVersion;//设备软件版本
	private String deviceBluetoothVersion;//设备蓝牙版本
	private String devicePcbVersion;//设备PCB版本

	/**
		//梯控模式 0开启 1关闭
	*/
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum ElevatorMode{
		OPEN,
		CLOSE;
	}
	
	public ElevatorMode getElevatorMode() {
		return elevatorMode;
	}
	public void setElevatorMode(ElevatorMode elevatorMode) {
		this.elevatorMode = elevatorMode;
	}
	public Integer getOpenTime() {
		return openTime;
	}
	public void setOpenTime(Integer openTime) {
		this.openTime = openTime;
	}
	
	public Integer getVolume() {
		return volume;
	}
	public void setVolume(Integer volume) {
		this.volume = volume;
	}
	public enum AccessFeature{
		ACCESS_CARD, //支持门禁卡开门,默认存在
		ACCESS_PASSWD,//固定密码开门,默认存在
		TEMP_PASSWD,//临时密码开门,默认存在
		BLUETOOTH,//蓝牙开门,默认存在
		
		BAR_CODE, //支持条形码开门
		QR_CODE, //二维码开门
		VIDEO_PHONE,//视频通话,
		;
	}
	
	/**
		同步状态枚举:
		NOT_SYNC 0 未同步
		SUCCESS 1 成功
		FAILED 2 失败
	 */
	private SyncStatus syncStatus;
	
	
	
	/**
		门禁用途枚举:
		IN 0 进
		OUT 1 出
	 */
	private UseType useType;
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum SyncStatus{
		NOT_SYNC,
		SUCCESS,
		FAILED;
	}
	
	 public enum Status{
		Uninitialized(-1),//负1-未完成锁初始化设置
		Normal(0)//0-正常
		;
		 public int staus;
			
		private Status(int staus){
			this.staus = staus;
		};
			
		public static int getStaus(Status sta){
			return sta.staus;
		}
			
		public int getStaus(){
			return staus;
		}
	}
	 
	 public enum AccessType{
		 AccessGate,//0 大门门禁
		 AccessDoor,//1 楼门门禁
		 AccessRoom,//2 办公室门禁门禁
		 AccessEleOut,//3 梯控门禁-外挂式
		 AccessEleIn,//4 梯控门禁-内置式
		;
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum UseType{
		IN,
		OUT;
	}
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum AuthencateAccessType{
		Internet,
		Bluetooth;
	}
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum CustomerType{
		community,
		company;
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum NetStatus{
		ACTIVATED_NAME,//表示当前联网中
		DEACTIVATED_NAME,//表示当前断网中
		TEST_READY_NAME;//表示当前处于测试期  
	}
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum StopStatus{
		ACTIVATED_NAME,//表示当前正常
		DEACTIVATED_NAME;//表示当前已停机 
	}
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum DeviceStatus{
		ACTIVATED_NAME,//表示当前开机
		DEACTIVATED_NAME;//表示当前已关机
	}
	
	@Column(nullable = false,length=20)
	public String getAccessName() {
		return accessName;
	}
	@Column(nullable = false,length=64)
	public String getSn() {
		return sn;
	}
	@Column(nullable = false,length=64)
	public String getTempPassword() {
		return tempPassword;
	}
	@Column(nullable = false,length=64)
	public String getManagerPassword() {
		return managerPassword;
	}
	@Column(length=64)
	public String getInitialManagerPassword() {
		return initialManagerPassword;
	}
	@Column(length=64)
	public String getUnlockPassword() {
		return unlockPassword;
	}
	@Column(length=64)
	public String getInitialUnlockPassword() {
		return initialUnlockPassword;
	}
	public Date getLastSynchTime() {
		return lastSynchTime;
	}
	@Column(length=32)
	public String getProductModel() {
		return productModel;
	}
	@Column(length=32)
	public String getFirmwareVersion() {
		return firmwareVersion;
	}
	@Column(length=32)
	public String getBluetoothMac() {
		return bluetoothMac;
	}
	@Column(nullable = false)
	public Integer getStatus() {
		return status;
	}
	@Column(length=32)
	public String getCertificateCode() {
		return certificateCode;
	}
	@Column(length=20)
	public String getCommunityNumber() {
		return communityNumber;
	}
	@Column(nullable = false)
	public Integer getAccessType() {
		return accessType;
	}
	@Column(nullable = false)
	public Integer getAccessIndex() {
		return accessIndex;
	}
	@Column(length=100)
	public String getAccessInstallAddress() {
		return accessInstallAddress;
	}
	
	public void setAccessInstallAddress(String accessInstallAddress) {
		this.accessInstallAddress = accessInstallAddress;
	}
	public void setAccessName(String accessName) {
		this.accessName = accessName;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public void setTempPassword(String tempPassword) {
		this.tempPassword = tempPassword;
	}
	public void setManagerPassword(String managerPassword) {
		this.managerPassword = managerPassword;
	}
	public void setInitialManagerPassword(String initialManagerPassword) {
		this.initialManagerPassword = initialManagerPassword;
	}
	public void setUnlockPassword(String unlockPassword) {
		this.unlockPassword = unlockPassword;
	}
	public void setInitialUnlockPassword(String initialUnlockPassword) {
		this.initialUnlockPassword = initialUnlockPassword;
	}
	public void setLastSynchTime(Date lastSynchTime) {
		this.lastSynchTime = lastSynchTime;
	}
	public void setProductModel(String productModel) {
		this.productModel = productModel;
	}
	public void setFirmwareVersion(String firmwareVersion) {
		this.firmwareVersion = firmwareVersion;
	}
	public void setBluetoothMac(String bluetoothMac) {
		this.bluetoothMac = bluetoothMac;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public void setCertificateCode(String certificateCode) {
		this.certificateCode = certificateCode;
	}
	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
	public void setAccessType(Integer accessType) {
		this.accessType = accessType;
	}
	public void setAccessIndex(Integer accessIndex) {
		this.accessIndex = accessIndex;
	}
	@Column(length=20)
	public String getLastSyncUser() {
		return lastSyncUser;
	}
	public void setLastSyncUser(String lastSyncUser) {
		this.lastSyncUser = lastSyncUser;
	}
	public SyncStatus getSyncStatus() {
		return syncStatus;
	}
	public void setSyncStatus(SyncStatus syncStatus) {
		this.syncStatus = syncStatus;
	}
	public String getBuildingName() {
		return buildingName;
	}
	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public Integer getBuilding() {
		return building;
	}
	public void setBuilding(Integer building) {
		this.building = building;
	}
	public Integer getUnit() {
		return unit;
	}
	public void setUnit(Integer unit) {
		this.unit = unit;
	}
	@Column(nullable=true)
	public Long getAccessNum() {
		return accessNum;
	}
	public void setAccessNum(Long accessNum) {
		this.accessNum = accessNum;
	}
	@Column(length=256)
	public String getQrcodeUrl() {
		return qrcodeUrl;
	}
	public void setQrcodeUrl(String qrcodeUrl) {
		this.qrcodeUrl = qrcodeUrl;
	}
	public String getFeatures() {
		return features;
	}
	public void setFeatures(String features) {
		this.features = features;
	}
	public UseType getUseType() {
		return useType;
	}
	public void setUseType(UseType useType) {
		this.useType = useType;
	}
	public AuthencateAccessType getAuthencateAccessType() {
		return authencateAccessType;
	}
	public void setAuthencateAccessType(AuthencateAccessType authencateAccessType) {
		this.authencateAccessType = authencateAccessType;
	}
	@Column(length=20)
	public String getAccessVersion() {
		return accessVersion;
	}
	public void setAccessVersion(String accessVersion) {
		this.accessVersion = accessVersion;
	}
	@Column(length=20)
	public String getVisualAccessVersion() {
		return visualAccessVersion;
	}
	public void setVisualAccessVersion(String visualAccessVersion) {
		this.visualAccessVersion = visualAccessVersion;
	}
	@Column(length=20)
	public String getVisualKernelVersion() {
		return visualKernelVersion;
	}
	public void setVisualKernelVersion(String visualKernelVersion) {
		this.visualKernelVersion = visualKernelVersion;
	}
	public String getAccessHardwareVersion() {
		return accessHardwareVersion;
	}
	public void setAccessHardwareVersion(String accessHardwareVersion) {
		this.accessHardwareVersion = accessHardwareVersion;
	}
	@Column(length=20)
	public String getVisualAccessHardwareVersion() {
		return visualAccessHardwareVersion;
	}
	public void setVisualAccessHardwareVersion(String visualAccessHardwareVersion) {
		this.visualAccessHardwareVersion = visualAccessHardwareVersion;
	}
	public CustomerType getCustomerType() {
		return customerType;
	}
	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}
	public String getIccid() {
		return iccid;
	}
	public void setIccid(String iccid) {
		this.iccid = iccid;
	}
	public String getImsi() {
		return imsi;
	}
	public void setImsi(String imsi) {
		this.imsi = imsi;
	}
	public NetStatus getNetStatus() {
		return netStatus;
	}
	public void setNetStatus(NetStatus netStatus) {
		this.netStatus = netStatus;
	}
	public StopStatus getStopStatus() {
		return stopStatus;
	}
	public void setStopStatus(StopStatus stopStatus) {
		this.stopStatus = stopStatus;
	}
	public DeviceStatus getDeviceStatus() {
		return deviceStatus;
	}
	public void setDeviceStatus(DeviceStatus deviceStatus) {
		this.deviceStatus = deviceStatus;
	}
	@Column(length=32)
	public String getProductVersion() {
		return productVersion;
	}
	public void setProductVersion(String productVersion) {
		this.productVersion = productVersion;
	}
	@Column(length=20)
	public String getDeviceSoftwareVersion() {
		return deviceSoftwareVersion;
	}

	public void setDeviceSoftwareVersion(String deviceSoftwareVersion) {
		this.deviceSoftwareVersion = deviceSoftwareVersion;
	}
	@Column(length=20)
	public String getDeviceBluetoothVersion() {
		return deviceBluetoothVersion;
	}

	public void setDeviceBluetoothVersion(String deviceBluetoothVersion) {
		this.deviceBluetoothVersion = deviceBluetoothVersion;
	}
	@Column(length=20)
	public String getDevicePcbVersion() {
		return devicePcbVersion;
	}

	public void setDevicePcbVersion(String devicePcbVersion) {
		this.devicePcbVersion = devicePcbVersion;
	}
}
