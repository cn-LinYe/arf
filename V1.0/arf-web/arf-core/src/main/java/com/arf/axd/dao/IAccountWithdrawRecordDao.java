package com.arf.axd.dao;

import java.util.List;

import com.arf.axd.entity.AccountWithdrawRecord;
import com.arf.core.dao.BaseDao;

public interface IAccountWithdrawRecordDao extends BaseDao<AccountWithdrawRecord, Long> {

	/**
	 * 根据名字查询
	 * @param userName
	 * @return
	 */
	List<AccountWithdrawRecord> findByUserName(String userName);

	/**
	 * 查询正在提现中的提现记录
	 * @param userName
	 * @param bankNumber
	 * @return
	 */
	List<AccountWithdrawRecord> findWithdrawingByUserName(String userName,String bankNumber);
}
