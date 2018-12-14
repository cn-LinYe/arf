package com.arf.insurance.dao;

import java.util.List;

import com.arf.base.PageResult;
import com.arf.core.dao.BaseDao;
import com.arf.insurance.entity.InsuranceOrderRecord;

public interface IInsuranceOrderRecordDao extends BaseDao<InsuranceOrderRecord, Long>{

	/**
	 * 通过条件查找保险套餐--分页
	 * @return
	 */
	public PageResult<InsuranceOrderRecord> findInsurancePageByCondition(String userName,Integer businessNum,String orderStartDate,String orderEndDate,Integer orderStatus,Integer pageSize,Integer pageNo);
	
	/**
	 * 通过订单编号查找可取消的套餐
	 * @param orderNo
	 * @return
	 */
	public InsuranceOrderRecord findByOrderNo(String orderNo);
	
	/**根据订单编号及商户信息进行订单查询
	 * @param orderNo 订单编号
	 * @param businessNum 商户编号
	 * @return
	 */
	public InsuranceOrderRecord findInsuranceRecord(String orderNo,Integer businessNum);
	
	
	/**根据支付订单编号进行订单查询
	 * @param outOrderNo
	 * @return
	 */
	public InsuranceOrderRecord findByOutTradeNo(String outOrderNo);
	
	/**获取用户订单情况
	 * @param userName 用户名称
	 * @return
	 */
	public List<InsuranceOrderRecord> findUserOrders(String userName);
	
}
