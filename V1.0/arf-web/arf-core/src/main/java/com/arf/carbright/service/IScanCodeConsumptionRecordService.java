package com.arf.carbright.service;

import com.arf.carbright.entity.ScanCodeConsumptionRecord;
import com.arf.core.service.BaseService;

public interface IScanCodeConsumptionRecordService extends BaseService<ScanCodeConsumptionRecord, Long>{

	/**
	 * 通过订单编号查找
	 * @param orderNum
	 * @return
	 */
	ScanCodeConsumptionRecord findByOrderNum(String orderNum);
}
