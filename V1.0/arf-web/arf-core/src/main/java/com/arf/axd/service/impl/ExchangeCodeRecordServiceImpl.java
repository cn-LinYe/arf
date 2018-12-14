package com.arf.axd.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.axd.dao.IExchangeCodeRecordDao;
import com.arf.axd.entity.ExchangeCodeRecord;
import com.arf.axd.service.IExchangeCodeRecordService;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;

@Service("exchangeCodeRecordServiceImpl")
public class ExchangeCodeRecordServiceImpl extends BaseServiceImpl<ExchangeCodeRecord,Long> implements IExchangeCodeRecordService{

	@Resource(name="exchangeCodeRecordDaoImpl") 
	private IExchangeCodeRecordDao iExchangeCodeRecordDao;
	
	@Override
	protected BaseDao<ExchangeCodeRecord, Long> getBaseDao() {
		return iExchangeCodeRecordDao;
	}

	@Override
	public ExchangeCodeRecord findByExchangeCode(String exchangeCode) {
		return iExchangeCodeRecordDao.findByExchangeCode(exchangeCode);
	}

}
