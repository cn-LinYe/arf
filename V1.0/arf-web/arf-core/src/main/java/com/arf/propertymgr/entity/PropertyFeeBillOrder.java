package com.arf.propertymgr.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.arf.core.entity.BaseEntity;
import com.arf.propertymgr.entity.PropertyFeeBill.PayType;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "pty_property_fee_bill_order",
		uniqueConstraints = {
				@UniqueConstraint(columnNames = { "outTradeNo" }, name = "unique_out_trade_no") 
		})
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "pty_property_fee_bill_order_sequence")
public class PropertyFeeBillOrder extends BaseEntity<Long> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4538620679288227526L;
	
	private String outTradeNo;//订单号
	private String billNos;//英文逗号分隔的账单号
	private BigDecimal totalFee;//订单总费用
	private Date paidDate;//支付完成时间
	private TradeStatus tradeStatus;//订单状态 0:UNSUCCESS 未成功,1:SUCCESS 成功
	private PayType payType;//"支付方式枚举:WEIXIN 1:微信支付 ALIPAY 2:支付宝支付  WALLET_ACCOUNT 3:电子钱包 GOLDCARD 4:停车卡
	private String userName;//订单归属用户
	private String communityNumber;//小区编号
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum TradeStatus{
		UNSUCCESS,
		SUCCESS,
		;
	}
	
	@Column(length = 40,nullable = false)
	public String getOutTradeNo() {
		return outTradeNo;
	}
	@Column(length = 2000,nullable = false)
	public String getBillNos() {
		return billNos;
	}
	@Column(precision = 10, scale = 2,nullable = false)
	public BigDecimal getTotalFee() {
		return totalFee;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	public Date getPaidDate() {
		return paidDate;
	}
	@Column(nullable = false)
	public TradeStatus getTradeStatus() {
		return tradeStatus;
	}
	
	@Column(length = 20)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	public void setBillNos(String billNos) {
		this.billNos = billNos;
	}
	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
	}
	public void setPaidDate(Date paidDate) {
		this.paidDate = paidDate;
	}
	public void setTradeStatus(TradeStatus tradeStatus) {
		this.tradeStatus = tradeStatus;
	}
	public PayType getPayType() {
		return payType;
	}
	public void setPayType(PayType payType) {
		this.payType = payType;
	}
	
	@Column(length = 20)
	public String getCommunityNumber() {
		return communityNumber;
	}
	public void setCommunityNumber(String communityNumber) {
		this.communityNumber = communityNumber;
	}
}
