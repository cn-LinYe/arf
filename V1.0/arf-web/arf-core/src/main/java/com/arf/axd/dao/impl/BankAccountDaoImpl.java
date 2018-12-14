package com.arf.axd.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.arf.axd.dao.IBankAccountDao;
import com.arf.axd.entity.BankAccount;
import com.arf.axd.entity.BankAccount.Status;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("bankAccountDaoImpl")
public class BankAccountDaoImpl extends BaseDaoImpl<BankAccount,Long> implements IBankAccountDao{

	@Override
	public BankAccount findByTypeBankNumberStatus(BankAccount.Type type, String bankNumber,BankAccount.UserType userType,BankAccount.Status ...status) {
		StringBuffer hqlSb = new StringBuffer("from BankAccount where 1=1 ");
		if(type != null){
			hqlSb.append(" and type = :type ");
		}
		if(bankNumber != null){
			hqlSb.append(" and bankNumber = :bankNumber ");
		}
		if(status != null && status.length >0 ){
			hqlSb.append(" and status in(:status) ");
		}
		if(userType==null){
			hqlSb.append(" and (userType is null or userType =:userType)");
		}else{
			hqlSb.append(" and userType = :userType");
		}
		TypedQuery<BankAccount> query = entityManager.createQuery(hqlSb.toString(), BankAccount.class);
		
		if(type != null){
			query.setParameter("type", type.ordinal());
		}
		if(bankNumber != null){
			query.setParameter("bankNumber", bankNumber);
		}
		if(userType==null){
			query.setParameter("userType", BankAccount.UserType.user.ordinal());
		}else{
			query.setParameter("userType",userType.ordinal());
		}
		if(status != null && status.length >0 ){
			List<Integer> sList = new ArrayList<Integer>();
			for(BankAccount.Status s : status){
				sList.add(s.ordinal());
			}
			query.setParameter("status", sList);
		}
		
		try {
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public BankAccount findByBankNumber(String bankNumber,BankAccount.UserType userType){
		StringBuffer hql = new StringBuffer("from BankAccount where bankNumber = :bankNumber  and status = :status");
		if(userType==null){
			hql.append(" and (userType is null or userType = :userType) ");
		}else{
			hql.append(" and userType = :userType");
		}
		TypedQuery<BankAccount> query = entityManager.createQuery(hql.toString(), BankAccount.class);
		query.setParameter("bankNumber", bankNumber);
		query.setParameter("status", BankAccount.Status.Normal.ordinal());
		if(userType==null){
			query.setParameter("userType", BankAccount.UserType.user.ordinal());
		}else{
			query.setParameter("userType",userType.ordinal());
		}
		try{
			return query.getSingleResult();
		} catch (Exception e){
			return null;
		}
	}

	@Override
	public List<BankAccount> findByUserNameStatus(String userName, Status status) {
		String hql = "from BankAccount where userName = :userName and status = :status";
		TypedQuery<BankAccount> query = entityManager.createQuery(hql.toString(), BankAccount.class);
		query.setParameter("userName", userName);
		query.setParameter("status", status.ordinal());
		return query.getResultList();
	}

	@Override
	public List<BankAccount> findByUserName(String userName,BankAccount.UserType userType){
		StringBuffer hql = new StringBuffer("from BankAccount where userName = :userName and status = :status");
		if(userType==null){
			hql.append(" and (userType is null or userType=:userType)");
		}else{
			hql.append(" and userType = :userType");
		}
		TypedQuery<BankAccount> query = this.entityManager.createQuery(hql.toString(), BankAccount.class);
		query.setParameter("userName", userName);
		query.setParameter("status", BankAccount.Status.Normal.ordinal());
		if(userType==null){
			query.setParameter("userType", BankAccount.UserType.user.ordinal());
		}else{
			query.setParameter("userType",userType.ordinal());
		}
		List<BankAccount> list = query.getResultList();
		return list;
	}
}
