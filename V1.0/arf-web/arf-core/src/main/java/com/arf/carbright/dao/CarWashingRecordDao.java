package com.arf.carbright.dao;

import java.util.Date;
import java.util.List;

import com.arf.base.PageResult;
import com.arf.carbright.entity.CarWashingRecord;
import com.arf.carbright.entity.CarWashingRecord.OrderType;
import com.arf.core.dao.BaseDao;

public interface CarWashingRecordDao extends BaseDao<CarWashingRecord, Long>{

	/**
	 * 查询用户的订单根据订单状态
	 * @param userName
	 * @param status
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	PageResult<CarWashingRecord> findMyOrders(String userName, com.arf.carbright.entity.CarWashingRecord.Status status,int pageSize, int pageNo);
	
	/**
	 * 查询用户的订单根据订单状态和套餐记录Id
	 * 查询套餐使用记录
	 * @param userName
	 * @param status
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	PageResult<CarWashingRecord> findPackageUsedRecords(String userName,Long packageRecordId, com.arf.carbright.entity.CarWashingRecord.Status status,int pageSize, int pageNo);
	
	/**
	 * 查询用户正在进行的订单记录
	 * @param userName
	 * @param status  :status=ONGOING 正在进行，status=FINISH 已经完成
	 * @return
	 */
	List<CarWashingRecord> findOrderByUserName(String userName,String status);
	
	/**
	 * 通过订单编号来查询订单记录（订单id）
	 * @param orderNo
	 * @return
	 */
	CarWashingRecord findByOrderNo(String orderNo);
	
	
	/**
	 * 通过订单编号来查询订单记录
	 * @param orderNo
	 * @return
	 */
	CarWashingRecord findByOrderNum(String orderNo);
	
	
	
	
	/**
	 * 商户查询自己的订单接口
	 * 【注意】 
	 * 如果是查询商户已完成订单{@link com.arf.carbright.entity.CarWashingRecord.Status.Merchant_Completed},
	 * 则会查询所有包括Merchant_Completed("商户已完成"), Member_Completed("用户已完成"),Member_Evaluate("用户已评价");的订单
	 * 如果是查询商户已经确认的订单{@link com.arf.carbright.entity.CarWashingRecord.Status.Confirmed},则查询包括这两个状态的订单:Confirmed,User_stored_Key
	 * @param businessLoginName
	 * @param status 订单状态
	 * @return
	 */
	PageResult<CarWashingRecord> findMerchantOrders(long businessId,CarWashingRecord.Status status,int pageSize, int pageNo);
	/**
	 * 商家订单分析统计
	 * @param businessId
	 * @param status
	 * @return
	 */
	public int orderMerchantAnalysis(Long businessId, Integer status);
	
	
	/**查找用户订单超时订单
	 * @param status 订单状态
	 * @param orderType 订单类型
	 * @param rangeDate 范围时间
	 * @param minutes 超时分钟数
	 * @return
	 */
	public List<CarWashingRecord> findOrderTimeOut(CarWashingRecord.Status status,OrderType orderType,Date rangeDate,int minutes);
	
	/**
	 * 查找商户未服务的订单数量
	 * @param orderNo
	 * @return
	 */
	List<CarWashingRecord> findByStatus(Integer[] status,Long businessId);
}
