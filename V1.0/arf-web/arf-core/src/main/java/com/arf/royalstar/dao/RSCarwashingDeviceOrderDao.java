package com.arf.royalstar.dao;

import com.arf.core.dao.BaseDao;
import com.arf.royalstar.entity.RSCarwashingDeviceOrder;

public interface RSCarwashingDeviceOrderDao extends BaseDao<RSCarwashingDeviceOrder, Long> {

	public RSCarwashingDeviceOrder findByOrderNo(String orderNo);
}
