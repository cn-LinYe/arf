package com.arf.goldcard.service;

import java.util.Map;

import com.arf.base.PageResult;
import com.arf.core.service.BaseService;
import com.arf.goldcard.entity.GoldCardExchangeCodeRecord;

public interface IGoldCardExchangeCodeRecordService extends BaseService<GoldCardExchangeCodeRecord, Long>{

	/**
	 * 通过用户名查找金卡订单记录信息
	 * @param userName
	 * @return
	 */
	PageResult<Map<String,Object>> getIGoldCardExchangeCodeServiceOrders(String userName,Integer pageSize,Integer pageNO);
	
}
