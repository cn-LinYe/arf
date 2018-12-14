package com.arf.payment.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 支付结果缓存DTO,用户支付结果查询接口, 缓存时间以{@link com.arf.payment.dto.PaymentResultCacheDto.CACHE_EXPIRATION}为准,缓存key以{@link com.arf.payment.dto.PaymentResultCacheDto.CACHE_KEY}
 * @author Caolibao
 *
 */
public class PaymentResultCacheDto implements Serializable{
	private static final long serialVersionUID = 8359640186236088983L;
	public static final long CACHE_EXPIRATION = 5 * 60;//缓存时间10分钟
	public static final String CACHE_KEY = "ARFWEB.Payment.PaymentResult:";//缓存key
	
	private PaymentResult paymentResult;
	private String notifyDate;//支付结果通知时间,
	private int expensedPint;//200,/** 使用积分 */,
	private int remainedPoint;//1500,/** 剩余积分 */,
	private BigDecimal totalFee;//29.33,/** 支付金额 */,
	private BigDecimal balance;//99.23, /** 账户余额 */,
	private String paidDate;//”206-12-09 12;//23;//45”,/** 支付回调成功的时间 */,
	private int nextLevelPoint;//1800,/** 下一等级需要积分 */,
	private String descritionMsg;//”您本次消费获得了x会员积分,再积累yy分就可以升级了哦!”/** 描述信息 */
	private String warnMessage;//”请在15分钟内驶出停车场”/** 提示信息 */
	private BigDecimal giftVoucher;// 600.00 获得的礼品代金券
	private String validDate;//有效期 如："一个月" 或者 "一年"
	private String voucherNumber;//卡券编码
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	public enum PaymentResult{
		NOT_NOTIFY, //平台还未通知
		NOTIFY_SUCCESS, //通知并处理成功
		NOTIFY_FAILED, //通知但处理失败
		;
	}

	public PaymentResultCacheDto(){
		super();
		this.paymentResult = PaymentResult.NOT_NOTIFY;
	}

	public PaymentResult getPaymentResult() {
		return paymentResult;
	}

	public String getNotifyDate() {
		return notifyDate;
	}

	public int getExpensedPint() {
		return expensedPint;
	}

	public int getRemainedPoint() {
		return remainedPoint;
	}

	public BigDecimal getTotalFee() {
		return totalFee;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public String getPaidDate() {
		return paidDate;
	}

	public int getNextLevelPoint() {
		return nextLevelPoint;
	}

	public String getDescritionMsg() {
		return descritionMsg;
	}

	public BigDecimal getGiftVoucher() {
		return giftVoucher;
	}

	public void setPaymentResult(PaymentResult paymentResult) {
		this.paymentResult = paymentResult;
	}

	public void setNotifyDate(String notifyDate) {
		this.notifyDate = notifyDate;
	}

	public void setExpensedPint(int expensedPint) {
		this.expensedPint = expensedPint;
	}

	public void setRemainedPoint(int remainedPoint) {
		this.remainedPoint = remainedPoint;
	}

	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public void setPaidDate(String paidDate) {
		this.paidDate = paidDate;
	}

	public void setNextLevelPoint(int nextLevelPoint) {
		this.nextLevelPoint = nextLevelPoint;
	}

	public void setDescritionMsg(String descritionMsg) {
		this.descritionMsg = descritionMsg;
	}

	public void setGiftVoucher(BigDecimal giftVoucher) {
		this.giftVoucher = giftVoucher;
	}

	public String getValidDate() {
		return validDate;
	}

	public void setValidDate(String validDate) {
		this.validDate = validDate;
	}

	public String getVoucherNumber() {
		return voucherNumber;
	}

	public void setVoucherNumber(String voucherNumber) {
		this.voucherNumber = voucherNumber;
	}

	public String getWarnMessage() {
		return warnMessage;
	}

	public void setWarnMessage(String warnMessage) {
		this.warnMessage = warnMessage;
	}
	
	
}
