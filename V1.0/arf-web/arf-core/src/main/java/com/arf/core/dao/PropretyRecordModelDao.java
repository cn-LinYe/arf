package com.arf.core.dao;

import com.arf.core.entity.PropretyRecordModel;

/**
 * Dao - 物业费订单
 * 
 * @author arf  dg
 * @version 4.0
 */
public interface PropretyRecordModelDao extends BaseDao<PropretyRecordModel, Long>{
	/**
	 * 通过订单号查询
	 */
	public PropretyRecordModel selectByouttradeno(String out_trade_no);
	
	/**
	 * 通过房间号查找
	 * @param HouseId
	 * @return
	 */
	public PropretyRecordModel selectByHouseId(String HouseId);
}
