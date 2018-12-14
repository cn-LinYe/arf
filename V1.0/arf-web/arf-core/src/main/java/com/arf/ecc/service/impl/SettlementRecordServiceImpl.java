package com.arf.ecc.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.ecc.dao.ISettlementRecordDao;
import com.arf.ecc.entity.SettlementRecord;
import com.arf.ecc.service.SettlementRecordService;

@Repository("settlementRecordServiceImpl")
public class SettlementRecordServiceImpl extends BaseServiceImpl<SettlementRecord, Long>
		implements SettlementRecordService {

	@Resource(name="settlementRecordDaoImpl")
	ISettlementRecordDao settlementRecordDao;
	
	@Override
	protected BaseDao<SettlementRecord, Long> getBaseDao() {
		return settlementRecordDao;
	}
	
}
