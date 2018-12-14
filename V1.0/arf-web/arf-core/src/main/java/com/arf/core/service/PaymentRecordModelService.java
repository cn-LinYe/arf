package com.arf.core.service;

import java.util.List;

import com.arf.core.entity.PaymentRecordModel;

/**
 * Service - 支付纪录表
 * 
 * @author arf
 * @version 4.0
 */
public interface PaymentRecordModelService extends BaseService<PaymentRecordModel, Long> {
	
	/**
	 * 根据用户名查找支付记录
	 * @param user_name
	 * 					用户名
	 * @return	支付记录集合
	 */
	List<PaymentRecordModel> select(String user_name);
	
}
