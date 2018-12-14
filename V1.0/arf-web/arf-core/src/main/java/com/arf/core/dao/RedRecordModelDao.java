package com.arf.core.dao;

import java.util.List;

import com.arf.core.entity.RedRecordModel;

/**
 * Dao - 发送红包纪录表
 * 
 * @author arf
 * @version 4.0
 */
public interface RedRecordModelDao extends BaseDao<RedRecordModel, Long>{
	/**
	 * 查询未成功的红包信息
	 * @return	未成功的红包信息
	 */
	List<RedRecordModel> sellectByStatus();
	
	RedRecordModel findByOutTradeNo(String outTradeNo);
}
