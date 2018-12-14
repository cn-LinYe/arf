package com.arf.carbright.dao;

import com.arf.carbright.entity.ScanCodeConsumptionRecord;
import com.arf.core.dao.BaseDao;

public interface IScanCodeConsumptionRecordDao extends BaseDao<ScanCodeConsumptionRecord, Long>{

	/**
	 * 通过订单编号查找
	 * @param orderNum
	 * @return
	 */
	ScanCodeConsumptionRecord findByOrderNum(String orderNum);
}
