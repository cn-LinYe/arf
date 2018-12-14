package com.arf.carbright.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.carbright.dao.IRPushMessageServiceRecordDao;
import com.arf.carbright.entity.RPushMessageServiceRecord;
import com.arf.carbright.service.IRPushMessageServiceRecordService;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;

@Service("rPushMessageServiceRecordServiceImpl")
public class RPushMessageServiceRecordServiceImpl extends BaseServiceImpl<RPushMessageServiceRecord, Long> implements IRPushMessageServiceRecordService{

	@Resource(name="rPushMessageServiceRecordDaoImpl")
	public IRPushMessageServiceRecordDao rPushMessageServiceRecordImpl;
	
	@Override
	protected BaseDao<RPushMessageServiceRecord, Long> getBaseDao() {
		return rPushMessageServiceRecordImpl;
	}

}
