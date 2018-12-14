package com.arf.axd.dao.impl;

import org.springframework.stereotype.Repository;

import com.arf.axd.dao.IEccPhysicalGiftRecordDao;
import com.arf.axd.entity.EccPhysicalGiftRecord;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("eccPhysicalGiftRecordDaoImpl")
public class EccPhysicalGiftRecordDaoImpl extends BaseDaoImpl<EccPhysicalGiftRecord,Long> implements IEccPhysicalGiftRecordDao{

}
