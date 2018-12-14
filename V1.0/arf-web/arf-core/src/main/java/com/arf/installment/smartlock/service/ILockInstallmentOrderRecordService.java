package com.arf.installment.smartlock.service;

import java.util.List;

import com.arf.core.service.BaseService;
import com.arf.installment.smartlock.dto.LockInstallmentOrderRecordDto;
import com.arf.installment.smartlock.entity.LockInstallmentOrderRecord;

public interface ILockInstallmentOrderRecordService extends BaseService<LockInstallmentOrderRecord, Long>{
	
	List<LockInstallmentOrderRecord> findByUserNameAndStatus(String userName,LockInstallmentOrderRecord.Status ...status);
	
	public List<LockInstallmentOrderRecordDto> findByUserName(String userName);
	
	LockInstallmentOrderRecord findByOrderNo(String orderNo);
}
