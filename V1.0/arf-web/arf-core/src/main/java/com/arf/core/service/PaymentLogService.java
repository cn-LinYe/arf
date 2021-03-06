/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service;

import com.arf.core.entity.PaymentLog;

/**
 * Service - 支付记录
 * 
 * @author arf
 * @version 4.0
 */
public interface PaymentLogService extends BaseService<PaymentLog, Long> {

	/**
	 * 根据编号查找支付记录
	 * 
	 * @param sn
	 *            编号(忽略大小写)
	 * @return 支付记录，若不存在则返回null
	 */
	PaymentLog findBySn(String sn);

	/**
	 * 支付处理
	 * 
	 * @param paymentLog
	 *            支付记录
	 */
	void handle(PaymentLog paymentLog);
	/**
     * 支付处理
     * 
     * @param paymentLog
     *            支付记录
     */
	void handleNoPrice(PaymentLog paymentLog) throws Exception;

}