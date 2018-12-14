package com.arf.silverleopard.serivce;

import com.arf.core.service.BaseService;
import com.arf.silverleopard.entity.PaymentMethods;

public interface PaymentMethodsService extends BaseService<PaymentMethods, Long>{

	int findCount(String appId,String code);
}
