package com.arf.carbright.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.arf.base.PageResult;
import com.arf.carbright.dto.SaleroomOfPeriodDto;
import com.arf.carbright.entity.RPackageRecord;
import com.arf.core.dao.BaseDao;

public interface RPackageRecordDao extends BaseDao<RPackageRecord, Long>{

	
	/**
	 * 通过套餐编码查询用户的订单
	 * @return
	 */
	public List<RPackageRecord> findByPackageTypeNum(String packageTypeNum,Integer status,Integer feePayStatus); 
	/**
	 * 通过用户名查找成功的可用的订单
	 * @return
	 */
	public List<RPackageRecord> findByUserName(String userName,Integer status,Integer feePayStatus);
	
	/**
	 * 通过用户名查找成功的可用的订单--分页
	 * @return
	 */
	public PageResult<RPackageRecord> findPageByUserName(String userName,List<String> useRangeNum,Integer status,Integer feePayStatus,int pageSize,int pageNo);
	
	/**
	 * 通过条件查找商户发行套餐--分页
	 * @return
	 */
	public PageResult<RPackageRecord> findPageByCondition(String userName,Integer businessId,List<String> useRangeNum,Integer status, Integer feePayStatus,
			Date startTime,Date endTime,int pageSize, int pageNo);
	/**
	 * 通过订单编号查找所有记录
	 * @param outTradeNo
	 * @return
	 */
	public List<RPackageRecord> findByOutTradeNo(String outTradeNo);
	
	/**
	 * 查询某一outTradeNo下的所有记录总金额
	 * @param outTradeNo
	 * @return 返回金额单位元
	 */
	public BigDecimal findSumTotalFee(String outTradeNo);
	
	/**
	 * 查询某一outTradeNo下的所有记录总赠送金额
	 * @param outTradeNo
	 * @return 返回金额单位元
	 */
	public BigDecimal findSumGiftAmount(String outTradeNo);
	
	/**
	 * 查询用户对某一编号的套餐总的购买次数
	 * @param userName @Nullable
	 * @param packageNum @Nullable
	 * @return
	 */
	public int findCountByPkgNum(String userName, String packageTypeNum);
	
	/**
	 * 统计某个阶段的商户销售总额
	 * @param businessId
	 * @param startTime
	 * @param endTime
	 * @return SaleroomOfPeriodDto
	 */
	SaleroomOfPeriodDto findSaleroomOfPeriod(long businessId,Date startBuyingTime,Date endBuyingTime);
	/**
	 * 可用、未过时的套餐记录
	 * @param status
	 * @param endTime
	 * @return
	 */
	public List<RPackageRecord> findByStatusAndEndTime(Integer status, Date endTime);
	
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
	 * 根据套餐Id查找套餐
	 * @param userName
	 * @return
	 */
	public  RPackageRecord findBypackageId(String userName,Long packageId);
	
	/**
	 * 根据用户名查找即将过期的卡券并通过用户名分组
	 * @param userName
	 * @return
	 */
	public List<RPackageRecord> findPackageByUserName(String userName,Date endTime);
	
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
	
	/**获取用户可用卡券数量（失效、可用、全部）
	 * @param userName
	 * @param packageTypeNum
	 * @param status
	 * @return
	 */
	public int findCountByPkgNum(String userName, String packageTypeNum,RPackageRecord.Status status);
	
}
