package com.arf.core.dao;

import com.arf.core.entity.PayMoneyWayModel;

/**
 * Dao - 根据支付方式获取支付金额表
 * 
 * @author arf
 * @version 4.0
 */
public interface PayMoneyWayModelDao extends BaseDao<PayMoneyWayModel, Long>{
	
	/**
	 * 通过支付类型获得支付金额
	 * @param level
	 * 				支付类型
	 * @return 支付金额表
	 */
	PayMoneyWayModel selectByLevel(int level);
}
