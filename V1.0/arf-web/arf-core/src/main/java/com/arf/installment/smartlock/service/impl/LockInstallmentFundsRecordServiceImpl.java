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
import com.arf.installment.smartlock.dao.ILockInstallmentFundsRecordDao;
import com.arf.installment.smartlock.entity.LockInstallmentFundsRecord;
import com.arf.installment.smartlock.service.ILockInstallmentFundsRecordService;

@Service("lockInstallmentFundsRecordService")
public class LockInstallmentFundsRecordServiceImpl extends BaseServiceImpl<LockInstallmentFundsRecord,Long> implements ILockInstallmentFundsRecordService{

	@Resource(name="lockInstallmentFundsRecordDao")
	ILockInstallmentFundsRecordDao lockInstallmentFundsRecordDao;
	
	@Override
	protected BaseDao<LockInstallmentFundsRecord, Long> getBaseDao() {
		return lockInstallmentFundsRecordDao;
	}

	@Override
	public List<LockInstallmentFundsRecord> findByOrderNo(String orderNo){
		return lockInstallmentFundsRecordDao.findByOrderNo(orderNo);
	}
	
	@Override
	public LockInstallmentFundsRecord findByFundsNo(String fundsNo) {
		List<LockInstallmentFundsRecord> list = this.findList(null,(List<Order>)null, 
				new Filter("fundsNo",Operator.eq,fundsNo));
		return CollectionUtils.isEmpty(list)?null:list.get(0);
	}

	@Override
	public List<LockInstallmentFundsRecord> findByFundsNos(String[] fundsNos) {
		if(fundsNos == null || fundsNos.length == 0){
			return null;
		}
		List<LockInstallmentFundsRecord> list = this.findList(null,(List<Order>)null, 
				new Filter("fundsNo",Operator.in,Arrays.asList(fundsNos)));
		return list;
	}

	@Override
	public List<LockInstallmentFundsRecord> findFundsRecordDeadLine() {
		return lockInstallmentFundsRecordDao.findFundsRecordDeadLine();
	}
}
