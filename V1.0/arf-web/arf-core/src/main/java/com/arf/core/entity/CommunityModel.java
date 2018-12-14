package com.arf.core.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * 小区表
 * @author Administrator
 *
 */
@Entity
@Table(name = "community",
indexes={@Index(name="index_comunity_number",columnList="community_number"),
		@Index(name="index_community_property_number",columnList="property_number")},
uniqueConstraints={@UniqueConstraint(columnNames={"community_number"},name="unique_community_number")})
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "community_sequence")
public class CommunityModel extends BaseEntity<Long>{

    private static final long serialVersionUID = 4551995615533345660L;
    
    /**
     * 默认安心点授权有效期:10分钟 add by Caolibao 2016-07-18 10:01
     * {@link com.arf.core.entity.CommunityModel.axdAliveTime}
     */
    @Deprecated
    public static final int Default_AxdAliveTime = 24 * 60; 
    
    /**
     * 小区在线KEY
     */
    public static final String Arfweb_Community_Online = "Arfweb_Community_Online";
    
    /**
     * 缓存中的安心点小区(但com.arf.core.entity.CommunityModel.isAxd暂时为0)
     */
    public static final String Key_Will_Be_AXD_COMMUNITY = "Will_Be_AXD_COMMUNITY"; 
    
    /**
     * 小区在线缓存时长 65秒
     */
    public static final long Arfweb_Community_Online_Expiration = 65;
    
    public static final String ECCCONFIG = "ECCCONFIG";//redis配置参数父节点
    public static final String ECC_OFFLINE_SENDSMS = "ECC_OFFLINE_SENDSMS";//小区离线后，发送短信通知的手机号（全局手机号，redisdb0的sms_notify_user不存在时取此值）
    public static final String ECC_LANPENG_DEVICE_REPORT_INTERVAL = "ECC_LANPENG_DEVICE_REPORT_INTERVAL";//蓝鹏设备汇报间隔参数化
    
    /**
     * 小区离线短信已发送缓存KEY
     */
    public static final String Arfweb_Community_Offline_Phone = "Arfweb_Community_Offline_Phone:";
    public static final long Arfweb_Community_Offline_Phone_Interval = 60 * 60 * 1000L;//发短信时间间隔毫秒，1小时
    public static final long Arfweb_Community_Offline_Phone_Expiration = 12 * 60 * 60L;//缓存时长秒，12小时
    public static final int Arfweb_Community_Offline_Phone_NotifyTimes = 6;//12小时内允许发6次
    
    //	private int id;
	private String communityName;// 小区名
	private String community_number;// 唯一标识id
	private int property_number;// 物业表外键
	private int isRed=0;//是否开启发送红包, 0:不开启,1:开启
	/**
	 * 摄像机是否在线 0不在线1在线
	 */
	private Integer connectFlag=0;
	/**
	 * 是否开启安心点 0:不开启1：开启
	 */
	private Integer isAxd=0;
	private Integer totalCount=0;//小区设置的总数
	/**
	 * 小区负责人电话
	 */
	private String phone;
	/**
	 * 执行方法
	 * 0：默认执行JNI
	 * 1：socket
	 * 2：可以改成执行蓝鹏
	 * 3：安快
	 */
	private Integer executeMethod=0;
	private String communityAddress = "";//地址
	
	private String description;// 小区描述
	private Integer uploadCount=0;//上传后开通vip总数
	private int amount_type = 350;// 规定交钱数额     
	private int no;// 省市区外键
	private Integer provinceno = 0;
	private Integer cityno     = 0;
	private Integer areano     = 0;
	/**
	 * 一天一共发短信次数
	 */
	private Integer sendAllMessages=0;
	/**
	 * 一天已经发短信次数
	 */
	private Integer sendOverMessages=0;
	/**
	 * 发短信时间
	 */
	private Date sendMessageTime;
	/**
	 * 遍历几次才发短信
	 */
	private Integer ergoticTimes=0;
	/**
	 * 是否开通发短信功能 1:开通 0不开通
	 */
	private Integer isSendMessage=0;
	/**
	 * <del>发短信内容</del>
	 * 小区联系人
	 */
	private String contanct;
    /**
     * 子公司编号
     */
    private Integer branchId;
    /**
     * 安心点授权有效期 单位:分钟  add by Caolibao 2016-07-18 10:01
     */
    private Integer axdAliveTime;
    /**
     * 类型
     */
    private ParkingType parkingType = ParkingType.Community;
    
    private byte disableTmpParkingFeeAgr;//临时停车费 在线缴费协议开通字段，默认为0,0开通协议 1关闭协议
    private byte disableMonthyParkingFeeAgr;//月租车续费 在线缴费协议开通字段，默认为0,0开通协议 1关闭协议
    private byte disablePropertyFeeAgr;//物业费 在线缴费协议开通字段，默认为0,0开通协议 1关闭协议
    
