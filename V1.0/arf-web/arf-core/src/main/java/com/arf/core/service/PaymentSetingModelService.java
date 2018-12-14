package com.arf.core.service;

import com.arf.core.entity.PaymentSetingModel;
import com.arf.core.entity.PaymentSetingModel.Type;

/**
 * Service - 子公司支付账号
 * 
 * @author arf  dg
 * @version 4.0
 */
public interface PaymentSetingModelService extends BaseService<PaymentSetingModel, Long> {
	
	
	/**
	 * 小区id查询
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
