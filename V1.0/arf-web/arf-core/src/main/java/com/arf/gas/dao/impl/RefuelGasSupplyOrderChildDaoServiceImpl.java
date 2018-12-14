package com.arf.gas.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.gas.dao.IRefuelGasSupplyOrderChildDao;
import com.arf.gas.entity.RefuelGasSupplyOrderChild;

@Repository("refuelGasSupplyOrderChildDaoServiceImpl")
public class RefuelGasSupplyOrderChildDaoServiceImpl extends BaseDaoImpl<RefuelGasSupplyOrderChild, Long> implements IRefuelGasSupplyOrderChildDao{

	@Override
	public List<RefuelGasSupplyOrderChild> findByOrderNo(String orderNo) {
		String sql ="from RefuelGasSupplyOrderChild where supplyOrderNo=:orderNo";
		Query query =entityManager.createQuery(sql,RefuelGasSupplyOrderChild.class);
		query.setParameter("orderNo", orderNo);
		@SuppressWarnings("unchecked")
		List<RefuelGasSupplyOrderChild> list =query.getResultList();
		return list;
	}
}
