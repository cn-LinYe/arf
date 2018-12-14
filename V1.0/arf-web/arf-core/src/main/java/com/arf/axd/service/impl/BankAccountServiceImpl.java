package com.arf.axd.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.axd.dao.IBankAccountDao;
import com.arf.axd.entity.BankAccount;
import com.arf.axd.entity.BankAccount.Status;
import com.arf.axd.entity.BankAccount.Type;
import com.arf.axd.entity.BankAccount.UserType;
import com.arf.axd.service.IBankAccountService;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;


@Service("bankAccountServiceImpl")
public class BankAccountServiceImpl extends BaseServiceImpl<BankAccount, Long> implements IBankAccountService{

	@Resource(name = "bankAccountDaoImpl")
	private IBankAccountDao bankAccountDaoImpl;
	
	@Override
	protected BaseDao<BankAccount, Long> getBaseDao() {
		return bankAccountDaoImpl;
	}

	@Override
	public BankAccount findByTypeBankNumberStatus(BankAccount.Type type, String bankNumber,BankAccount.UserType userType,BankAccount.Status ...status) {
		return bankAccountDaoImpl.findByTypeBankNumberStatus(type,bankNumber,userType,status);
	}
	
	@Override
	public BankAccount findByBankNumber(String bankNumber,BankAccount.UserType userType){
		return bankAccountDaoImpl.findByBankNumber(bankNumber,userType);
	}

	@Override
	public List<BankAccount> findByUserNameStatus(String userName, Status status) {
		return bankAccountDaoImpl.findByUserNameStatus(userName,status);
	}

	@Override
	public List<BankAccount> findByUserName(String userName,BankAccount.UserType userType){
		return bankAccountDaoImpl.findByUserName(userName,userType);
	}

}