    private String propertyOfficePhone;//物业管理处电话
	private Double lat;// 纬度
	private Double lng;// 经度
	private AuthorizationMode authorizationMode;//车辆授权模式0禁止自由出入模式1自由出入模式
	private Date changeDate;//车辆授权模式更改时间
	/**
     *是否开启临时停车, 默认0:不开启,1:开启 {@link com.arf.core.entity.CommunityModel.IsTemporaryParking}
     */
	private Integer isTemporaryParking;
	
	/**
     * 是否接受审核短信 默认0:不开启,1:开启
     */
	private Integer isPushProperty;
	
	private String propertyAuditPhone;//物业审核电话
	
	/**
     *是否支持积分抵扣, 0:支持,默认1:不支持 {@link com.arf.core.entity.CommunityModel.IsPointsDeduction}
     */
	private Integer isPointsDeduction;
	
	public CommunityModel() {
		super();
	}

//	public CommunityModel(int id, String communityName, String description, String community_number, int no,
//			int property_number) {
//		super();
//		this.id = id;
//		this.communityName = communityName;
//		this.description = description;
//		this.community_number = community_number;
//		this.no = no;
//		this.property_number = property_number;
//	}

	public CommunityModel(String communityName, String description, String community_number, int no,
			 int property_number, int amount_type) {
		super();
		this.communityName = communityName;
		this.description = description;
		this.community_number = community_number;
		this.no = no;
		this.property_number = property_number;
		this.amount_type = amount_type;
	}

//	@Id
//	@Column(name = "id")
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	public int getId() {
//		return id;
//	}
//
//	public void setId(int id) {
//		this.id = id;
//	}
    
    public enum ParkingType{
    	/**
    	 * 小区
    	 */
		Community,
		/**
		 * 停车场,如果是此类型则ParkingInfo表也会有该community_Number对应的的停车场记录
		 */
		Parking
		;
	}
    
    public enum AuthorizationMode{
    	ForbidFreeComeAndOut,//禁止自由出入模式
    	FreeComeAndOut;//自由出入模式
    	
    }
  //是否开启临时停车
    public enum IsTemporaryParking{
    	NotOpen,//不开启
    	Open;//开启
    	
    }
  //是否支持积分抵扣
    public enum IsPointsDeduction{
    	Support,//支持
    	NotSupport;//不支持
    }
    
	@Column(name = "executeMethod",nullable =false)
	public Integer getExecuteMethod() {
		return executeMethod;
	}
	public void setExecuteMethod(Integer executeMethod) {
		this.executeMethod = executeMethod;
	}
	@Column(name = "contanct")
	public String getContanct() {
		return contanct;
	}
	public void setContanct(String contanct) {
		this.contanct = contanct;
	}
	@Column(name = "sendAllMessages")
	public Integer getSendAllMessages() {
		return sendAllMessages;
	}
	public void setSendAllMessages(Integer sendAllMessages) {
		this.sendAllMessages = sendAllMessages;
	}
	
	@Column(name = "phone")
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Column(name = "sendOverMessages")
	public Integer getSendOverMessages() {
		return sendOverMessages;
	}
	public void setSendOverMessages(Integer sendOverMessages) {
		this.sendOverMessages = sendOverMessages;
	}
	@Column(name = "sendMessageTime")
	public Date getSendMessageTime() {
		return sendMessageTime;
	}
	public void setSendMessageTime(Date sendMessageTime) {
		this.sendMessageTime = sendMessageTime;
	}
	@Column(name = "ergoticTimes")
	public Integer getErgoticTimes() {
		return ergoticTimes;
	}
	public void setErgoticTimes(Integer ergoticTimes) {
		this.ergoticTimes = ergoticTimes;
	}
	@Column(name = "isSendMessage")
	public Integer getIsSendMessage() {
		return isSendMessage;
	}
	public void setIsSendMessage(Integer isSendMessage) {
		this.isSendMessage = isSendMessage;
	}
	
	@Column(name = "connectFlag")
	public Integer getConnectFlag() {
		return connectFlag;
	}
	public void setConnectFlag(Integer connectFlag) {
		this.connectFlag = connectFlag;
	}
	@Column(name = "uploadCount")
	public Integer getUploadCount() {
		return uploadCount;
	}

	public void setUploadCount(Integer uploadCount) {
		this.uploadCount = uploadCount;
	}

	@Column(name = "totalCount")
	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	@Column(name = "description", length = 255)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "community_number", length = 32, nullable = false)
	public String getCommunity_number() {
		return community_number;
	}

	public void setCommunity_number(String community_number) {
		this.community_number = community_number;
	}

	@Column(name = "no", nullable = false)//int 不用设置length
	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	@Column(name = "property_number", length = 64)
	public int getProperty_number() {
		return property_number;
	}

	public void setProperty_number(int property_number) {
		this.property_number = property_number;
	}

