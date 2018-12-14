package com.arf.goldcard.service;

import java.util.Map;

import com.arf.base.PageResult;
import com.arf.core.service.BaseService;
import com.arf.goldcard.entity.UserGoldCardRecord;

public interface IUserGoldCardRecordService extends BaseService<UserGoldCardRecord,Long>{


	/**
	 * 通过用户名查找金卡订单记录信息
	 * @param userName
	 * @return
	 */
	PageResult<Map<String,Object>> getGoldCardOrders(String userName,Integer pageSize,Integer pageNO);
	/**
	 * 通过cardNo查找
	 * @param cardNo
	 * @return
	 */
	UserGoldCardRecord findByCardNo(String cardNo);
	
}
