package com.arf.goldcard.dao;

import java.util.Date;
import java.util.List;

import com.arf.core.dao.BaseDao;
import com.arf.goldcard.entity.GoldCardType;

public interface IGoldCardTypeDao extends BaseDao<GoldCardType, Long> {

	/**
	 * 查询所有可供购买的金卡类型
	 * @param userName
	 * @return
	 */
	public List<GoldCardType> findByUnShelved(Integer unShelved,Date nowTime);
}
