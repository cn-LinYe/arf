package com.arf.gas.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 家加油订单表
 */
@Entity
@Table(name = "refuel_gas_order")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "refuel_gas_order_sequence")
public class RefuelGasOrder extends BaseEntity<Long>{

	private static final long serialVersionUID = 1L;
	private String userName;//	varchar(20)	否		普通	账号信息
	private String cardNumber;//	varchar(20)	否		普通	油卡编号
	private Integer businessNum;//	int	否		普通	商户编号
	private Integer gasNum;//	int	否		普通	油站编号
	private String gasOrderNo;//	varchar(20)	否		普通	加油订单编号
	private OrderStatus orderStatus;//	tinyint	否		普通	订单状态（0、待支付 Not_Pay 1、已支付 Pay 2、支付失败 Pay_Failure 3、已删除）
	private Byte gunNum	;//tinyint	否		普通	油枪编号
	private Integer orderAmount;//	int	否		普通	订单金额
	private Integer discountsAmount;//	int	否		普通	抵扣金额
	private PayType payType	;//tinyint	否		普通	支付类型（0、免密支付 Free_Payment 1、密码支付 Payment_Password 2、 指纹支付 Fingerprint_Password ）
	private Byte oliType	;//tinyint	否		普通	油类型（0、#0 1、#92 2、 #95）此类型缓存redis 不做数据库维护
	private Integer oliQuantity;//	double	否		普通	油品数量
	private String licensePlateNumber;//	varchar(20)	否		普通	车牌号码
	private Integer orderPrice;//			普通	油品单价
	private Byte tubingNum;//油罐编号
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum OrderStatus{
		 Not_Pay,Pay,Pay_Failure,Deleted;
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum PayType{
		Free_Payment,Payment_Password ,Fingerprint_Password;
		public static PayType get(int ordinal){
			PayType payType[]= RefuelGasOrder.PayType.values();
			if (ordinal>payType.length-1) {
				return null;
			}else{
				return payType[ordinal];
			}
		}
	}
		
	public Byte getTubingNum() {
		return tubingNum;
	}
	public void setTubingNum(Byte tubingNum) {
		this.tubingNum = tubingNum;
	}
	public Integer getOrderPrice() {
		return orderPrice;
	}
	public void setOrderPrice(Integer orderPrice) {
		this.orderPrice = orderPrice;
	}
	@Column(length=20)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(length=20)
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public Integer getBusinessNum() {
		return businessNum;
	}
	public void setBusinessNum(Integer businessNum) {
		this.businessNum = businessNum;
	}
	public Integer getGasNum() {
		return gasNum;
	}
	public void setGasNum(Integer gasNum) {
		this.gasNum = gasNum;
	}
	@Column(length=30)
	public String getGasOrderNo() {
		return gasOrderNo;
	}
	public void setGasOrderNo(String gasOrderNo) {
		this.gasOrderNo = gasOrderNo;
	}
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Byte getGunNum() {
		return gunNum;
	}
	public void setGunNum(Byte gunNum) {
		this.gunNum = gunNum;
	}
	public Integer getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(Integer orderAmount) {
		this.orderAmount = orderAmount;
	}
	public Integer getDiscountsAmount() {
		return discountsAmount;
	}
	public void setDiscountsAmount(Integer discountsAmount) {
		this.discountsAmount = discountsAmount;
	}
	public PayType getPayType() {
		return payType;
	}
	public void setPayType(PayType payType) {
		this.payType = payType;
	}
	public Byte getOliType() {
		return oliType;
	}
	public void setOliType(Byte oliType) {
		this.oliType = oliType;
	}
	public Integer getOliQuantity() {
		return oliQuantity;
	}
	public void setOliQuantity(Integer oliQuantity) {
		this.oliQuantity = oliQuantity;
	}
	@Column(length=20)
	public String getLicensePlateNumber() {
		return licensePlateNumber;
	}
	public void setLicensePlateNumber(String licensePlateNumber) {
		this.licensePlateNumber = licensePlateNumber;
	}
	
}
