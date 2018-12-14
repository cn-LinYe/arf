package com.arf.platform.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.OrderEntity;

/**
 * 停车收费记录表
 * @author dg
 * @version 1.0
 */
@Entity
@Table(
		name = "r_stoprecord",
		indexes={
			@Index(name="index_arriveTime",columnList="arrive_time"),
			@Index(name="index_userName",columnList="user_name"),
			@Index(name="index_license",columnList="license"),
			@Index(name="index_parking_number",columnList="parking_number"),
			@Index(name="index_leave_time",columnList="leave_time")
		}
)
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "r_stoprecord_sequence")
public class RStoprecord extends OrderEntity<Long>{

	private static final long serialVersionUID = 1L;
	
	/** 区域id */
	private Integer areaId;
	/***停车场ID/小区主键ID**/
	private Integer parkingId;
	/**
	 * 停车场编码 小区编号(community_number)
	 */
	private String parkingNumber;
	/**停车场名/小区名(community_name)*/
	private String parkingName;
	/** 停车的车位ID*/
	private Integer berthId;
	/**
	 * 车位编号
	 */
	private String berthNo;
	/**
	 * 订单号
	 */
	private String outTradeNo;
	/**
	 * vip订单编号
	 */
	private String vipOrderNo;
	/**
	 * 车牌号码
	 */
	private String license;
	/**
	 * 来车时间
	 */
	private Date arriveTime;
	/**
	 * 走车时间
	 */
	private Date leaveTime;
	/**
	 * 停车时长(秒)
	 */
	private Integer deration;
	/**
	 * 预收,单位元
	 */
	private BigDecimal receivedMoney;
	/**
	 * 应收,单位元
	 */
	private BigDecimal receivablesMoney;
	/**
	 * 停车费用（实收）,单位元
	 */
	private BigDecimal fee;
	/**
	 * 计费方式 0计时(默认),1买时
	 */
	private Integer feeType;
	/**
	 * 停车类型：0月卡车,1临时车,2免费车,3电子账户车 {@link com.arf.platform.entity.PParkingcar.StopType}
	 */
	private Integer stopType;
	/**
	 * 支付方式:0钱包支付，1微信支付，2支付宝支付，3 银行卡, 4现金, 5停车卡, 6钱包-停车卡 {@link com.arf.platform.entity.RStoprecord.PayType}
	 */
	private Integer payType;
	/**
	 * 记录的生成时间
	 */
	private Date createTime;
	/**
	 * 来车图片
	 */
	private String arriveCarImgUrl;
	/**
	 * 走车图片
	 */
	
	private String leaveCarImgUrl;
	/**
	 * 业主电话/用户名
	 */
	private String userName;
	/**
	 * 业主真实姓名
	 */
	private String realName;
	/**
	 * 子公司或物业特有信息子公司唯一标识id
	 */
	private Integer branchId;
	/**
	 * 总公司id
	 */
	private Integer headOfficeId;
	/**
	 * 物业唯一标识id
	 */
	private Integer popertyNumber;
	
	/**
	 * 支付状态0;未支付1;已支付 2;支付失败  {@link com.arf.platform.entity.RStoprecord.FeePayStatus}
	 */
	private Integer feePayStatus;
	
	/**
	 * 扣费方式 :默认0;手动支付1;自动扣费 2;微信公众号3;扫二维码  {@link com.arf.platform.entity.RStoprecord.DeductionsType}
	 */
	private Integer deductionsType;
	
	/**
	 * 代金券
	 */
	private String vouchersNum;
	/**
	 * 使用了多少积分
	 */
	private Integer points;
	
	/**
	 * 积分代金券抵扣金额
	 */
	private BigDecimal deductibleFee;//积分代金券抵扣金额
	
	/**
	 * 判断积分代金券是否返还
	 */
	private Integer isReturn=1;//1未返还0返还
	
	/**
	 * 积分代金券返还时间
	 */
	private Date returnTime;
	
	/**
	 * 网络异常费用
	 */
	private BigDecimal overTimeFee;//网络异常费用
	
	/**
	 * 支付时间
	 */
	private Date paidDate;//支付时间

	
	
	/**
	 * 支付状态0;未支付1;已支付 2;支付失败 
	 * @author Caolibao
	 * 2016年5月27日 上午11:50:17
	 *
	 */
	public enum FeePayStatus{
		Not_Paid,
		Paid,
		Failed_Paid,
		;
	}
	public enum IsReturn{
		Have_Return,NotReturn;
	}
	
	/**
	 * 支付方式:0钱包支付，1微信支付，2支付宝支付，3 银行卡, 4现金, 5停车卡, 6钱包-停车卡
	 * @author Caolibao
	 * 2016年5月27日 下午12:04:59
	 *
	 */
	public enum PayType{
		Member_Account,
		Weixin_Pay,
		Ali_Pay,
		Bank_Card,
		Cash_Pay,
		Gold_Card,
		Member_Account_Gold_Card
		;
	}

	
	@Column(precision=10,scale=2)
	public BigDecimal getDeductibleFee() {
		return deductibleFee;
	}

	public void setDeductibleFee(BigDecimal deductibleFee) {
		this.deductibleFee = deductibleFee;
	}

