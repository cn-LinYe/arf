package com.arf.laundry.dao.impl;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.laundry.dao.ILaundryOrderRefundDao;
import com.arf.laundry.entity.LaundryOrderItems;
import com.arf.laundry.entity.LaundryOrderRefund;

@Repository("laundryOrderRefundDaoImpl")
public class LaundryOrderRefundDaoImpl extends BaseDaoImpl<LaundryOrderRefund, Long> implements ILaundryOrderRefundDao {

	@Override
	public LaundryOrderRefund findByOutTradeNo(String outTradeNo) {
		String hql = "from LaundryOrderRefund where outTradeNo = :outTradeNo";
		TypedQuery<LaundryOrderRefund> query = entityManager.createQuery(hql, LaundryOrderRefund.class);
		query.setParameter("outTradeNo", outTradeNo);
		try {
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	
}
