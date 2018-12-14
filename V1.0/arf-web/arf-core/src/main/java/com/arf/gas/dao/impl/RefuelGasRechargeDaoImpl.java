package com.arf.gas.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.arf.base.PageResult;
import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.gas.dao.IRefuelGasRechargeDao;
import com.arf.gas.entity.RefuelGasRecharge;

@Repository("refuelGasRechargeDao")
public class RefuelGasRechargeDaoImpl extends BaseDaoImpl<RefuelGasRecharge, Long> implements IRefuelGasRechargeDao{

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public RefuelGasRecharge findByOrderNo(String orderNo) {
		StringBuffer queryHql = new StringBuffer(" from RefuelGasRecharge where rechargeOrderNo=:orderNo");
		
		TypedQuery<RefuelGasRecharge> typedQuery = this.entityManager.createQuery(queryHql.toString(), RefuelGasRecharge.class);
		typedQuery.setParameter("orderNo", orderNo);
		
		List<RefuelGasRecharge> list =typedQuery.getResultList();
		try {
			return list.isEmpty()?null:list.get(0);
		} catch (Exception e) {
			logger.error("根据订单编号查询充值订单出错", e);
			return null;
		}
	}

	@Override
	public PageResult<Map<String, Object>> findRechargeByUserNameAndGasNum(String userName, Integer gasNum,
			Integer businessNum, Integer pageSize, Integer pageNo, Date startTime, Date endTime, Integer orderStatus) {
		StringBuffer countHql = new StringBuffer("select count(r.id) as COUNT from refuel_gas_recharge r");
		StringBuffer queryHql = new StringBuffer("select ");
		queryHql.append(" r.id as id,r.create_date as createDate ,r.user_name as userName ,r.card_number as cardNumber ,r.business_num as businessNum ,");
		queryHql.append(" r.gas_num as gasNum	,r.recharge_order_no as rechargeOrderNo ,r.order_status as orderStatus ,r.recharge_amount as rechargeAmount ,");
		queryHql.append(" r.gift_amount as giftAmount , r.pay_type as payType ,r.pay_date as payDate ,r.business_payment as businessPayment ,");
		
		queryHql.append(" s.gas_name gasName,");
		queryHql.append(" b.business_name businessName,b.address businessAddress,b.business_pic businessPic,");
		queryHql.append(" b.business_description businessDescription,b.lat lat,b.lng lng");
		queryHql.append(" from refuel_gas_recharge r ");
		
		queryHql.append(" left join  refuel_gas_station s on s.gas_num=r.gas_num");
		queryHql.append(" left join  p_business b on b.business_num=r.business_num");
		queryHql.append(" where 1=1 ");
		
		countHql.append(" left join  refuel_gas_station s on s.gas_num=r.gas_num");
		countHql.append(" left join  p_business b on b.business_num=r.business_num");
		countHql.append(" where 1=1 ");
		
		if(StringUtils.isNotBlank(userName)){
			queryHql.append(" and r.user_name =:userName");
			countHql.append(" and r.user_name =:userName");
		}
		if(gasNum!=null){
			queryHql.append(" and r.gas_num =:gasNum");
			countHql.append(" and r.gas_num =:gasNum");
		}
		if(businessNum!=null){
			queryHql.append(" and r.business_num =:businessNum");
			countHql.append(" and r.business_num =:businessNum");
		}
		if(startTime!=null && endTime!=null){
			queryHql.append(" and r.create_date >=:startTime");
			countHql.append(" and r.create_date >=:startTime");
			queryHql.append(" and r.create_date <=:endTime");
			countHql.append(" and r.create_date <=:endTime");
		}
		if(orderStatus!=null){
			queryHql.append(" and r.order_status =:orderStatus");
			countHql.append(" and r.order_status =:orderStatus");
		}
		queryHql.append(" order by r.create_date desc");
		Query typedQuery = this.entityManager.createNativeQuery(queryHql.toString());
		Query queryCount = this.entityManager.createNativeQuery(countHql.toString());
		
		if(StringUtils.isNotBlank(userName)){
			typedQuery.setParameter("userName", userName);
			queryCount.setParameter("userName", userName);
		}
		if(gasNum!=null){
			typedQuery.setParameter("gasNum", gasNum);
			queryCount.setParameter("gasNum", gasNum);
		}
		if(businessNum!=null){
			typedQuery.setParameter("businessNum", businessNum);
			queryCount.setParameter("businessNum", businessNum);
		}
		if(startTime!=null && endTime!=null){
			typedQuery.setParameter("startTime", startTime);
			queryCount.setParameter("startTime", startTime);
			typedQuery.setParameter("endTime", endTime);
			queryCount.setParameter("endTime", endTime);
		}
		if(orderStatus!=null){
			typedQuery.setParameter("orderStatus", orderStatus);
			queryCount.setParameter("orderStatus", orderStatus);
		}
		
		int count = 0;
		try{
			count = ((BigInteger)queryCount.getSingleResult()).intValue();
		} catch (Exception e) {
			logger.error("查询会员充值订单出错", e);
		}
		PageResult<Map<String,Object>> pageResult = new PageResult<Map<String,Object>>();
		//查询所有
		pageResult.setTotalNum(count);
		//分页查询
		if(pageNo==null || pageSize==null){
			pageNo=1;
			pageSize=count;
		}
		typedQuery.setFirstResult((pageNo-1)*pageSize);
		typedQuery.setMaxResults(pageSize);
		typedQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> rows = typedQuery.getResultList();
		pageResult.setList(rows);
		
		return pageResult;
	}

	@Override
	public List<RefuelGasRecharge> findByCardNumber(List<String> cardNumbers) {
		if (CollectionUtils.isEmpty(cardNumbers)) {
			return null;
		}
		StringBuffer queryHql = new StringBuffer(" from RefuelGasRecharge where cardNumber in (:cardNumbers)");
		
		TypedQuery<RefuelGasRecharge> typedQuery = this.entityManager.createQuery(queryHql.toString(), RefuelGasRecharge.class);
		typedQuery.setParameter("cardNumbers", cardNumbers);
		List<RefuelGasRecharge> list = new ArrayList<RefuelGasRecharge>();
		
		try {
			 list =typedQuery.getResultList();
		} catch (Exception e) {
			logger.error("根据油卡编号查询充值订单出错", e);
			return null;
		}
		return list;
	}

}
