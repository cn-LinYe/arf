package com.arf.axd.dao;

import java.util.List;

import com.arf.axd.entity.BankAccount;
import com.arf.axd.entity.BankAccount.Status;
import com.arf.core.dao.BaseDao;

public interface IBankAccountDao extends BaseDao<BankAccount,Long>{

	/**
	 * 根据类型、账户号码、状态查询(后期会做银行卡验证，所有不让多个人绑定同一个银行卡)
	 * @param type 
	 * @param bankNumber
	 * @param statuses 为空查询所有状态
	 * @return
	 */
	BankAccount findByTypeBankNumberStatus(BankAccount.Type type, String bankNumber,BankAccount.UserType userType,BankAccount.Status ...status);
	
	/**
	 * 根据账户号码查询用户银行卡记录
	 * @param bankNumber
	 * @return
	 */
	BankAccount findByBankNumber(String bankNumber,BankAccount.UserType userType);

	/**
	 * 根据用户名、状态查询
	 * @param userName
	 * @param status
	 * @return
	 */
	List<BankAccount> findByUserNameStatus(String userName, Status status);

	/**
	 * 根据用户名获取用户的银行卡/支付宝/微信账户
	 * @param userName
	 * @return
	 */
	List<BankAccount> findByUserName(String userName,BankAccount.UserType userType);
}
