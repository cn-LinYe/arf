package com.arf.gas.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.gas.dao.IRefuelGasRewardCardConfDao;
import com.arf.gas.entity.RefuelGasRewardCardConf;
import com.arf.gas.entity.RefuelGasRewardCardConf.PolicyType;

@Repository("refuelGasRewardCardConfDao")
public class RefuelGasRewardCardConfDaoImpl extends BaseDaoImpl<RefuelGasRewardCardConf, Long> implements IRefuelGasRewardCardConfDao{

	@Override
	public List<RefuelGasRewardCardConf> findByGasNumAndPolicyType(Integer gasNum, Integer businessNum,
			PolicyType policyType) {
		StringBuffer queryHql = new StringBuffer(" from RefuelGasRewardCardConf where 1=1 and status=:status");
		queryHql.append(" and gasNum=:gasNum and businessNum=:businessNum" );
		if (policyType!=null) {
			queryHql.append(" and policyType=:policyType");
		}
		queryHql.append(" order by cardAmount desc");
		TypedQuery<RefuelGasRewardCardConf> typedQuery = this.entityManager.createQuery(queryHql.toString(), RefuelGasRewardCardConf.class);
		typedQuery.setParameter("gasNum", gasNum);
		typedQuery.setParameter("businessNum", businessNum);
		typedQuery.setParameter("status", RefuelGasRewardCardConf.Status.Active);
		if(policyType!=null){
			typedQuery.setParameter("policyType", policyType);
		}
		List<RefuelGasRewardCardConf> list =typedQuery.getResultList();
		return list;
	}

}
