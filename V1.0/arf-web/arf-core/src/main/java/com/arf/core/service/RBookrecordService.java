package com.arf.core.service;

import java.util.List;

import com.arf.core.entity.RBookrecord;

/**
 * Service - RBookrecord表
 * 
 * @author arf  dg
 * @version 4.0
 */
public interface RBookrecordService extends BaseService<RBookrecord, Long> {

	/**
	 * 通过订单号查询订单记录
	 * @param orderSn
	 * @return
	 */
	List<RBookrecord> findByOrderSn(String orderSn);
	
}
