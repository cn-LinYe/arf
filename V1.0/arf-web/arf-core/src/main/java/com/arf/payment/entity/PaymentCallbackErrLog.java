package com.arf.payment.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.alibaba.fastjson.JSON;
import com.arf.core.entity.BaseEntity;
import com.arf.payment.AliPaymentCallBackData;
import com.arf.payment.WeixinPaymentCallBackData;

@Entity
@Table(
		name = "payment_callback_err_log"
)
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "payment_callback_err_log_sequence")
public class PaymentCallbackErrLog extends BaseEntity<Long> {
	private static final long serialVersionUID = 2177527762297655864L;

	private String outTradeNo;
	private Channel channel;
	private double totalFee;
	private String errReason;
	private String userName;
	
	/*
	 * 回调参数快照
	 */
	private String snapshoot;

	/**
	 * 支付渠道枚举: 1:微信支付 2:支付宝支付
	 * 
	 * @author Caolibao
	 *
	 */
	public enum Channel {
		UNKNOWN,//未知
		WEIXIN,//微信
		ALIPAY,//支付宝
		WALLET_ACCOUNT,//电子账户
		;
	}
	
	public PaymentCallbackErrLog(){
		super();
	}
	public PaymentCallbackErrLog(String outTradeNo, Channel channel, double totalFee, String errReason,
			String snapshoot) {
		super();
		this.outTradeNo = outTradeNo;
		this.channel = channel;
		this.totalFee = totalFee;
		this.errReason = errReason;
		this.snapshoot = snapshoot;
	}
	
	public PaymentCallbackErrLog(String outTradeNo, Channel channel, double totalFee, String errReason,
			AliPaymentCallBackData callbackData) {
		super();
		this.outTradeNo = outTradeNo;
		this.channel = channel;
		this.totalFee = totalFee;
		this.errReason = errReason;
		if(callbackData != null){
			this.snapshoot = JSON.toJSONString(callbackData);
		}
	}
	public PaymentCallbackErrLog(String outTradeNo, Channel channel, double totalFee, String errReason,
			WeixinPaymentCallBackData callbackData) {
		super();
		this.outTradeNo = outTradeNo;
		this.channel = channel;
		this.totalFee = totalFee;
		this.errReason = errReason;
		if(callbackData != null){
			this.snapshoot = JSON.toJSONString(callbackData);
		}
	}

	@Column(length = 40)
	public String getOutTradeNo() {
		return outTradeNo;
	}

	public Channel getChannel() {
		return channel;
	}

	public double getTotalFee() {
		return totalFee;
	}
	
	public String getErrReason() {
		return errReason;
	}
	@Column(length = 4000)
	public String getSnapshoot() {
		return snapshoot;
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

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public void setTotalFee(double totalFee) {
		this.totalFee = totalFee;
	}

	public void setErrReason(String errReason) {
		this.errReason = errReason;
	}

	public void setSnapshoot(String snapshoot) {
		this.snapshoot = snapshoot;
	}
}
