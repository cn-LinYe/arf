package com.arf.gas.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.gas.dao.IRefuelGasTubingGunDao;
import com.arf.gas.entity.RefuelGasTubingGun;

@Repository("refuelGasTubingGunDaoImpl")
public class RefuelGasTubingGunDaoImpl extends BaseDaoImpl<RefuelGasTubingGun, Long> implements IRefuelGasTubingGunDao{

	@Override
	public List<RefuelGasTubingGun> findByGasNum(Integer gasNum, Integer businessNum,Integer tubingNum) {
		StringBuffer queryHql = new StringBuffer(" from RefuelGasTubingGun where gasNum=:gasNum and businessNum=:businessNum");
		if (tubingNum!=null) {
			queryHql.append(" and tubingNum=:tubingNum");
		}	
		TypedQuery<RefuelGasTubingGun> typedQuery = this.entityManager.createQuery(queryHql.toString(), RefuelGasTubingGun.class);
							  
		typedQuery.setParameter("gasNum", gasNum);
		typedQuery.setParameter("businessNum", businessNum);
		if (tubingNum!=null) {
			typedQuery.setParameter("tubingNum", tubingNum.byteValue());
		}							 
		List<RefuelGasTubingGun> list =typedQuery.getResultList();
		return list;
	}
}
