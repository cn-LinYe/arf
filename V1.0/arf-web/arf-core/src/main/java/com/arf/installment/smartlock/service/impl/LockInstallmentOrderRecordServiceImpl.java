package com.arf.installment.smartlock.service.impl;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.arf.core.Filter;
import com.arf.core.Filter.Operator;
import com.arf.core.Order;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.installment.smartlock.dao.ILockInstallmentOrderRecordDao;
import com.arf.installment.smartlock.dto.LockInstallmentOrderRecordDto;
import com.arf.installment.smartlock.entity.LockInstallmentOrderRecord;
import com.arf.installment.smartlock.entity.LockInstallmentOrderRecord.Status;
import com.arf.installment.smartlock.service.ILockInstallmentOrderRecordService;

@Service("lockInstallmentOrderRecordService")
public class LockInstallmentOrderRecordServiceImpl extends BaseServiceImpl<LockInstallmentOrderRecord, Long> implements ILockInstallmentOrderRecordService{

	@Resource(name = "lockInstallmentOrderRecordDao")
	private ILockInstallmentOrderRecordDao lockInstallmentOrderRecordDao;
	
	@Override
	protected BaseDao<LockInstallmentOrderRecord, Long> getBaseDao() {
		return lockInstallmentOrderRecordDao;
	}

	@Override
	public List<LockInstallmentOrderRecord> findByUserNameAndStatus(String userName, Status ...status) {
		return this.findList(null,(List<Order>)null, 
				new Filter("userName",Operator.eq,userName),
				new Filter("status",Operator.in,Arrays.asList(status)));
	}

	@Override
	public List<LockInstallmentOrderRecordDto> findByUserName(String userName){
		return lockInstallmentOrderRecordDao.findByUserName(userName);
	}
	
	@Override
	public LockInstallmentOrderRecord findByOrderNo(String orderNo) {
		List<LockInstallmentOrderRecord> list = this.findList(null,(List<Order>)null, 
				new Filter("orderNo",Operator.eq,orderNo));
		return CollectionUtils.isEmpty(list)?null:list.get(0);
	}
}
