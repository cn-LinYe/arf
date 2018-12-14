package com.arf.eparking.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.arf.base.PageResult;
import com.arf.core.service.BaseService;
import com.arf.eparking.entity.ParkingFeeApplicationRecords;
import com.arf.eparking.entity.ParkingFeeApplicationRecords.ApplyStatus;

public interface ParkingFeeApplicationRecordsService extends BaseService<ParkingFeeApplicationRecords, Long>{

	/**
	 * 通过商户编号和申请状态获取减免停车费记录
	 * @param businessNum
	 * @param applyStatus
	 * @return
	 */
	public PageResult<Map<String, Object>> findByBusinessNumAndApplyStatus(String businessNum, ApplyStatus applyStatus, Integer pageSize,Integer pageNo);
	
	/**
	 * 通过商户编号和订单号获取减免停车费记录
	 * @param businessNum
	 * @param orderNo
	 * @return
	 */
	public List<ParkingFeeApplicationRecords> findByBusinessNumAndOrderNo(String businessNum, String orderNo);
	
	/**
	 * 通过商户编号和车牌号等获取减免停车费记录
	 * @param businessNum
	 * @param communityNumber
	 * @param license
	 * @param arriveTime
	 * @return
	 */
	public List<ParkingFeeApplicationRecords> findByBusinessNumAndLicense(String businessNum, String communityNumber,String license,Date arriveTime);
	
	/**
	 * 通过微信openId获取减免停车费记录
	 * @param openId
	 * @return
	 */
	public PageResult<Map<String, Object>> findByOpenId(String openId, Integer pageSize,Integer pageNo);
	
	/**
	 * 统计一定时间内金额等信息
	 * @return
	 */
	public List<Map<String, Object>> statisticsBusinessNumOrder(String businessNum, ApplyStatus applyStatus, String startTime,
			String endTime);

	/**
	 * 统计一定时间内记录
	 * @return
	 */
	public PageResult<Map<String, Object>> findByBusinessNumAndApplyStatus(String businessNum, ApplyStatus applyStatus,Integer pageSize, Integer pageNo, String startTime, String endTime);

}
