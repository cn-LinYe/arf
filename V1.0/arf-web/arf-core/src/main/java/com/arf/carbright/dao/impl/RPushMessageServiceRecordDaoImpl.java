package com.arf.carbright.dao.impl;

import org.springframework.stereotype.Repository;

import com.arf.carbright.dao.IRPushMessageServiceRecordDao;
import com.arf.carbright.entity.RPushMessageServiceRecord;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("rPushMessageServiceRecordDaoImpl")
public class RPushMessageServiceRecordDaoImpl extends BaseDaoImpl<RPushMessageServiceRecord, Long> implements IRPushMessageServiceRecordDao {
	
}
