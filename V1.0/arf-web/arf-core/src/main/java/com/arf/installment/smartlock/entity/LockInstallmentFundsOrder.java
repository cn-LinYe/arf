package com.arf.installment.smartlock.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.arf.core.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="lock_installment_funds_order")
@SequenceGenerator(name="sequenceGenerator",sequenceName="lock_installment_funds_order_sequence")
public class LockInstallmentFundsOrder extends BaseEntity<Long>{
	private static final long serialVersionUID = -321901500117472605L;
	
	private String fundsNos;//款项编号,多个用英文逗号分割
	private LockInstallmentOrderRecord.PayType payType;//支付类型枚举 WEIXIN 0 微信支付;ALIPAY 1 支付宝;WALLET_ACCOUNT 2 电子钱包
	private PaidStatus paidStatus;//支付状态枚举:0: UNPAID未支付,1: PAID已支付
	private Date paidDate;//支付日期
	private String outTradeNo;//支付唯一编号
	private BigDecimal totalFee;//实际支付金额
	private String userName;//用户名
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum PaidStatus{
		UNPAID,PAID;
	}
	
	@Column(nullable = false,length=1000)
	public String getFundsNos() {
		return fundsNos;
	}
	public void setFundsNos(String fundsNos) {
		this.fundsNos = fundsNos;
	}
	@Column(nullable = false)
	public LockInstallmentOrderRecord.PayType getPayType() {
		return payType;
	}
	public void setPayType(LockInstallmentOrderRecord.PayType payType) {
		this.payType = payType;
	}
	public PaidStatus getPaidStatus() {
		return paidStatus;
	}
	public void setPaidStatus(PaidStatus paidStatus) {
		this.paidStatus = paidStatus;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	public Date getPaidDate() {
		return paidDate;
	}
	public void setPaidDate(Date paidDate) {
		this.paidDate = paidDate;
	}
	@Column(nullable=false,unique=true,length=40)
	public String getOutTradeNo() {
		return outTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	@Column(precision=10,scale=2)
	public BigDecimal getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
	}
	@Column(length=20,nullable=false)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
