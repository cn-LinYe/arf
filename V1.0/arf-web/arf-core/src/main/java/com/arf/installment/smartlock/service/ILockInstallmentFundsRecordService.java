package com.arf.installment.smartlock.service;

import java.util.List;

import com.arf.core.service.BaseService;
import com.arf.installment.smartlock.entity.LockInstallmentFundsRecord;

public interface ILockInstallmentFundsRecordService extends BaseService<LockInstallmentFundsRecord,Long>{
	
	/**
	 * 根据款项编号查询
	 * @param userName
	 * @return
	 */
	public LockInstallmentFundsRecord findByFundsNo(String fundsNo);
	
	/**
	 * 根据款项编号数组查询List
	 * @param fundsNos
	 * @return
	 */
	public List<LockInstallmentFundsRecord> findByFundsNos(String[] fundsNos);
	public List<LockInstallmentFundsRecord> findByOrderNo(String orderNo);

	/**
	 * 查找未支付过期的款项
	 * @return
	 */
	public List<LockInstallmentFundsRecord> findFundsRecordDeadLine();
}
