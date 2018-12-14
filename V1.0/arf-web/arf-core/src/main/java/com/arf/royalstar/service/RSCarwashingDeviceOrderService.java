package com.arf.royalstar.service;

import com.arf.core.service.BaseService;
import com.arf.royalstar.entity.RSCarwashingDeviceOrder;

public interface RSCarwashingDeviceOrderService extends BaseService<RSCarwashingDeviceOrder, Long> {

	public RSCarwashingDeviceOrder findByOrderNo(String orderNo);
}
