package com.arf.installment.smartlock.dao;

import java.util.List;

import com.arf.core.dao.BaseDao;
import com.arf.installment.smartlock.dto.LockInstallmentOrderRecordDto;
import com.arf.installment.smartlock.entity.LockInstallmentOrderRecord;

public interface ILockInstallmentOrderRecordDao extends BaseDao<LockInstallmentOrderRecord,Long>{

	public List<LockInstallmentOrderRecordDto> findByUserName(String userName);
}
