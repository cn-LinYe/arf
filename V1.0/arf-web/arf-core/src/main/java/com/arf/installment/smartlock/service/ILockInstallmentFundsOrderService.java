package com.arf.installment.smartlock.service;

import com.arf.core.service.BaseService;
import com.arf.installment.smartlock.entity.LockInstallmentFundsOrder;

public interface ILockInstallmentFundsOrderService extends BaseService<LockInstallmentFundsOrder, Long>{
	
	LockInstallmentFundsOrder findByOutTradeNo(String outTradeNo);
}
