package com.arf.core.service;

import com.arf.core.entity.PayMoneyWayModel;

/**
 * Service - 根据支付方式获取支付金额表
 * 
 * @author arf
 * @version 4.0
 */
public interface PayMoneyWayModelService extends BaseService<PayMoneyWayModel, Long> {
	/**
	 * 通过支付类型获得支付金额
	 * @param level
	 * 				支付类型
	 * @return 支付金额表
	 */
	PayMoneyWayModel selectByLevel(int level);
}
