package com.arf.core.service;

import java.util.Date;
import java.util.List;

import com.arf.core.entity.ParkRateRecordModel;

/**
 * Service - 月租车
 * 
 * @author arf  dg
 * @version 4.0
 */
public interface ParkRateRecordModelService extends BaseService<ParkRateRecordModel, Long> {
	
	/**
	 * 通过订单号查询
	 * @param out_trade_no
	 * @return
	 */
	public ParkRateRecordModel  selectByouttradeno(String out_trade_no);

	public List<ParkRateRecordModel> findByUserName(String userName);
	
	/**
	 * 查找今天的月租缴费订单
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<ParkRateRecordModel> findTodayRecord(Date startDate,Date endDate,List<String> communityList);

	/**
	 * 查询最近的订单记录
	 * @param licensePlateNumber
	 * @param communityNumber
	 * @param secondsInterval
	 * @return
	 */
	public ParkRateRecordModel findLatestRecord(String licensePlateNumber,String communityNumber,int secondsInterval);
	
}
