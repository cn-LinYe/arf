package com.arf.core.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.util.StringUtil;
import org.springframework.stereotype.Repository;

import com.arf.core.dao.TemporaryCarOrderDao;
import com.arf.core.entity.ParkRateRecordModel;
import com.arf.core.entity.TemporaryCarOrder;

/**
 * Dao - 临时车牌表
 * 
 * @author arf  dg
 * @version 4.0
 */
@Repository("temporaryCarOrderDaoImpl")
public class TemporaryCarOrderDaoImpl extends BaseDaoImpl<TemporaryCarOrder, Long> implements TemporaryCarOrderDao {

	@Override
	public TemporaryCarOrder selectByouttradeno(String out_trade_no) {
		try {
			String jpql = "select temporarycarorder from TemporaryCarOrder temporarycarorder where temporarycarorder.out_trade_no = :out_trade_no";
		List<TemporaryCarOrder> temporaryCarOrders=	entityManager.createQuery(jpql, TemporaryCarOrder.class).setParameter("out_trade_no", out_trade_no).getResultList();
		TemporaryCarOrder temporaryCarOrder=(temporaryCarOrders==null||temporaryCarOrders.size()<=0)?null:temporaryCarOrders.get(0);
			return temporaryCarOrder;
		} catch (Exception e) {
			return null;
		}
	}
	
}
