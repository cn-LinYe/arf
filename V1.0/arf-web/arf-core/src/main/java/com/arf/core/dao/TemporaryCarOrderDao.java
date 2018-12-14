package com.arf.core.dao;

import com.arf.core.entity.TemporaryCarOrder;

/**
 * Dao - 临时车牌表
 * 
 * @author arf  dg
 * @version 4.0
 */
public interface TemporaryCarOrderDao extends BaseDao<TemporaryCarOrder, Long>{
	
	/**
	 * 通过订单号查询
	 * @param out_trade_no
	 * @return
	 */
	public TemporaryCarOrder selectByouttradeno(String out_trade_no) ;

}
