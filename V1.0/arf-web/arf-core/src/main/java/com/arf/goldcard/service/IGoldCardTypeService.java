package com.arf.goldcard.service;

import java.util.Date;
import java.util.List;

import com.arf.core.service.BaseService;
import com.arf.goldcard.entity.GoldCardType;

public interface IGoldCardTypeService extends BaseService<GoldCardType,Long>{

	/**
	 * 通过类型编号查找
	 * @param TypeNum
	 * @return
	 */
	public GoldCardType findByTypeNum(String typeNum);
	
	/**
	 * 查询所有可供购买的金卡类型
	 * @param userName
	 * @return
	 */
	public List<GoldCardType> findByUnShelved(Integer unShelved,Date nowTime);
}
