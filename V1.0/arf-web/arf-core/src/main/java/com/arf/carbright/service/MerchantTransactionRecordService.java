package com.arf.carbright.service;

import java.util.Map;

import com.arf.base.PageResult;
import com.arf.carbright.entity.MerchantTransactionRecord;
import com.arf.carbright.entity.MerchantTransactionRecord.RecordType;
import com.arf.core.service.BaseService;

public interface MerchantTransactionRecordService extends BaseService<MerchantTransactionRecord, Long>{
	
	/**
	 * 根据商户编号，交易类型，开始时间，结束时间查询商户交易记录
	 * businessNum
	 * recordType
	 * startTime
	 * endTime
	 * **/
	public PageResult<Map<String, Object>> findByBusinessNumAndRecordType(String businessNum,RecordType recordType,
			Integer pageSize, Integer pageNo,String startTime,String endTime);

}
