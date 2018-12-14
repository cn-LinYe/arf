package com.arf.installment.smartlock.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(
	name = "lock_installment_order_record",
	indexes={
		@Index(name="index_order_no",columnList="orderNo"),
		@Index(name="index_user_name",columnList="userName")
	},
	uniqueConstraints={
		@UniqueConstraint(columnNames={"orderNo"},name="unique_order_no")
	}
)
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "lock_installment_order_record_sequence")
public class LockInstallmentOrderRecord extends BaseEntity<Long> {
	private static final long serialVersionUID = 1L;
	
	public static final String LOCK_INSTALLMENT_CONFIG = "LOCK_INSTALLMENT_CONFIG";
	public static final String BUY_SMARTLOCK_LIMIT = "BUY_SMARTLOCK_LIMIT";//购买次数

	private String userName;// not null 用户名
	private String typeNum;// not null 同lock_installment_type.type_num
	private int installmentPeriods;// not null 分期数,如果是全额购买则为1
	private BigDecimal firstFunds;// not null 首款,如果是全额购买则首款即为全款
	private String userPhone;// not null 用户电话
	private String realName;// not null 用户姓名
	private String cityNo;// not null 城市编码
	private String fullCityName;// not null eg.广东省深圳市
	private String address;// 地址
	/**
	 * 0:NEW新建, 
	 * 1: BUY_SUCCESS 购买能成功 
	 * 2:SENT_OUT已发货 
	 * 3:RECEIVED已收货 
	 * 4:REFUNDING 申请退款/货中 
	 * 5:REFUNDED 退款完成
	 */
	private Status status;// not null
	/**
	 * 还款状态枚举; INSTALLMENT_ING 0 分期中; PAYBACK_CLEAR 1 还款完成; OVER_DUE 2 存在逾期款
	 */
	private FundsClear fundsClear;// not null
	private String orderNo;// not null 唯一 购买编号
	private Date buyDate;// 购买日期,首款支付回调成功时设置该日期
	/**
	 * 支付方式枚举; WEIXIN 0 微信支付; ALIPAY 1 支付宝 ;WALLET_ACCOUNT 2 电子钱包
	 */
	private PayType payType;
	private Date applyRefundDate;// 申请退款时间
	private Date refundFinishDate;// 退款完成时间
	
	private BuyChannel buyChannel;// 购买渠道
	
	/**
	 * 
	 */
	private RefundType refundType;//退款/退货类型枚举
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum RefundType {
		CUSTOMER_CLAIM,//客户发起退货/退款要求
		OVER_DUE_RECLAIM,//款项预期门锁被回收
		;
	}
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum BuyChannel {
		INSTALLMENT, //分期乐
		OPEN_ECC, // 开通ECC
		BANNER_ADV_H5, //banner-广告H5
		;
	}
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum PayType {
		WEIXIN, ALIPAY, WALLET_ACCOUNT
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum Status {
		NEW,          //新建
		BUY_SUCCESS,  //购买成功
		@Deprecated
		SENT_OUT,	 //已发货
		@Deprecated
		RECEIVED,    //已收货
		REFUND_AUDIT, //退款审核中
		REFUNDING,    //退款中(如果审核不通过,状态回退到1)
		REFUNDED,     //退款完成
	}
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum FundsClear {
		INSTALLMENT_ING, PAYBACK_CLEAR, OVER_DUE
	}

	@Column(length=30,nullable=false)
	public String getUserName() {
		return userName;
	}

	@Column(length=40,nullable=false)
	public String getTypeNum() {
		return typeNum;
	}

	@Column(columnDefinition="int(11) NOT NULL DEFAULT 1")
	public int getInstallmentPeriods() {
		return installmentPeriods;
	}

	@Column(precision=10,scale=2)
	public BigDecimal getFirstFunds() {
		return firstFunds;
	}
	
	@Column(length=20,nullable=false)
	public String getUserPhone() {
		return userPhone;
	}
	@Column(length=10,nullable=false)
	public String getRealName() {
		return realName;
	}
	@Column(length=20,nullable=false)
	public String getCityNo() {
		return cityNo;
	}
	@Column(length=50,nullable=false)
	public String getFullCityName() {
		return fullCityName;
	}

	public String getAddress() {
		return address;
	}
	
	public Status getStatus() {
		return status;
	}

	public FundsClear getFundsClear() {
		return fundsClear;
	}
	@Column(length=40,nullable=false,unique=true)
	public String getOrderNo() {
		return orderNo;
	}

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	public Date getBuyDate() {
		return buyDate;
	}

	public PayType getPayType() {
		return payType;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	public Date getApplyRefundDate() {
		return applyRefundDate;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	public Date getRefundFinishDate() {
		return refundFinishDate;
	}
	
	public BuyChannel getBuyChannel() {
		return buyChannel;
	}

	public void setBuyChannel(BuyChannel buyChannel) {
		this.buyChannel = buyChannel;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setTypeNum(String typeNum) {
		this.typeNum = typeNum;
	}

	public void setInstallmentPeriods(int installmentPeriods) {
		this.installmentPeriods = installmentPeriods;
	}

	public void setFirstFunds(BigDecimal firstFunds) {
		this.firstFunds = firstFunds;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public void setCityNo(String cityNo) {
		this.cityNo = cityNo;
	}

	public void setFullCityName(String fullCityName) {
		this.fullCityName = fullCityName;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void setFundsClear(FundsClear fundsClear) {
		this.fundsClear = fundsClear;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public void setBuyDate(Date buyDate) {
		this.buyDate = buyDate;
	}

	public void setPayType(PayType payType) {
		this.payType = payType;
	}

	public void setApplyRefundDate(Date applyRefundDate) {
		this.applyRefundDate = applyRefundDate;
	}

	public void setRefundFinishDate(Date refundFinishDate) {
		this.refundFinishDate = refundFinishDate;
	}

	public RefundType getRefundType() {
		return refundType;
	}

	public void setRefundType(RefundType refundType) {
		this.refundType = refundType;
	}
}