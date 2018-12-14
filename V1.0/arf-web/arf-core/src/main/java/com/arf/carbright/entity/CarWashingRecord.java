package com.arf.carbright.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.search.annotations.DateBridge;
import org.hibernate.search.annotations.Resolution;

import com.arf.core.entity.BaseEntity;

@Entity
@Table(name = "r_car_washing_record")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "car_washing_record_sequence")
public class CarWashingRecord extends BaseEntity<Long>{
	private static final long serialVersionUID = 1L;
	
	private String orderNo;	// Varchar(40)		订单编号
	private String bluetoothCertificate;	// Varchar(40)		蓝牙箱凭证码
	private String phoneMac;	// Varchar(40)		手机MAC
	private String parkingId;	// Varchar(50)		要预定停车/预约洗车的停车场ID/小区编号(community_number)
	private String parkingName;	// Varchar(50)		要预定停车/预约洗车的停车场名/小区名
	private Integer berthId;	// int	可空	预定停车的车位ID
	private String berthNo;	// Varchar(20)	可空	预定停车的车位编号
	private String license;	// Varchar(20)		预定停车/洗车的车牌号码
	private String phone;	// Varchar(20)		预定车位/洗车人的电话号码
	private Integer feePayType;	// tinyint		支付方式 0;代支付，1微信支付，2支付宝支付，3 银行卡 4、现金 5、钱包支付 6、套餐支付
	private Integer feePayStatus;	// tinyint		支付状态0;未支付1;已支付 2;支付失败 
	private Integer status;	// tinyint;		订单状态：0未确认，1 商户已确认，2已取消  3用户已存钥匙  4服务中 5商户确认已完成 6用户确认已完成 7用户已评价 8系统超时取消；
	private BigDecimal fee;	// Decimal(10,2)		预约停车费用/洗车费用，单位元
	private String userName;	// Varchar(20)		车主登录账号
	private Date bookTime;// 	Datetime		预定开始时间
	private Date arriveTime;	// Datetime		车辆到达时间
	private Date leaveTime;	// Datetime		预定离开时间
	private Integer isPayAgent;	// tinyint		是否代付： 0否 1 是 默认0；
	private Integer orderType;	// tinyint		订单类型：0、预约停车 1、点滴洗-洗外观 2、点滴洗-洗外观+内饰 3、自助洗车机
	private String entrance;	// Varchar(20)		入口名称
	private String invoiceContent;	// Varchar(255) 		发票内容
	private String invoiceTitle;	// Varchar(255) 		发票标题
	private String thumbnail;	// Varchar(512)		缩略图
	private String orderPrePic;	// Varchar(512) 		洗车前照片
	private String orderAfterPic;	// Varchar(512)		洗车后照片
	private Integer branchId;	// int		子公司或物业特有信息子公司唯一标识id
	private Integer headOfficeId;	// int		总公司id
	private Integer popertyNumber;	// int		物业唯一标识id
	private String cabinetNum;	// String		柜子唯一标识Number
	private String boxNum;	// String		柜子里的小箱子唯一标识Number
	private Long packageRecordId;//套餐记录Id(如果是套餐支付的话)
	private Long businessId;//接单商户主键Id	
	private String address;//用户车辆停放地址
	private String message;//用户留言	
	private int currentTimes;//当前使用的是套餐的第几次	
	private String cabinetAddress;	//Varchar(50)		柜子安装地址	
	private String locationImage;//位置图片	
	private Date serviceDate;//服务时间	
	private Date completeDate;//完成时间	
	private Date cancelDate;//取消时间
	private Double startMileage;//起始里程
	private Double endMileage;//结束里程
	private Integer whetherKey;//是否存钥匙 0 存钥匙 1不存钥匙
	
	/**
	 * 订单状态
	 * @author Caolibao
	 * 2016年5月19日 下午4:14:46
	 */
	public enum Status{
		Not_Confirm("未确认"), //未确认
		Confirmed("已确认"), //已确认
		Canceled("已取消"), //已取消
		User_stored_Key("已存钥匙"),//用户已存钥匙
		Servicing("服务中"),//服务中
		Merchant_Completed("商户已完成"), //商户确认已完成
		Member_Completed("用户已完成"), //用户确认已完成
		Member_Evaluate("用户已评价"), //用户已评价
		System_Canceled("系统超时取消"); //系统超时取消
		
