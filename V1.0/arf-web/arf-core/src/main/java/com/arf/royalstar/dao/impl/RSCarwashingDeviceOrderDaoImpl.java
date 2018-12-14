package com.arf.royalstar.dao.impl;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.royalstar.dao.RSCarwashingDeviceOrderDao;
import com.arf.royalstar.entity.RSCarwashingDeviceOrder;

@Repository("rsCarwashingDeviceOrderDaoImpl")
public class RSCarwashingDeviceOrderDaoImpl extends BaseDaoImpl<RSCarwashingDeviceOrder, Long>
		implements RSCarwashingDeviceOrderDao {

	@Override
	public RSCarwashingDeviceOrder findByOrderNo(String orderNo){
		String hql = "from RSCarwashingDeviceOrder cdo where cdo.orderNo = :orderNo";
		TypedQuery<RSCarwashingDeviceOrder> query = this.entityManager.createQuery(hql,RSCarwashingDeviceOrder.class);
		query.setParameter("orderNo", orderNo);
		return query.getSingleResult();
	}
}
