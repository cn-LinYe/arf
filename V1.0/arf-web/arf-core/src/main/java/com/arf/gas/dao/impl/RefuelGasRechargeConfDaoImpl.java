package com.arf.gas.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.gas.dao.IRefuelGasRechargeConfDao;
import com.arf.gas.entity.RefuelGasRechargeConf;
import com.arf.gas.entity.RefuelGasRechargeConf.Status;

@Repository("refuelGasRechargeConfDaoImpl")
public class RefuelGasRechargeConfDaoImpl extends BaseDaoImpl<RefuelGasRechargeConf, Long> implements IRefuelGasRechargeConfDao{

	@Override
	public List<RefuelGasRechargeConf> findByGasNum(Integer gasNum, Status status) {
		StringBuffer sql=new StringBuffer("from RefuelGasRechargeConf where gasNum=:gasNum ");
		if (status!=null) {
			sql.append(" and status="+status.ordinal());
		}
		Query query =entityManager.createQuery(sql.toString(),RefuelGasRechargeConf.class);
		query.setParameter("gasNum",gasNum);
		@SuppressWarnings("unchecked")
		List<RefuelGasRechargeConf> list =query.getResultList();
		return list;
	}
}
