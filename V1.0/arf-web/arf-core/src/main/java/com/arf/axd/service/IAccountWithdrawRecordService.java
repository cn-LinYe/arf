package com.arf.axd.service;

import java.util.List;

import com.arf.axd.entity.AccountWithdrawRecord;
import com.arf.core.service.BaseService;

public interface IAccountWithdrawRecordService extends BaseService<AccountWithdrawRecord, Long> {

	List<AccountWithdrawRecord> findByUserName(String userName);
	
	/**
	 * 查询正在提现中的提现记录
	 * @param userName
	 * @param bankNumber
	 * @return
	 */
	List<AccountWithdrawRecord> findWithdrawingByUserName(String userName,String bankNumber);
}
