package com.arf.axd_b2b.service;

import com.arf.axd_b2b.entity.GoodsOrder;
import com.arf.core.service.BaseService;

public interface IGoodsOrderService extends BaseService<GoodsOrder, Long> {

	/**
	 * 通过安心点统一订单编号查询
	 * @param axdOrderNo
	 * @return
	 */
	GoodsOrder findByAxdOrderNo(String axdOrderNo);

}