	@Column(precision=10,scale=2)
	public BigDecimal getOverTimeFee() {
		return overTimeFee;
	}

	public void setOverTimeFee(BigDecimal overTimeFee) {
		this.overTimeFee = overTimeFee;
	}

	public Integer getIsReturn() {
		return isReturn;
	}

	public void setIsReturn(Integer isReturn) {
		this.isReturn = isReturn;
	}

	/**
	 *扣费方式 0;手动支付1;自动扣费 2;微信公众号3;扫二维码
	 * @author 
	 * 2016年12月221日 
	 *
	 */
	public enum DeductionsType{
		Manual_Pay,
		Automatic_Pay,
		WeChat_Public_Number_Pay,
		Scan_QR_Code
		;
	}
	@Column(length =64)
	public String getVouchersNum() {
		return vouchersNum;
	}

	public void setVouchersNum(String vouchersNum) {
		this.vouchersNum = vouchersNum;
	}
	@Column(length =20)
	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	@Column(name = "area_id")
	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	@Column(name = "parking_id")
	public Integer getParkingId() {
		return parkingId;
	}

	public void setParkingId(Integer parkingId) {
		this.parkingId = parkingId;
	}
	@Column(name = "parking_number",length=50)
	public String getParkingNumber() {
		return parkingNumber;
	}

	public void setParkingNumber(String parkingNumber) {
		this.parkingNumber = parkingNumber;
	}

	@Column(name = "parking_name",length =50)
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
	@Column(name = "berth_no",length =20)
	public String getBerthNo() {
		return berthNo;
	}

	public void setBerthNo(String berthNo) {
		this.berthNo = berthNo;
	}
	@Column(name = "out_trade_no",length =32)
	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	
	@Column(name = "vip_order_no",length =32)
	public String getVipOrderNo() {
		return vipOrderNo;
	}

	public void setVipOrderNo(String vipOrderNo) {
		this.vipOrderNo = vipOrderNo;
	}
	@Column(name = "license",length =20)
	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}
	@Column(name = "arrive_time")
	public Date getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
	}
	@Column(name = "leave_time")
	public Date getLeaveTime() {
		return leaveTime;
	}

	public void setLeaveTime(Date leaveTime) {
		this.leaveTime = leaveTime;
	}
	@Column(name = "deration")
	public Integer getDeration() {
		return deration;
	}

	public void setDeration(Integer deration) {
		this.deration = deration;
	}
	@Column(name = "received_money",precision =10,scale=2)
	public BigDecimal getReceivedMoney() {
		return receivedMoney;
	}

	public void setReceivedMoney(BigDecimal receivedMoney) {
		this.receivedMoney = receivedMoney;
	}
	@Column(name = "receivables_money",precision =10,scale=2)
	public BigDecimal getReceivablesMoney() {
		return receivablesMoney;
	}

	public void setReceivablesMoney(BigDecimal receivablesMoney) {
		this.receivablesMoney = receivablesMoney;
	}

	@Column(name = "fee",precision =10,scale=2)
	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	@Column(name = "fee_type")
	public Integer getFeeType() {
		return feeType;
	}

	public void setFeeType(Integer feeType) {
		this.feeType = feeType;
	}
	@Column(name = "stop_type")
	public Integer getStopType() {
		return stopType;
	}

	public void setStopType(Integer stopType) {
		this.stopType = stopType;
	}

	@Column(name = "pay_type")
	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	@Column(name = "create_time")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Column(name = "arrive_car_imgurl",length=200)
	public String getArriveCarImgUrl() {
		return arriveCarImgUrl;
	}

	public void setArriveCarImgUrl(String arriveCarImgUrl) {
		this.arriveCarImgUrl = arriveCarImgUrl;
	}
	@Column(name = "leave_car_imgurl",length=200)
	public String getLeaveCarImgUrl() {
		return leaveCarImgUrl;
	}

	public void setLeaveCarImgUrl(String leaveCarImgUrl) {
		this.leaveCarImgUrl = leaveCarImgUrl;
	}
	@Column(name = "user_name",length=32)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(name = "real_name",length=32)
	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	@Column(name = "branch_id")
	public Integer getBranchId() {
		return branchId;
	}

	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}

	@Column(name = "head_office_id")
	public Integer getHeadOfficeId() {
		return headOfficeId;
	}

	public void setHeadOfficeId(Integer headOfficeId) {
		this.headOfficeId = headOfficeId;
	}
	@Column(name = "poperty_number")
	public Integer getPopertyNumber() {
		return popertyNumber;
	}

	public void setPopertyNumber(Integer popertyNumber) {
		this.popertyNumber = popertyNumber;
	}

	public Integer getFeePayStatus() {
		return feePayStatus;
	}

	public void setFeePayStatus(Integer feePayStatus) {
		this.feePayStatus = feePayStatus;
	}

	public Integer getDeductionsType() {
		return deductionsType;
	}

	public void setDeductionsType(Integer deductionsType) {
		this.deductionsType = deductionsType;
	}

	public Date getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
	}

	@Column(name = "paid_date")
	public Date getPaidDate() {
		return paidDate;
	}

	public void setPaidDate(Date paidDate) {
		this.paidDate = paidDate;
	}

	
}
