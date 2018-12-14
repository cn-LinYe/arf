package com.arf.propertymgr.service;

import com.arf.core.service.BaseService;
import com.arf.propertymgr.entity.PropertyFeeBillOrder;

public interface IPropertyFeeBillOrderService extends BaseService<PropertyFeeBillOrder, Long> {

	PropertyFeeBillOrder findByOutTradeNo(String outTradeNo);
}
