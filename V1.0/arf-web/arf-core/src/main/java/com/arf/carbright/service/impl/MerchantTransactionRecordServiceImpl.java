package com.arf.carbright.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.base.PageResult;
import com.arf.carbright.dao.MerchantTransactionRecordDao;
import com.arf.carbright.entity.MerchantTransactionRecord;
import com.arf.carbright.entity.MerchantTransactionRecord.RecordType;
import com.arf.carbright.service.MerchantTransactionRecordService;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;

@Service("merchantTransactionRecordService")
public class MerchantTransactionRecordServiceImpl extends BaseServiceImpl<MerchantTransactionRecord, Long> implements MerchantTransactionRecordService{

	@Resource(name="merchantTransactionRecordDao")
	MerchantTransactionRecordDao merchantTransactionRecordDao;
	
	@Override
	protected BaseDao<MerchantTransactionRecord, Long> getBaseDao() {
		return merchantTransactionRecordDao;
	}

	@Override
	public PageResult<Map<String, Object>> findByBusinessNumAndRecordType(String businessNum, RecordType recordType,
			Integer pageSize, Integer pageNo, String startTime, String endTime) {
		return merchantTransactionRecordDao.findByBusinessNumAndRecordType(businessNum, recordType, pageSize, pageNo, startTime, endTime);
	}

}
