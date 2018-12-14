package com.arf.gas.dao;

import java.util.List;

import com.arf.core.dao.BaseDao;
import com.arf.gas.entity.RefuelGasCard;

public interface IRefuelGasCardDao extends BaseDao<RefuelGasCard, Long>{

	/**
	 * 根据用户名商户油站编号查询可用油卡
	 * @param userName
	 * @param gasNum
	 * @param businessNum
	 * @return
	 */
	RefuelGasCard findByUserName(String userName,Integer gasNum,Integer businessNum);
	
	/**
	 * 根据油卡编号查询油卡
	 * @param cardNumber
	 * @return
	 */
	RefuelGasCard findByCardNumber(String cardNumber);
	
	/**
	 * 批量开卡
	 * @param cardNumber
	 */
	void addCars(List<RefuelGasCard> refuelGasCards);
}
