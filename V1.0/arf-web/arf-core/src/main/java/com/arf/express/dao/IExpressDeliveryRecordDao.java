package com.arf.express.dao;

import java.util.List;
import java.util.Map;

import com.arf.base.PageResult;
import com.arf.core.dao.BaseDao;
import com.arf.express.entity.ExpressDeliveryRecord;

public interface IExpressDeliveryRecordDao extends BaseDao<ExpressDeliveryRecord, Long>{

	/**
	 * 根据条件查询用户订单
	 * @param userName
	 * @param pageSize
	 * @param pageNo
	 * @param status
	 * @return
	 */
	public PageResult<Map<String, Object>> findExpressOrder(String userName,Integer pageSize,Integer pageNo,Integer status,Integer businessNum);
	
	/**
	 * 用户名+商户编码查询
	 * @param userName
	 * @param businessNum
	 * @return
	 */
	public List<ExpressDeliveryRecord> findByUserNameBusinessNum(String userName,Integer businessNum);
	
	/**
	 * 用户名+订单编码编码查询
	 * @param userName
	 * @param businessNum
	 * @return
	 */
	public ExpressDeliveryRecord findByUserNameOrderNo(String userName,String orderNo);

}
