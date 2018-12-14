package com.arf.silverleopard.serivce.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.silverleopard.dao.PaymentMethodsDao;
import com.arf.silverleopard.dao.SalesReceiptsDao;
import com.arf.silverleopard.entity.PaymentMethods;
import com.arf.silverleopard.entity.SalesReceipts;
import com.arf.silverleopard.serivce.PaymentMethodsService;
import com.arf.silverleopard.serivce.SalesReceiptsService;

@Service("paymentMethodsService")
public class PaymentMethodsServiceImpl extends BaseServiceImpl<PaymentMethods, Long> implements PaymentMethodsService{

	@Resource(name = "paymentMethodsDao")
	private PaymentMethodsDao paymentMethodsDao;
	
	@Override
	protected BaseDao<PaymentMethods, Long> getBaseDao() {
		return paymentMethodsDao;
	}

	@Override
	public int findCount(String appId, String code) {
		return paymentMethodsDao.findCount(appId, code);
	}

}