		private String description;
		private Status(String description){
			this.description = description;
		}
		
		public String description(){
			return description;
		}
		
		public static Status get(int ordinal){
			Status statuss[] = Status.values();
			if(ordinal > statuss.length - 1){
				return null;
			}else{
				return statuss[ordinal];
			}
		}
	}
	
	/**
	 * 支付状态
	 * @author Caolibao
	 * 2016年5月20日 下午5:48:34
	 *
	 */
	public enum PaidStatus{ //0;未支付1;已支付 2;支付失败 
		Not_Paid,
		Paid,
		Paid_Failed,
		;
	}
	
	/**
	 * 费用支付方式
	 * 支付方式 0;代支付，1微信支付，2支付宝支付，3 银行卡 4、现金 5、钱包支付 6、套餐支付
	 * @author Caolibao
	 * 2016年5月23日 下午4:25:55
	 *
	 */
	public enum FeePayType{
		Paid_Agent, //代支付
		Paid_WeiXin, //微信支付
		Paid_Alipay, //支付宝支付
		Paid_UnionPay, //银行卡
		Paid_Cash, //现金
		Paid_Account, //钱包支付
		Paid_Package //套餐支付
		;
		
		public static FeePayType get(int ordinal){
			FeePayType types[] = FeePayType.values();
			if(ordinal > types.length - 1){
				return null;
			}else{
				return types[ordinal];
			}
		}
	}
	
	/**
	 * 订单类型  0、预约停车 1、点滴洗-洗外观 2、点滴洗-洗外观+内饰3、自助洗车机
	 * @author Caolibao
	 * 2016年5月23日 下午4:41:24
	 *
	 */
	public enum OrderType{
		Booking_Parking, //预约停车
		Carbrighter_Appearance, //点滴洗-洗外观 
		Carbrighter_Appearance_Interior, //点滴洗-洗外观+内饰
		SelfService_Washing,//自助洗车机
	}
	

	/**
	 * 是否存钥匙 0 存钥匙 1不存钥匙
	 *
	 */
	public enum WhetherKey{
		Deposit, //存钥匙
		NotDeposit, //不存钥匙
	}
	
	@Column(length=512)
	public String getLocationImage() {
		return locationImage;
	}

	public void setLocationImage(String locationImage) {
		this.locationImage = locationImage;
	}

	@Column(length=40)
	public String getOrderNo() {
		return orderNo;
	}
	
	@Column(length=40)
	public String getBluetoothCertificate() {
		return bluetoothCertificate;
	}
	
	@Column(length=40)
	public String getPhoneMac() {
		return phoneMac;
	}
	
	@Column(length=50)
	public String getParkingId() {
		return parkingId;
	}
	@Column(length=50)
	public String getParkingName() {
		return parkingName;
	}
	
	public Integer getBerthId() {
		return berthId;
	}
	
	@Column(length=20)
	public String getBerthNo() {
		return berthNo;
	}
	
	@Column(length=20)
	public String getLicense() {
		return license;
	}
	
	@Column(length=20)
	public String getPhone() {
		return phone;
	}
	
	public Integer getFeePayType() {
		return feePayType;
	}
	
	public Integer getFeePayStatus() {
		return feePayStatus;
	}
	
	public Integer getStatus() {
		return status;
	}
	
	@Column(precision=10,scale=2)
	public BigDecimal getFee() {
		return fee;
	}
	
	@Column(length=20)
	public String getUserName() {
		return userName;
	}
	@Column(length=20)
	public String getCabinetNum() {
		return cabinetNum;
	}

	public void setCabinetNum(String cabinetNum) {
		this.cabinetNum = cabinetNum;
	}
	
	@DateBridge(resolution = Resolution.SECOND)
	public Date getBookTime() {
		return bookTime;
	}
	
	@DateBridge(resolution = Resolution.SECOND)
	public Date getArriveTime() {
		return arriveTime;
	}
	@DateBridge(resolution = Resolution.SECOND)
	public Date getLeaveTime() {
		return leaveTime;
	}
	
	public Integer getIsPayAgent() {
		return isPayAgent;
	}
	
	public Integer getOrderType() {
		return orderType;
	}
	
	@Column(length=20)
	public String getEntrance() {
		return entrance;
	}
	
	@Column(length=255)
	public String getInvoiceContent() {
		return invoiceContent;
	}
	
