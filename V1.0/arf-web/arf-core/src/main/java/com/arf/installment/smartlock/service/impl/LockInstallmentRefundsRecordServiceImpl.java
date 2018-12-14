package com.arf.installment.smartlock.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.installment.smartlock.dao.ILockInstallmentRefundsRecordDao;
import com.arf.installment.smartlock.entity.LockInstallmentRefundsRecord;
import com.arf.installment.smartlock.service.ILockInstallmentRefundsRecordService;

@Service("lockInstallmentRefundsRecordService")
public class LockInstallmentRefundsRecordServiceImpl extends BaseServiceImpl<LockInstallmentRefundsRecord, Long>
		implements ILockInstallmentRefundsRecordService {

	@Resource(name="lockInstallmentRefundsRecordDao")
	private ILockInstallmentRefundsRecordDao lockInstallmentRefundsRecordDao;
	
	@Override
	protected BaseDao<LockInstallmentRefundsRecord, Long> getBaseDao() {
		return lockInstallmentRefundsRecordDao;
	}
}
