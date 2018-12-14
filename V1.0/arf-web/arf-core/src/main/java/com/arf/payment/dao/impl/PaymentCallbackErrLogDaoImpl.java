package com.arf.payment.dao.impl;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.payment.dao.IPaymentCallbackErrLogDao;
import com.arf.payment.entity.PaymentCallbackErrLog;

@Repository("paymentCallbackErrLogDao")
public class PaymentCallbackErrLogDaoImpl extends BaseDaoImpl<PaymentCallbackErrLog, Long> implements IPaymentCallbackErrLogDao{

}