	@Column(name = "communityName", length = 32)
	public String getCommunityName() {
		return communityName;
	}

	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}

	//交钱金额限定
	@Column(name = "amount_type", length = 16, nullable = false)
	public int getAmount_type() {
		return amount_type;
	}
	
	public void setAmount_type(int amount_type) {
		this.amount_type = amount_type;
	}
	
	@Column(name = "provinceno", nullable = true)
	public Integer getProvinceno() {
		return provinceno;
	}

	public void setProvinceno(Integer provinceno) {
		this.provinceno = provinceno;
	}

	@Column(name = "cityno", nullable = true)
	public Integer getCityno() {
		return cityno;
	}

	public void setCityno(Integer cityno) {
		this.cityno = cityno;
	}

	@Column(name = "areano", nullable = true)
	public Integer getAreano() {
		return areano;
	}

	public void setAreano(Integer areano) {
		this.areano = areano;
	}

	public String getCommunityAddress() {
		return communityAddress;
	}

	@Column(name = "communityAddress",length = 128, nullable = true)
	public void setCommunityAddress(String communityAddress) {
		this.communityAddress = communityAddress;
	}
	
	@Column(name = "isRed")
	public int getIsRed() {
		return isRed;
	}
	public void setIsRed(int isRed) {
		this.isRed = isRed;
	}
	@Column(name = "isAxd")
	public Integer getIsAxd() {
		return isAxd;
	}
	public void setIsAxd(Integer isAxd) {
		this.isAxd = isAxd;
	}
	public Integer getBranchId() {
		return branchId;
	}
	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}
	
	/**
	 * 安心点授权有效期 单位:分钟  add by Caolibao 2016-07-18 10:01
	 * @return
	 */
	public Integer getAxdAliveTime() {
		return axdAliveTime;
	}
	public void setAxdAliveTime(Integer axdAliveTime) {
		this.axdAliveTime = axdAliveTime;
	}
	public ParkingType getParkingType() {
		return parkingType;
	}
	public void setParkingType(ParkingType parkingType) {
		this.parkingType = parkingType;
	}
	
	@Column(nullable = false)
	public byte getDisableTmpParkingFeeAgr() {
		return disableTmpParkingFeeAgr;
	}
	@Column(nullable = false)
	public byte getDisableMonthyParkingFeeAgr() {
		return disableMonthyParkingFeeAgr;
	}
	@Column(nullable = false)
	public byte getDisablePropertyFeeAgr() {
		return disablePropertyFeeAgr;
	}
	@Column(length = 20)
	public String getPropertyOfficePhone() {
		return propertyOfficePhone;
	}
	public Double getLat() {
		return lat;
	}
	public Double getLng() {
		return lng;
	}
	public void setDisableTmpParkingFeeAgr(byte disableTmpParkingFeeAgr) {
		this.disableTmpParkingFeeAgr = disableTmpParkingFeeAgr;
	}
	public void setDisableMonthyParkingFeeAgr(byte disableMonthyParkingFeeAgr) {
		this.disableMonthyParkingFeeAgr = disableMonthyParkingFeeAgr;
	}
	public void setDisablePropertyFeeAgr(byte disablePropertyFeeAgr) {
		this.disablePropertyFeeAgr = disablePropertyFeeAgr;
	}
	public void setPropertyOfficePhone(String propertyOfficePhone) {
		this.propertyOfficePhone = propertyOfficePhone;
	}
	public void setLat(Double lat) {
		this.lat = lat;
	}
	public void setLng(Double lng) {
		this.lng = lng;
	}

	public AuthorizationMode getAuthorizationMode() {
		return authorizationMode;
	}

	public void setAuthorizationMode(AuthorizationMode authorizationMode) {
		this.authorizationMode = authorizationMode;
	}

	public Date getChangeDate() {
		return changeDate;
	}

	public void setChangeDate(Date changeDate) {
		this.changeDate = changeDate;
	}

	public Integer getIsTemporaryParking() {
		return isTemporaryParking;
	}

	public void setIsTemporaryParking(Integer isTemporaryParking) {
		this.isTemporaryParking = isTemporaryParking;
	}

	public Integer getIsPointsDeduction() {
		return isPointsDeduction;
	}

	public void setIsPointsDeduction(Integer isPointsDeduction) {
		this.isPointsDeduction = isPointsDeduction;
	}

	public Integer getIsPushProperty() {
		return isPushProperty;
	}

	public void setIsPushProperty(Integer isPushProperty) {
		this.isPushProperty = isPushProperty;
	}

	public String getPropertyAuditPhone() {
		return propertyAuditPhone;
	}

	public void setPropertyAuditPhone(String propertyAuditPhone) {
		this.propertyAuditPhone = propertyAuditPhone;
	}

	/**
	 *	小区业务类型
	 *	PARKRATE 白名单
	 *	PROPERTY 房屋门禁
	 */
	public enum BusinessType{
		PARKRATE,
		PROPERTY;
	}
	
}
