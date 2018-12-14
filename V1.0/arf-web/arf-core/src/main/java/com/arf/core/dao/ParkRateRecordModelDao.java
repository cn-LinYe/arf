package com.arf.core.dao;

import java.util.Date;
import java.util.List;

import com.arf.core.entity.ParkRateRecordModel;

/**
 * Dao - 月租车
 * 
 * @author arf  dg
 * @version 4.0
 */
public interface ParkRateRecordModelDao extends BaseDao<ParkRateRecordModel, Long>{
	
	public ParkRateRecordModel selectByouttradeno (String out_trade_no);

	public List<ParkRateRecordModel> findByUserName(String userName);
	
	/**
	 * 查找今天的月租缴费订单
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<ParkRateRecordModel> findTodayRecord(Date startDate,Date endDate,List<String> communityList);
}
