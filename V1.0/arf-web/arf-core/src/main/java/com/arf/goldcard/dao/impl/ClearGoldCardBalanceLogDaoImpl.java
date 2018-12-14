package com.arf.goldcard.dao.impl;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.goldcard.dao.IClearGoldCardBalanceLogDao;
import com.arf.goldcard.entity.ClearGoldCardBalanceLog;

@Repository("clearGoldCardBalanceLogDao")
public class ClearGoldCardBalanceLogDaoImpl extends BaseDaoImpl<ClearGoldCardBalanceLog, Long> implements IClearGoldCardBalanceLogDao{

}

