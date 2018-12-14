package com.arf.installment.smartlock.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.installment.smartlock.dao.ILockInstallmentFundsRecordDao;
import com.arf.installment.smartlock.entity.LockInstallmentFundsRecord;
import com.arf.installment.smartlock.entity.LockInstallmentFundsRecord.DeadlineStatus;
import com.arf.installment.smartlock.entity.LockInstallmentFundsRecord.PayStatus;

@Repository("lockInstallmentFundsRecordDao")
public class LockInstallmentFundsRecordDaoImpl extends BaseDaoImpl<LockInstallmentFundsRecord,Long> implements ILockInstallmentFundsRecordDao{

	@Override
	public List<LockInstallmentFundsRecord> findByOrderNo(String orderNo){
		String hql = "from LockInstallmentFundsRecord where orderNo = :orderNo";
		TypedQuery<LockInstallmentFundsRecord> typedQuery = super.entityManager.createQuery(hql,LockInstallmentFundsRecord.class);
		typedQuery.setParameter("orderNo", orderNo);
		return typedQuery.getResultList();
	}
	
	@Override
	public LockInstallmentFundsRecord findByFundsNo(String fundsNo) {
		StringBuffer hql = new StringBuffer("from LockInstallmentFundsRecord where fundsNo = :fundsNo");
		TypedQuery<LockInstallmentFundsRecord> typedQuery = super.entityManager.createQuery(hql.toString(), LockInstallmentFundsRecord.class);
		typedQuery.setParameter("fundsNo", fundsNo);
		return typedQuery.getResultList().get(0);
	}

	@Override
	public List<LockInstallmentFundsRecord> findFundsRecordDeadLine() {
		StringBuffer hql = new StringBuffer("from LockInstallmentFundsRecord ");
		hql.append("where payStatus = :payStatus ");
		hql.append("and deadlineStatus = :deadlineStatus ");
		hql.append("and payDeadline < now() ");
		TypedQuery<LockInstallmentFundsRecord> query = super.entityManager.createQuery(hql.toString(), LockInstallmentFundsRecord.class);
		query.setParameter("payStatus", PayStatus.UNPAID);
		query.setParameter("deadlineStatus", DeadlineStatus.NORMAL);
		return query.getResultList();
	}
}
