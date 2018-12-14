package com.arf.goldcard.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.goldcard.dao.IGoldCardExchangeCodeDao;
import com.arf.goldcard.entity.GoldCardExchangeCode;
import com.arf.goldcard.service.IGoldCardExchangeCodeService;

@Service("goldCardExchangeCodeServiceImpl")
public class GoldCardExchangeCodeServiceImpl extends BaseServiceImpl<GoldCardExchangeCode, Long> implements IGoldCardExchangeCodeService{

	@Resource(name="goldCardExchangeCodeDaoImpl")
	IGoldCardExchangeCodeDao goldCardExchangeCodeDao;
	
	@Override
	protected BaseDao<GoldCardExchangeCode, Long> getBaseDao() {
		return goldCardExchangeCodeDao;
	}

	@Override
	public GoldCardExchangeCode findByExchangeCode(String exchangeCode) {
		return goldCardExchangeCodeDao.findByExchangeCode(exchangeCode);
	}

}
