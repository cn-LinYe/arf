/*
 * Copyright 2015-2016 ta-rf.cn. All rights reserved.
 * Support: http://www.ta-rf.cn
 * License: http://www.ta-rf.cn/license
 */
package com.arf.core.service.impl;

import javax.annotation.Resource;
import javax.persistence.LockModeType;

import com.arf.core.dao.BaseDao;
import com.arf.core.dao.PaymentLogDao;
import com.arf.core.dao.SnDao;
import com.arf.core.entity.DepositLog;
import com.arf.core.entity.Member;
import com.arf.core.entity.Order;
import com.arf.core.entity.Payment;
import com.arf.core.entity.PaymentLog;
import com.arf.core.entity.Sn;
import com.arf.core.service.MemberService;
import com.arf.core.service.OrderService;
import com.arf.core.service.PaymentLogService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * Service - 支付记录
 * 
 * @author arf
 * @version 4.0
 */
@Service("paymentLogServiceImpl")
public class PaymentLogServiceImpl extends BaseServiceImpl<PaymentLog, Long> implements PaymentLogService {

	@Resource(name = "paymentLogDaoImpl")
	private PaymentLogDao paymentLogDao;
	@Resource(name = "snDaoImpl")
	private SnDao snDao;
	@Resource(name = "memberServiceImpl")
	private MemberService memberService;
	@Resource(name = "orderServiceImpl")
	private OrderService orderService;

	@Override
	protected BaseDao<PaymentLog, Long> getBaseDao() {
		return paymentLogDao;
	}

	@Transactional(readOnly = true)
	public PaymentLog findBySn(String sn) {
		return paymentLogDao.findBySn(sn);
	}

	public void handle(PaymentLog paymentLog) {
		paymentLogDao.refresh(paymentLog, LockModeType.PESSIMISTIC_WRITE);

		Assert.notNull(paymentLog);
		Assert.notNull(paymentLog.getType());

		if (!PaymentLog.Status.wait.equals(paymentLog.getStatus())) {
			return;
		}

		switch (paymentLog.getType()) {
		case recharge:
			Member member = paymentLog.getMember();
			if (member != null) {
				memberService.addBalance(member, paymentLog.getEffectiveAmount(), DepositLog.Type.recharge, null, null);
			}
			break;
		case payment:
			Order order = paymentLog.getOrder();
			if (order != null) {
				Payment payment = new Payment();
				payment.setMethod(Payment.Method.online);
				payment.setPaymentMethod(paymentLog.getPaymentPluginName());
				payment.setFee(paymentLog.getFee());
				payment.setAmount(paymentLog.getAmount());
				payment.setOrder(order);
				orderService.payment(order, payment, null);
			}
			break;
		}
		paymentLog.setStatus(PaymentLog.Status.success);
	}

	@Override
	@Transactional
	public PaymentLog save(PaymentLog paymentLog) {
		Assert.notNull(paymentLog);

		paymentLog.setSn(snDao.generate(Sn.Type.paymentLog));

		return super.save(paymentLog);
	}

	public void handleNoPrice(PaymentLog paymentLog) throws Exception {
        paymentLogDao.refresh(paymentLog, LockModeType.PESSIMISTIC_WRITE);

        Assert.notNull(paymentLog);
        Assert.notNull(paymentLog.getType());

        if (!PaymentLog.Status.wait.equals(paymentLog.getStatus())) {
            return;
        }

       
        Order order = paymentLog.getOrder();
        if (order != null) {
            Payment payment = new Payment();
            payment.setMethod(Payment.Method.online);
            payment.setPaymentMethod(paymentLog.getPaymentPluginName());
            payment.setFee(paymentLog.getFee());
            payment.setAmount(paymentLog.getAmount());
            payment.setOrder(order);
            orderService.paymentNoprice(order, payment, null);
        }
       
        paymentLog.setStatus(PaymentLog.Status.success);
    }
}