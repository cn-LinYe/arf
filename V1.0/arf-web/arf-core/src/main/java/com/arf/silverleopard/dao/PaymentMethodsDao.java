package com.arf.silverleopard.dao;

import com.arf.core.dao.BaseDao;
import com.arf.silverleopard.entity.PaymentMethods;

public interface PaymentMethodsDao extends BaseDao<PaymentMethods, Long>{

	int findCount(String appId,String code);
}
