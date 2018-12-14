package com.arf.gift.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "gift_voucher_info_record",
		uniqueConstraints={@UniqueConstraint(columnNames="voucherNumber")})
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "gift_voucher_info_record_sequence")
public class GiftVoucherInfoRecord extends BaseEntity<Long>{

	public final static String RAMAIN_VOUCHER_TO_POINT_SYS_CONFIG = "RAMAIN_VOUCHER_TO_POINT";
	
	private static final long serialVersionUID = -2730758536788332455L;
	private String voucherName	;//varchar(50)	not null	券名称
	private String voucherNumber	;//varchar(40)	not null	卡券编号
	private Date endDate	;//datetime		有效期截止日期
	private Status status		;//int not null	状态枚举:UNUSED 0 未使用USED 1 已使用 2 已过期
	private BigDecimal amount	;//decimal(12,2)	not null	券面金额
	private String userName	;//varchar(30)	not null	用户名
	private Date usedDate	;//datetime		使用日期
	private BigDecimal remainAmount=new BigDecimal(0)	;//decimal(10,2)	not null 默认0	未使用完的金额
	private Integer remainExchangePoint=0	;//int	not null 默认0	未使用完兑换成的积分
	private GetWay getWay	;//int	not null 默认 0	领取方式枚举EXPRESS 0 快递SCENE 1 现场领取
	private GetChannel getChannel	;//int	not null	获得渠道枚举:ECC_MONTH_PAY 0 ECC月租缴费PROPERTY_PAY 1 物业缴费
	private String communityNumber	;//varchar(20)		来源小区编号
	private String expressAddress	;//varchar(100)		快递地址
	private String expressConsignee	;//varchar(30)		快递收货人
	private String expressPhone	;//varchar(20)		快递电收货电话
	private String expressDeliveryCorp	;//varchar(20)		快递公司
	private String expressDeliveryNumber	;//varchar(20)		快递单号
	private String expressLogisticsInfo	;//varchar(2000)		物流信息
	private ExpressLogisticsStatus expressLogisticsStatus	;//int		物流状态枚举NOT_SHIPPED 0 未发货SHIPPED 1 已发货SIGNED 2 已签收SIGNED_OUT( 第三方接口返回值:4 已签收) 3 已退签 (10 退回)
	//-1 待查询 0 查询异常 1 暂无记录 2 在途中 3 派送中 5 用户拒签 6 疑难件 7 无效单 8 超时单 9 签收失败
	private Date deliveryTime;//发货时间
	private Date signedTime;//签收时间
	private String outTradeNo;//订单编号
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum Status{
		UNUSED,USED,
		OVER_DUE
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum GetWay{
		EXPRESS,SCENE
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum GetChannel{
		ECC_MONTH_PAY,PROPERTY_PAY
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum ExpressLogisticsStatus{
		NOT_SHIPPED,SHIPPED,SIGNED,SIGNED_OUT
	}
	public static Status get(int ordinal){
		Status statuss[] = Status.values();
		if(ordinal > statuss.length - 1){
			return null;
		}else{
			return statuss[ordinal];
		}
	}
	public static ExpressLogisticsStatus getStatus(int ordinal){
		ExpressLogisticsStatus statuss[] = ExpressLogisticsStatus.values();
		if(ordinal > statuss.length - 1){
			return null;
		}else{
			return statuss[ordinal];
		}
	}
	
	@Column(length=30,nullable=false)
	public String getOutTradeNo() {
		return outTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	public Date getDeliveryTime() {
		return deliveryTime;
	}
	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	@Column(length=50,nullable=false)
	public String getVoucherName() {
		return voucherName;
	}
	public void setVoucherName(String voucherName) {
		this.voucherName = voucherName;
	}
	@Column(length=40,nullable=false)
	public String getVoucherNumber() {
		return voucherNumber;
	}
	public void setVoucherNumber(String voucherNumber) {
		this.voucherNumber = voucherNumber;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	@Column(nullable=false)
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	@Column(precision=10,scale=2)
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	@Column(length=30,nullable=false)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getUsedDate() {
		return usedDate;
	}
	public void setUsedDate(Date usedDate) {
		this.usedDate = usedDate;
	}
	@Column(precision=10,scale=2)
	public BigDecimal getRemainAmount() {
		return remainAmount;
	}
	public void setRemainAmount(BigDecimal remainAmount) {
		this.remainAmount = remainAmount;
	}
	@Column(nullable=false)
	public Integer getRemainExchangePoint() {
		return remainExchangePoint;
	}
	public void setRemainExchangePoint(Integer remainExchangePoint) {
		this.remainExchangePoint = remainExchangePoint;
	}
	public GetWay getGetWay() {
		return getWay;
	}
	public void setGetWay(GetWay getWay) {
		this.getWay = getWay;
	}
	@Column(nullable=false)
	public GetChannel getGetChannel() {
		return getChannel;
	}
	public void setGetChannel(GetChannel getChannel) {
		this.getChannel = getChannel;
	}
	@Column(nullable=false)
	public String getCommunityNumber() {
		return communityNumber;
	}
	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
	@Column(length=100)
	public String getExpressAddress() {
		return expressAddress;
	}
	public void setExpressAddress(String expressAddress) {
		this.expressAddress = expressAddress;
	}
	@Column(length=30)
	public String getExpressConsignee() {
		return expressConsignee;
	}
	public void setExpressConsignee(String expressConsignee) {
		this.expressConsignee = expressConsignee;
	}
	@Column(length=20)
	public String getExpressPhone() {
		return expressPhone;
	}
	public void setExpressPhone(String expressPhone) {
		this.expressPhone = expressPhone;
	}
	@Column(length=20)
	public String getExpressDeliveryCorp() {
		return expressDeliveryCorp;
	}
	public void setExpressDeliveryCorp(String expressDeliveryCorp) {
		this.expressDeliveryCorp = expressDeliveryCorp;
	}
	@Column(length=20)
	public String getExpressDeliveryNumber() {
		return expressDeliveryNumber;
	}
	public void setExpressDeliveryNumber(String expressDeliveryNumber) {
		this.expressDeliveryNumber = expressDeliveryNumber;
	}
	@Column(length=2000)
	public String getExpressLogisticsInfo() {
		return expressLogisticsInfo;
	}
	public void setExpressLogisticsInfo(String expressLogisticsInfo) {
		this.expressLogisticsInfo = expressLogisticsInfo;
	}
	public ExpressLogisticsStatus getExpressLogisticsStatus() {
		return expressLogisticsStatus;
	}
	public void setExpressLogisticsStatus(ExpressLogisticsStatus expressLogisticsStatus) {
		this.expressLogisticsStatus = expressLogisticsStatus;
	}
	public Date getSignedTime() {
		return signedTime;
	}
	public void setSignedTime(Date signedTime) {
		this.signedTime = signedTime;
	}
	
	
	
	
}
