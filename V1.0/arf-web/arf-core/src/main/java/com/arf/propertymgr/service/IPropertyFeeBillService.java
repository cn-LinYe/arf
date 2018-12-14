package com.arf.propertymgr.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.arf.core.service.BaseService;
import com.arf.propertymgr.condition.PropertyFeeBillCondition;
import com.arf.propertymgr.entity.PropertyFeeBill;
import com.arf.propertymgr.entity.PropertyFeeBill.PayStatus;

public interface IPropertyFeeBillService extends BaseService<PropertyFeeBill, Long> {

	/**
	 * 通过账单号集合查询
	 * @param billNos
	 * @return
	 */
	List<PropertyFeeBill> findByBillNos(List<String> billNos);
	
	/**
	 * 查询条件搜索
	 * @param condition
	 * @return
	 */
	@Deprecated
	List<PropertyFeeBill> findByCondition(PropertyFeeBillCondition condition);

	/**
	 * 根据用户名和缴费状态查询
	 * @param userName
	 * @param payStatus
	 * @return
	 */
	List<PropertyFeeBill> findByUsernamePayStatus(String userName,
			PayStatus payStatus);
	
	/**
	 * 查询条件搜索v2
	 * @param condition
	 * @return
	 */
	List<PropertyFeeBill> findByConditionV2(PropertyFeeBillCondition condition);
	/**
	 * 批量插入账单
	 * @param bills
	 */	
	public void addBillBatch(List<PropertyFeeBill> bills);
	
	/**
	 * 根据支付时间查询
	 * @param condition
	 * @return
	 */
	List<PropertyFeeBill> findByPaidDate(Date startDate,Date endDate,List<String> communityList);
	
	/**
	 * 根据用户名查找用户同一个房间
	 * @param userName
	 * @return
	 */
	List<Map<String,String>> findAgenentBills(String userName);
}
