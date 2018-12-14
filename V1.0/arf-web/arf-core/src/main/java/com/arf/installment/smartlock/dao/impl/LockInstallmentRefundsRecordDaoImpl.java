package com.arf.installment.smartlock.dao.impl;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.installment.smartlock.dao.ILockInstallmentRefundsRecordDao;
import com.arf.installment.smartlock.entity.LockInstallmentRefundsRecord;

@Repository("lockInstallmentRefundsRecordDao")
public class LockInstallmentRefundsRecordDaoImpl extends BaseDaoImpl<LockInstallmentRefundsRecord, Long>
		implements ILockInstallmentRefundsRecordDao {

}
