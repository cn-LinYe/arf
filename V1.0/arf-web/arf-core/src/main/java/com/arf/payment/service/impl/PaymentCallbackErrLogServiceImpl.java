package com.arf.payment.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.payment.dao.IPaymentCallbackErrLogDao;
import com.arf.payment.entity.PaymentCallbackErrLog;
import com.arf.payment.service.IPaymentCallbackErrLogService;

@Service("paymentCallbackErrLogService")
public class PaymentCallbackErrLogServiceImpl extends BaseServiceImpl<PaymentCallbackErrLog, Long> implements IPaymentCallbackErrLogService {

	@Resource(name="paymentCallbackErrLogDao")
	private IPaymentCallbackErrLogDao paymentCallbackErrLogDao;
	
	@Override
	protected BaseDao<PaymentCallbackErrLog, Long> getBaseDao() {
		return paymentCallbackErrLogDao;
	}

}
