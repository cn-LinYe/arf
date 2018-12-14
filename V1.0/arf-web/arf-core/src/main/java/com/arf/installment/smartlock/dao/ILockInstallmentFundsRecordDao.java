package com.arf.installment.smartlock.dao;

import java.util.List;

import com.arf.core.dao.BaseDao;
import com.arf.installment.smartlock.entity.LockInstallmentFundsRecord;

public interface ILockInstallmentFundsRecordDao extends BaseDao<LockInstallmentFundsRecord,Long>{

	public List<LockInstallmentFundsRecord> findByOrderNo(String orderNo);
	
		/**
	 * 根据款项编号查询
	 * @param userName
	 * @return
	 */
	public LockInstallmentFundsRecord findByFundsNo(String fundsNo);

	/**
	 * 查找未支付过期的款项
	 * @return
	 */
	public List<LockInstallmentFundsRecord> findFundsRecordDeadLine();
}
