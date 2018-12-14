package com.arf.axd.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.axd.dao.IEccPhysicalGiftRecordDao;
import com.arf.axd.entity.EccPhysicalGiftRecord;
import com.arf.axd.service.IEccPhysicalGiftRecordService;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;

@Service("eccPhysicalGiftRecordServiceImpl")
public class EccPhysicalGiftRecordServiceImpl extends BaseServiceImpl<EccPhysicalGiftRecord,Long> implements IEccPhysicalGiftRecordService{

	@Resource(name="eccPhysicalGiftRecordDaoImpl")
	private IEccPhysicalGiftRecordDao iEccPhysicalGiftRecordDao;
	
	@Override
	protected BaseDao<EccPhysicalGiftRecord, Long> getBaseDao() {
		return iEccPhysicalGiftRecordDao;
	}

}
