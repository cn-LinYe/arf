package com.arf.core.dao;

import com.arf.core.entity.PaymentSetingModel;
import com.arf.core.entity.PaymentSetingModel.Type;

/**
 * Dao - 月租车
 * 
 * @author arf  dg
 * @version 4.0
 */
public interface PaymentSetingModelDao extends BaseDao<PaymentSetingModel, Long>{
	
	/**
	 * 通过小区id查询
	 * @param CommunityNumber
	 * @return
	 */
	public PaymentSetingModel findByCommunityNumber(String CommunityNumber);

	/**
	 * 根据主体类型和编号（小区编号/商户编号/安尔发）查询
	 * @param type
	 * @param number
	 * @return
	 */
	public PaymentSetingModel findByObjectTypeNumber(Type type, String number);

	
}
