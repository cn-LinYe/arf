package com.arf.platform.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.platform.dao.IEccConnectionRecordDao;
import com.arf.platform.entity.EccConnectionRecord;
import com.arf.platform.service.IEccConnectionRecordService;

@Service("eccConnectionRecordServiceImpl")
public class EccConnectionRecordServiceImpl extends BaseServiceImpl<EccConnectionRecord,Long> implements IEccConnectionRecordService{

	@Resource(name = "eccConnectionRecordDaoImpl")
	private IEccConnectionRecordDao eccConnectionRecordDaoImpl;
		
	@Override
	protected BaseDao<EccConnectionRecord, Long> getBaseDao() {
		return eccConnectionRecordDaoImpl;
	}

	@Override
	public EccConnectionRecord findLastOfflineRecord(String communityNumber) {
		return eccConnectionRecordDaoImpl.findLastOfflineRecord(communityNumber);
	}

	@Override
	public EccConnectionRecord findLastOnlineRecord(String communityNumber) {
		return eccConnectionRecordDaoImpl.findLastOnlineRecord(communityNumber);
	}

	@Override
	public EccConnectionRecord findLastRecord(String communityNumber) {
		return eccConnectionRecordDaoImpl.findLastRecord(communityNumber);
	}
	
}
