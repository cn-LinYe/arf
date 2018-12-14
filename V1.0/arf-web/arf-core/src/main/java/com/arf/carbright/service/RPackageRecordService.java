package com.arf.carbright.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.arf.carbright.dto.SaleroomOfPeriodDto;
import com.arf.carbright.entity.PackageType;
import com.arf.carbright.entity.RPackageRecord;
import com.arf.core.AppMessage;
import com.arf.core.service.BaseService;
import com.arf.platform.entity.SMemberAccount;

public interface RPackageRecordService  extends BaseService<RPackageRecord, Long> {
	
	/**
	 * 生成套餐购买订单
	 * @param userName
	 * @param packageTypeNum
	 * @return
	 */
	public AppMessage generatePkgOrder(String userName,String license,RPackageRecord.FeePayType feePayType, List<String> packageTypeNum,List<String> vouchersNum,Integer points) throws Exception ;
	
	/**生成套餐购买订单
	 * @param userName 用户名
	 * @param license 车牌
	 * @param feePayType 
	 * @param packageTypes
	 * @param memberAccount
	 * @param odrerNumber
	 * @param totlaFees
	 * @param basicAccount
	 * @throws Exception
	 */
	public void generatePkgOrder(String userName,String license,RPackageRecord.FeePayType feePayType,List<PackageType> packageTypes,SMemberAccount memberAccount,String odrerNumber,BigDecimal totlaFees,BigDecimal basicAccount,BigDecimal paymentPrice) throws Exception;
	
	/**
	 * 根据用户名和状态来查询用户的套餐
	 * @param userName
	 * @param status 为null查询所有状态的套餐记录
	 * @return
	 */
	public AppMessage findPackages(String userName,RPackageRecord.Status status,int pageSize,int pageNo);
	
	/**
	 * 根据用户名和状态,支付状态来查询用户的套餐
	 * @param userName
	 * @param status 为null查询所有状态的套餐记录
	 * @return
	 */
	public AppMessage findPackages(String userName,int businessNum,RPackageRecord.Status status,RPackageRecord.FeePayStatus feePayStatus,int pageSize,int pageNo);
	
	/**
	 * 处理微信,支付宝等在线支付套餐购买的回调业务逻辑
	 * @param outTradeNo
	 * @param totalFee 单位:分
	 * @return
	 */
	boolean boughtPkgSuccess(String outTradeNo,int totalFee,Integer feePayType);
	
	/**
	 * 统计某个阶段的商户销售总额
	 * @param businessId
	 * @param startTime
	 * @param endTime
	 * @return 单位:元
	 */
	SaleroomOfPeriodDto findSaleroomOfPeriod(long businessId,Date startBuyingTime,Date endBuyingTime);
	
	/**
	 * 统计商户套餐（某一个）销售情况
	 * 根据不同时间进行查询 
	 * @param startTime
	 * @param endTime
	 * @param businessId
	 * @param packageId
	 * @return
	 */
	public BigDecimal salesInfoStatistics(Date startTime,Date endTime,Integer businessId,Integer packageId);
	
	/**统计销售情况根据分组
	 * @param startTime
	 * @param endTime
	 * @param businessId
	 * @param packageId
	 * @param limitCount
	 * @return
	 */
	public List<HashMap<String,Object>> salesInfoGroupDay(Date startTime,Date endTime,Integer businessId,Integer packageId,Integer limitCount);
	
	/**查询支付失败或者未支付订单并且存在积分兑换或者代金券使用情况
	 * @param buyingdate
	 * @param minute
	 * @return
	 */
	public List<RPackageRecord> findByFailureRecordToday(String buyingdate,int minute);

	/**
	 * 根据用户名查找卡券并且根据商户id分组
	 * @param userName
	 * @return
	 */
	public List<HashMap<String,Object>> findByUserName(String userName);
	
	/**
	 * 根据用户名查找即将过期的卡券并通过用户名分组
	 * @param userName
	 * @return
	 */
	public List<RPackageRecord> findPackageByUserName(String userName,Date endTime);
	
	/**
	 * 根据套餐Id查找套餐
	 * @param userName
	 * @return
	 */
	public  RPackageRecord findBypackageId(String userName,Long packageId);
	
	/**
	 * 批量更新推送时间
	 * @param list
	 * @return
	 */
	public  void updatePackageRecord(List<String> list);
	
	/**
	 * 批量更新推送时间
	 * @param list
	 * @return
	 */
	public  void updatePackageByOrderNo(List<String> list);

	/**
	 * 查询用户对某一编号的套餐总的购买次数
	 * @param userName @Nullable
	 * @param packageNum @Nullable
	 * @return
	 */
	public int findCountByPkgNum(String userName, String packageTypeNum);
	
	
	/**获取用户可用卡券数量（失效、可用、全部）
	 * @param userName
	 * @param packageTypeNum
	 * @param status
	 * @return
	 */
	public int findCountByPkgNum(String userName, String packageTypeNum,RPackageRecord.Status status);
}
