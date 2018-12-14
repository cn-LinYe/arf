package com.arf.core.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "r_bookrecord")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "r_bookrecord_sequence")
public class RBookrecord extends OrderEntity<Long>{

	
	private static final long serialVersionUID = 1L;

	/**
	 * 订单编号
	 */
	private String orderNo;
	/**
	 * 蓝牙箱凭证码
	 */
	private String bluetoothCertificate;
	/**
	 * 要预定停车/预约洗车的停车场ID/小区id
	 */
	private Integer parkingId;
	/**
	 * 要预定停车/预约洗车的停车场名/小区名
	 */
	private String parkingName;
	/**
	 * 预定停车的车位ID
	 */
	private Integer berthId;
	/**
	 * 预定停车的车位编号
	 */
	private String berthNo;
	/**
	 * 预定停车/洗车的车牌号码
	 */
	private String license;
	/**
	 * 预定车位/洗车人的电话号码
	 */
	private String phone;
	/**
	 * 支付方式 0;代支付，1微信支付，2支付宝支付，3 银行卡 4、现金 5、钱包支付 6、套餐支付
	 */
	private Integer feePayType;
	/**
	 * 支付状态0;未支付1;已支付 2;支付失败 3订单完成
	 */
	private Integer feePayStatus;
	/**
	 * 预约停车费用/洗车费用，单位元
	 */
	private BigDecimal bookFee;
	/**
	 * 车主登录账号
	 */
	private String userName;
	/**
	 * 下单时间
	 */
	private String createTime;
	/**
	 * 预定开始时间
	 */
	private Date bookTime;
	/**
	 * 车辆到达时间
	 */
	private Date arriveTime;
	/**
	 * 预定离开时间
	 */
	private Date leaveTime;
	/**
	 * 订单状态：0已取消，1超时取消，2等待到达，3已延时，4已完成；
	 */
	private Integer status;
	/**
	 * 是否代付：0否 1 是 默认0；
	 */
	private Integer isPayAgent;
	/**
	 * 订单类型：0、预约停车 1、点滴洗-洗外观 2、点滴洗-洗外观+内饰
	 */
	private Integer orderType;
	/**
	 * 入口名称
	 */
	private String entrance;

	@Column(name = "order_no",length=40)
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	@Column(name = "bluetooth_certificate",length=40)
	public String getBluetoothCertificate() {
		return bluetoothCertificate;
	}

	public void setBluetoothCertificate(String bluetoothCertificate) {
		this.bluetoothCertificate = bluetoothCertificate;
	}

	@Column(name = "parking_id")
	public Integer getParkingId() {
		return parkingId;
	}

	public void setParkingId(Integer parkingId) {
		this.parkingId = parkingId;
	}
	@Column(name = "parking_name",length=50)
	public String getParkingName() {
		return parkingName;
	}

	public void setParkingName(String parkingName) {
		this.parkingName = parkingName;
	}
	@Column(name = "berth_id")
	public Integer getBerthId() {
		return berthId;
	}

	public void setBerthId(Integer berthId) {
		this.berthId = berthId;
	}
	@Column(name = "berth_no",length=20)
	public String getBerthNo() {
		return berthNo;
	}

	public void setBerthNo(String berthNo) {
		this.berthNo = berthNo;
	}
	@Column(name = "license",length=20)
	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}
	@Column(name = "phone",length=20)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Column(name = "fee_pay_type")
	public Integer getFeePayType() {
		return feePayType;
	}

	public void setFeePayType(Integer feePayType) {
		this.feePayType = feePayType;
	}
	@Column(name = "fee_pay_status")
	public Integer getFeePayStatus() {
		return feePayStatus;
	}

	public void setFeePayStatus(Integer feePayStatus) {
		this.feePayStatus = feePayStatus;
	}
	@Column(name = "book_fee",precision =10,scale=2)
	public BigDecimal getBookFee() {
		return bookFee;
	}

	public void setBookFee(BigDecimal bookFee) {
		this.bookFee = bookFee;
	}
	@Column(name = "user_name",length=20)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(name = "create_time")
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	@Column(name = "book_time")
	public Date getBookTime() {
		return bookTime;
	}

	public void setBookTime(Date bookTime) {
		this.bookTime = bookTime;
	}
	@Column(name = "arrive_time")
	public Date getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
	}
	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	@Column(name = "is_payagent")
	public Integer getIsPayAgent() {
		return isPayAgent;
	}

	public void setIsPayAgent(Integer isPayAgent) {
		this.isPayAgent = isPayAgent;
	}
	@Column(name = "leave_time")
	public Date getLeaveTime() {
		return leaveTime;
	}

	public void setLeaveTime(Date leaveTime) {
		this.leaveTime = leaveTime;
	}
	@Column(name = "order_type")
	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
//	@Column(name = "entrance",columnDefinition = "varchar(20) DEFAULT NULL COMMENT '入口名称'")
	@Column(name = "entrance",length=20)
	public String getEntrance() {
		return entrance;
	}

	public void setEntrance(String entrance) {
		this.entrance = entrance;
	}

}