package com.arf.goldcard.dao;

import java.util.Map;

import com.arf.base.PageResult;
import com.arf.core.dao.BaseDao;
import com.arf.goldcard.entity.UserGoldCardRecord;

public interface IUserGoldCardRecordDao extends BaseDao<UserGoldCardRecord, Long> {

	/**
	 * 通过用户名查找金卡订单记录信息
	 * @param userName
	 * @return
	 */
	PageResult<Map<String,Object>> getGoldCardOrders(String userName,Integer pageSize,Integer pageNO);
}
