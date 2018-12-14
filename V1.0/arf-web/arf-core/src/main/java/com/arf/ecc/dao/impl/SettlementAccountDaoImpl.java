package com.arf.ecc.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.ecc.dao.ISettlementAccountDao;
import com.arf.ecc.entity.SettlementAccount;

@Repository("settlementAccountDaoImpl")
public class SettlementAccountDaoImpl extends BaseDaoImpl<SettlementAccount, Long> implements ISettlementAccountDao {

	@Override
	public SettlementAccount findByNumber(String number,SettlementAccount.Type type){
		StringBuffer sql =new StringBuffer(" from SettlementAccount where number=:number and type = :type");
		TypedQuery<SettlementAccount> query = entityManager.createQuery(sql.toString(),SettlementAccount.class);
		query.setParameter("number", number);
		query.setParameter("type", type.ordinal());
		List<SettlementAccount> list ;
		try {
			 list = query.getResultList();
		} catch (Exception e) {
			return null;
		}
		
		return list.isEmpty()?null:list.get(0);
	}
}
