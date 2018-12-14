package com.arf.salesman.service;

import com.arf.core.service.BaseService;
import com.arf.salesman.entity.SalemanOperating;

public interface ISalesmanOperatingService extends BaseService<SalemanOperating, Long> {

	/**
	 * 通过下去编码查找操作记录
	 * @param comuunityNum
	 * @return
	 */
	SalemanOperating findByCommunity(String comuunityNum);
}
