package com.arf.ecc.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.ecc.dao.ISettlementParamDao;
import com.arf.ecc.entity.SettlementParam;

@Repository("settlementParamDaoImpl")
public class SettlementParamDaoImpl extends BaseDaoImpl<SettlementParam, Long> implements ISettlementParamDao {
	
	@Override
	public SettlementParam findByNumber(String communityNumber, SettlementParam.AssignType assignType){
		StringBuffer sql =new StringBuffer(" from SettlementParam where number=:number and assignType =:assignType and status = 0");
		TypedQuery<SettlementParam> query = entityManager.createQuery(sql.toString(),SettlementParam.class);
		query.setParameter("number", communityNumber);
		query.setParameter("assignType", assignType.toString());
		List<SettlementParam> list ;
		try {
			 list = query.getResultList();
		} catch (Exception e) {
			return null;
		}
		
		return list.isEmpty()?null:list.get(0);
	}
}

