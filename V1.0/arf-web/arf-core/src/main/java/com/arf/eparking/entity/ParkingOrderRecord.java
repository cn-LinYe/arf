package com.arf.eparking.entity;

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
@Table(name = "r_parking_order_record")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "parking_order_record_sequence")
public class ParkingOrderRecord extends BaseEntity<Long>{
	private static final long serialVersionUID = 1L;
	
	private String orderNo;	// Varchar(40)		订单编号
	private String phoneMac;	// Varchar(40)		手机MAC
	private String parkingId;	// Varchar(50)		要预定停车的停车场ID/小区编号
	private String parkingName;	// Varchar(50)		要预定停车的停车场名/小区名
	private Integer berthId;	// int	可空	预定停车的车位ID 可空
	private String berthNo;	// Varchar(20)	可空	预定停车的车位编号 可空
	private String license;	// Varchar(20)		预定停车的车牌号码
	private String phone;	// Varchar(20)		预定车位人的电话号码
	private Integer feePayType;	// tinyint		支付方式 0;代支付，1微信支付，2支付宝支付，3 银行卡 4、现金 5、钱包支付 6、套餐支付
	private Integer feePayStatus;	// tinyint		支付状态0;未支付1;已支付 2;支付失败 
	private Integer status;	// tinyint;		订单状态：0已取消，1超时取消，2等待到达，3已延时，4已完成，5用户已评论；
	private BigDecimal fee;	// Decimal(10,2)		预约停车费用，单位元
	private String userName;	// Varchar(20)		车主登录账号
	private Date bookTime;// 	Datetime		预定开始时间
	private Date arriveTime;	// Datetime		车辆到达时间
	private Date leaveTime;	// Datetime		预定离开时间
	private Integer isPayAgent;	// tinyint		是否代付： 0否 1 是 默认0；
	private String entrance;	// Varchar(20)		入口名称
	private String invoiceContent;	// Varchar(255) 		发票内容
	private String invoiceTitle;	// Varchar(255) 		发票标题
	private String thumbnail;	// Varchar(255)		缩略图
	private Integer branchId;	// int		子公司或物业特有信息子公司唯一标识id
	private Integer headOfficeId;	// int		总公司id
	private Integer popertyNumber;	// int		物业唯一标识id
	private BigDecimal breakContractFee;// Decimal(10,2) 爽约费扣款金额 
	private String blacklistNo;//爽约被拉黑关联的黑名单记录唯一标识
	private Date cancelDate;//取消时间
	
	
	/**
	 * 订单状态
	 * @author Caolibao
	 * 2016年5月19日 下午4:14:46
	 */
	public enum Status{
		Canceled("已取消"), 
		TimeOut_Canceled("超时取消"), 
		Waiting("等待到达"),
		Delayed("已延时"),
		Finished("已完成"),
		Member_Evaluate("用户已评价"); 
		
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
	
	public Date getCancelDate() {
		return cancelDate;
	}

	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
	}

	@Column(length=40)
	public String getOrderNo() {
		return orderNo;
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
	
	@Column(length=255)
	public String getThumbnail() {
		return thumbnail;
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
	@Column(precision=10,scale=2)
	public BigDecimal getBreakContractFee() {
		return breakContractFee;
	}
	@Column(length = 32)
	public String getBlacklistNo() {
		return blacklistNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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
	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}
	public void setHeadOfficeId(Integer headOfficeId) {
		this.headOfficeId = headOfficeId;
	}
	public void setPopertyNumber(Integer popertyNumber) {
		this.popertyNumber = popertyNumber;
	}
	public void setBreakContractFee(BigDecimal breakContractFee) {
		this.breakContractFee = breakContractFee;
	}
	public void setBlacklistNo(String blacklistNo) {
		this.blacklistNo = blacklistNo;
	}
}
	