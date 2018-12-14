package com.arf.axd.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.arf.axd.dao.IAccountWithdrawRecordDao;
import com.arf.axd.entity.AccountWithdrawRecord;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("accountWithdrawRecordDaoImpl")
public class AccountWithdrawRecordDaoImpl extends BaseDaoImpl<AccountWithdrawRecord, Long>
		implements IAccountWithdrawRecordDao {
	
	@Override
	public List<AccountWithdrawRecord> findByUserName(String userName){
		StringBuffer hql = new StringBuffer("from AccountWithdrawRecord where userName = :userName");
		TypedQuery<AccountWithdrawRecord> query = this.entityManager.createQuery(hql.toString(), AccountWithdrawRecord.class);
		query.setParameter("userName", userName);
		List<AccountWithdrawRecord> list = query.getResultList();
		return list;
	}

	@Override
	public List<AccountWithdrawRecord> findWithdrawingByUserName(String userName, String bankNumber) {
		//状态,0:申请中,1:审核通过,2:审核驳回,3:打款成功,4:打款失败
		StringBuffer hql = new StringBuffer("from AccountWithdrawRecord where userName = :userName and bankNumber = :bankNumber and (status = 0 or status = 1)");
		TypedQuery<AccountWithdrawRecord> query = this.entityManager.createQuery(hql.toString(), AccountWithdrawRecord.class);
		query.setParameter("userName", userName);
		query.setParameter("bankNumber", bankNumber);
		List<AccountWithdrawRecord> list = query.getResultList();
		return list;
	}
	
}
