package com.arf.carbright.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.carbright.dao.IScanCodeConsumptionRecordDao;
import com.arf.carbright.entity.ScanCodeConsumptionRecord;
import com.arf.carbright.service.IScanCodeConsumptionRecordService;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;

@Service("scanCodeConsumptionRecordServiceImpl")
public class ScanCodeConsumptionRecordServiceImpl extends BaseServiceImpl<ScanCodeConsumptionRecord, Long> implements IScanCodeConsumptionRecordService{

	@Resource(name="scanCodeConsumptionRecordDaoImpl")
	IScanCodeConsumptionRecordDao scanCodeConsumptionRecordDao;
	
	@Override
	protected BaseDao<ScanCodeConsumptionRecord, Long> getBaseDao() {
		return scanCodeConsumptionRecordDao;
	}

	@Override
	public ScanCodeConsumptionRecord findByOrderNum(String orderNum) {
		return scanCodeConsumptionRecordDao.findByOrderNum(orderNum);
	}

}
