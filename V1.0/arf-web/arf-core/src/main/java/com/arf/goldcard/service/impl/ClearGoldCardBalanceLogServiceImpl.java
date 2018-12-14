package com.arf.goldcard.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.goldcard.dao.IClearGoldCardBalanceLogDao;
import com.arf.goldcard.entity.ClearGoldCardBalanceLog;
import com.arf.goldcard.service.IClearGoldCardBalanceLogService;

@Service("clearGoldCardBalanceLogService")
public class ClearGoldCardBalanceLogServiceImpl extends BaseServiceImpl<ClearGoldCardBalanceLog, Long> implements IClearGoldCardBalanceLogService{

	@Resource(name = "clearGoldCardBalanceLogDao")
	private IClearGoldCardBalanceLogDao clearGoldCardBalanceLogDao;
	
	@Override
	protected BaseDao<ClearGoldCardBalanceLog, Long> getBaseDao() {
		return clearGoldCardBalanceLogDao;
	}

}

