package com.arf.axd.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.axd.dao.IAccountWithdrawRecordDao;
import com.arf.axd.entity.AccountWithdrawRecord;
import com.arf.axd.service.IAccountWithdrawRecordService;
import com.arf.core.Filter;
import com.arf.core.Filter.Operator;
import com.arf.core.Order;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;

@Service("accountWithdrawRecordServiceImpl")
public class AccountWithdrawRecordServiceImpl extends BaseServiceImpl<AccountWithdrawRecord, Long> 
		implements IAccountWithdrawRecordService {
	
	@Resource(name = "accountWithdrawRecordDaoImpl")
	private IAccountWithdrawRecordDao accountWithdrawRecordDaoImpl;

	@Override
	protected BaseDao<AccountWithdrawRecord, Long> getBaseDao() {
		return accountWithdrawRecordDaoImpl;
	}
	
	@Override
	public List<AccountWithdrawRecord> findByUserName(String userName){
		return accountWithdrawRecordDaoImpl.findByUserName(userName);
	}

	@Override
	public List<AccountWithdrawRecord> findWithdrawingByUserName(String userName,String bankNumber) {
		return accountWithdrawRecordDaoImpl.findWithdrawingByUserName(userName,bankNumber);
	}

}