	@Column(length=255)
	public String getInvoiceTitle() {
		return invoiceTitle;
	}
	
	@Column(length=512)
	public String getThumbnail() {
		return thumbnail;
	}
	
	@Column(length=512)
	public String getOrderPrePic() {
		return orderPrePic;
	}
	
	@Column(length=512)
	public String getOrderAfterPic() {
		return orderAfterPic;
	}
	
	public Integer getBranchId() {
		return branchId;
	}
	public Integer getHeadOfficeId() {
		return headOfficeId;
	}
	public Integer getPopertyNumber() {
		return popertyNumber;
	}
	@Column(length=20)
	public String getBoxNum() {
		return boxNum;
	}
	@Column(nullable=true)
	public Long getPackageRecordId() {
		return packageRecordId;
	}
	
	@Column(nullable=true)
	public Long getBusinessId() {
		return businessId;
	}
	@Column(nullable=true,length = 255)
	public String getAddress() {
		return address;
	}
	@Column(nullable=true,length = 512)
	public String getMessage() {
		return message;
	}
	public int getCurrentTimes() {
		return currentTimes;
	}
	@Column(length = 50)
	public String getCabinetAddress() {
		return cabinetAddress;
	}
	public void setCabinetAddress(String cabinetAddress) {
		this.cabinetAddress = cabinetAddress;
	}
	
	public void setPackageRecordId(Long packageRecordId) {
		this.packageRecordId = packageRecordId;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public void setBluetoothCertificate(String bluetoothCertificate) {
		this.bluetoothCertificate = bluetoothCertificate;
	}
	public void setPhoneMac(String phoneMac) {
		this.phoneMac = phoneMac;
	}
	public void setParkingId(String parkingId) {
		this.parkingId = parkingId;
	}
	public void setParkingName(String parkingName) {
		this.parkingName = parkingName;
	}
	public void setBerthId(Integer berthId) {
		this.berthId = berthId;
	}
	public void setBerthNo(String berthNo) {
		this.berthNo = berthNo;
	}
	public void setLicense(String license) {
		this.license = license;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setFeePayType(Integer feePayType) {
		this.feePayType = feePayType;
	}
	public void setFeePayStatus(Integer feePayStatus) {
		this.feePayStatus = feePayStatus;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setBookTime(Date bookTime) {
		this.bookTime = bookTime;
	}
	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
	}
	public void setLeaveTime(Date leaveTime) {
		this.leaveTime = leaveTime;
	}
	public void setIsPayAgent(Integer isPayAgent) {
		this.isPayAgent = isPayAgent;
	}
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	public void setEntrance(String entrance) {
		this.entrance = entrance;
	}
	public void setInvoiceContent(String invoiceContent) {
		this.invoiceContent = invoiceContent;
	}
	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public void setOrderPrePic(String orderPrePic) {
		this.orderPrePic = orderPrePic;
	}
	public void setOrderAfterPic(String orderAfterPic) {
		this.orderAfterPic = orderAfterPic;
	}
	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}
	public void setHeadOfficeId(Integer headOfficeId) {
		this.headOfficeId = headOfficeId;
	}
	public void setPopertyNumber(Integer popertyNumber) {
		this.popertyNumber = popertyNumber;
	}

	public void setBoxNum(String boxNum) {
		this.boxNum = boxNum;
	}


	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setCurrentTimes(int currentTimes) {
		this.currentTimes = currentTimes;
	}

	public Date getServiceDate() {
		return serviceDate;
	}

	public void setServiceDate(Date serviceDate) {
		this.serviceDate = serviceDate;
	}

	public Date getCompleteDate() {
		return completeDate;
	}

	public void setCompleteDate(Date completeDate) {
		this.completeDate = completeDate;
	}

	public Date getCancelDate() {
		return cancelDate;
	}

	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
	}

	public Double getStartMileage() {
		return startMileage;
	}

	public void setStartMileage(Double startMileage) {
		this.startMileage = startMileage;
	}

	public Double getEndMileage() {
		return endMileage;
	}

	public void setEndMileage(Double endMileage) {
		this.endMileage = endMileage;
	}

	public Integer getWhetherKey() {
		return whetherKey;
	}

	public void setWhetherKey(Integer whetherKey) {
		this.whetherKey = whetherKey;
	}

	
	
}
	