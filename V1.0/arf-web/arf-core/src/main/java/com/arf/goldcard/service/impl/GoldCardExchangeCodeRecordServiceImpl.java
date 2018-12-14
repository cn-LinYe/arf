package com.arf.goldcard.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.base.PageResult;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.goldcard.dao.IGoldCardExchangeCodeRecordDao;
import com.arf.goldcard.entity.GoldCardExchangeCodeRecord;
import com.arf.goldcard.service.IGoldCardExchangeCodeRecordService;

@Service("goldCardExchangeCodeRecordServiceImpl")
public class GoldCardExchangeCodeRecordServiceImpl extends BaseServiceImpl<GoldCardExchangeCodeRecord, Long> implements IGoldCardExchangeCodeRecordService{

	@Resource(name="goldCardExchangeCodeRecordDaoImpl")
	IGoldCardExchangeCodeRecordDao goldCardExchangeCodeRecordDao;
	
	@Override
	protected BaseDao<GoldCardExchangeCodeRecord, Long> getBaseDao() {
		return goldCardExchangeCodeRecordDao;
	}

	@Override
	public PageResult<Map<String, Object>> getIGoldCardExchangeCodeServiceOrders(String userName, Integer pageSize,Integer pageNO) {
		return goldCardExchangeCodeRecordDao.getIGoldCardExchangeCodeServiceOrders(userName, pageSize, pageNO);
	}

}
