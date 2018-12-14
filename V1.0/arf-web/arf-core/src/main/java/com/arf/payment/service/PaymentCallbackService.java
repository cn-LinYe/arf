package com.arf.payment.service;

import java.math.BigDecimal;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.arf.core.AppMessage;
import com.arf.payment.AliPaymentCallBackData;
import com.arf.payment.WeixinPaymentCallBackData;

/**
 * 统一支付回调接口
 * @author Caolibao
 * 2016年7月25日 上午11:59:40
 *
 */
public interface PaymentCallbackService extends ApplicationListener<ContextRefreshedEvent>{
	/**
	 * 微信支付异步通知回调业务逻辑接口
	 * @param callbackData
	 * @return
	 */
	boolean weixiCallback(WeixinPaymentCallBackData callbackData);
	
	/**
	 * 支付宝支付异步通知回调业务逻辑接口
	 * @param callbackData
	 * @return
	 */
	boolean alipayCallback(AliPaymentCallBackData callbackData);

	/**
	 * 电子钱包支付业务逻辑
	 * 内部业务实现步骤为:1.电子钱包余额扣费;2.订单状态更新
	 * 如果成功则AppMessage.code返回com.arf.core.AppMessageCode.CODE_SUCCESS=200
	 * @param outTradeNo
	 * @param totalFee
	 * @return 
	 */
	AppMessage walletAccountCallback(String outTradeNo,BigDecimal totalFee);
}
