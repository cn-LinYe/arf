package com.arf.gas.dao.impl;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.arf.base.PageResult;
import com.arf.core.Page;
import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.gas.dao.IRefuelGasSupplyOrderDao;
import com.arf.gas.entity.RefuelGasSupplyOrder;
import com.arf.gas.entity.RefuelGasSupplyOrder.OrderStatus;

@Repository("refuelGasSupplyOrderDaoImpl")
public class RefuelGasSupplyOrderDaoImpl extends BaseDaoImpl<RefuelGasSupplyOrder, Long> implements IRefuelGasSupplyOrderDao{

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public List<RefuelGasSupplyOrder> findByGasNum(Integer gasNum, Integer businessNum, List<RefuelGasSupplyOrder.OrderStatus> orderStatus, Date startTime, Date endTime) {
		StringBuffer queryHql = new StringBuffer(" from RefuelGasSupplyOrder where 1=1");
		queryHql.append(" and gasNum=:gasNum and businessNum=:businessNum");
		if(orderStatus!=null){
			queryHql.append(" and orderStatus in (:orderStatus)");
		}
		if(startTime!=null && endTime!=null){
			queryHql.append(" and supply_date >=:startTime");
			queryHql.append(" and supply_date <=:endTime");
		}
		TypedQuery<RefuelGasSupplyOrder> typedQuery = this.entityManager.createQuery(queryHql.toString(), RefuelGasSupplyOrder.class);
		
		typedQuery.setParameter("gasNum", gasNum);
		typedQuery.setParameter("businessNum", businessNum);
		if(orderStatus!=null){
			typedQuery.setParameter("orderStatus", orderStatus);
		}	
		if(startTime!=null && endTime!=null){
			typedQuery.setParameter("startTime", startTime);
			typedQuery.setParameter("endTime", endTime);
		}
		List<RefuelGasSupplyOrder> list =typedQuery.getResultList();
		return list;
	}

	@Override
	public PageResult<RefuelGasSupplyOrder> findByCondition(Integer gasNum, Integer businessNum,
			OrderStatus orderStatus, String startTime, String endTime, Integer pageSize, Integer pageNo) {
		SimpleDateFormat sim =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		StringBuffer queryHql = new StringBuffer(" from RefuelGasSupplyOrder where 1=1");
		queryHql.append(" and gasNum=:gasNum and businessNum=:businessNum");
		StringBuffer sqlCount=new StringBuffer(" SELECT COUNT(*) FROM refuel_gas_supply_order WHERE 1=1  and gas_num=:gasNum and business_num=:businessNum");
		if(orderStatus!=null){
			queryHql.append(" and orderStatus ="+orderStatus.ordinal());
			sqlCount.append(" AND order_status="+orderStatus.ordinal());
		}
		if(startTime!=null ){
			queryHql.append(" and supplyDate >=:startTime");
			sqlCount.append("  AND supply_date>=:startTime");
		}
		if(endTime!=null){
			queryHql.append(" and supplyDate <=:endTime");
			sqlCount.append("  AND supply_date<=:endTime");
		}
		queryHql.append(" order by createDate desc");
		TypedQuery<RefuelGasSupplyOrder> typedQuery = this.entityManager.createQuery(queryHql.toString(), RefuelGasSupplyOrder.class);
		Query query=entityManager.createNativeQuery(sqlCount.toString());
		typedQuery.setParameter("gasNum", gasNum);
		typedQuery.setParameter("businessNum", businessNum);
		query.setParameter("gasNum", gasNum);
		query.setParameter("businessNum", businessNum);
		if(endTime!=null){
			Date end =null;
			try {
				end =sim.parse(endTime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			typedQuery.setParameter("endTime", end);
			query.setParameter("endTime", end);
		}
		if(startTime!=null){
			Date start =null;
			try {
				start =sim.parse(startTime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			typedQuery.setParameter("startTime", start);
			query.setParameter("startTime", start);
		}
		if (pageNo!=null&&pageSize!=null ) {
			typedQuery.setMaxResults(pageSize);
			typedQuery.setFirstResult((pageNo-1)*pageSize);
		}
		int count =0;
		try {
			count =((BigInteger) query.getSingleResult()).intValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<RefuelGasSupplyOrder> list =typedQuery.getResultList();
		PageResult<RefuelGasSupplyOrder> pageList=new PageResult<>(list, count);
		return pageList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public RefuelGasSupplyOrder findByOrderNo(String orderNo) {
		String sql ="from RefuelGasSupplyOrder where supplyOrderNo =:orderNo";
		Query query =entityManager.createQuery(sql,RefuelGasSupplyOrder.class);
		query.setParameter("orderNo", orderNo);
		List<RefuelGasSupplyOrder> list = null;
		try {
			list =query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CollectionUtils.isEmpty(list)?null:list.get(0);
	}
}











