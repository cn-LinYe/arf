package com.arf.violation.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.arf.core.service.BaseService;
import com.arf.violation.entity.TrafficViolationOrderItem;

public interface ITrafficViolationOrderItemService extends BaseService<TrafficViolationOrderItem, Long>{

	/**
	 * 通过违章信息的特征码查询
	 * @return
	 */
	TrafficViolationOrderItem findByUniqueCode(String uniqueCode);
	
	/**
	 * 通过违章信息的特征码集合查询
	 * @return
	 */
	List<String> findByUniqueCodes(List<String> uniqueCodes);
	
	/**
	 * 通过违章时间及车牌查找
	 * @return
	 */
	List<Date> findByTimeLicense(List<Date> time,String license);
	
	/**
	 * 根据订单编号查询
	 * @return
	 */
	List<Map<String,Object>> findByOrderNo(String orderNo);
	/**
	 * 更新订单状态
	 * @param orderNo
	 * @return
	 */
	void updateOrderStatus(String orderNo,Integer status);
	
	/**
	 * 根据车牌查找（查找不同的订单）
	 * @param license
	 * @return
	 */
	int findByLicense(String license);
}
