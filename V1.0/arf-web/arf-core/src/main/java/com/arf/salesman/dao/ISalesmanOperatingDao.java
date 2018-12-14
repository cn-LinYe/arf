package com.arf.salesman.dao;

import com.arf.core.dao.BaseDao;
import com.arf.salesman.entity.SalemanOperating;

public interface ISalesmanOperatingDao extends BaseDao<SalemanOperating, Long> {

	/**
	 * 通过下去编码查找操作记录
	 * @param comuunityNum
	 * @return
	 */
	SalemanOperating findByCommunity(String comuunityNum);
}
