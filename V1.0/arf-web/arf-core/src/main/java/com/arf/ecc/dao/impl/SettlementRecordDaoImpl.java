package com.arf.ecc.dao.impl;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.ecc.dao.ISettlementRecordDao;
import com.arf.ecc.entity.SettlementRecord;

@Repository("settlementRecordDaoImpl")
public class SettlementRecordDaoImpl extends BaseDaoImpl<SettlementRecord, Long> implements ISettlementRecordDao {

}
