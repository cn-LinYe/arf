package com.arf.gas.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.arf.base.PageResult;
import com.alibaba.fastjson.JSON;
import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.gas.dao.IRefuelGasOrderDao;
import com.arf.gas.dto.RefuelGasOrderDto;
import com.arf.gas.entity.RefuelGasOrder.OrderStatus;
import com.arf.gas.entity.RefuelGasOrder;

@Repository("refuelGasOrderDaoImpl")
public class RefuelGasOrderDaoImpl extends BaseDaoImpl<RefuelGasOrder, Long> implements IRefuelGasOrderDao{
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public RefuelGasOrder findByOrderNo(String orderNo) {
		String hql = "from RefuelGasOrder where gasOrderNo = :orderNo";
		List<RefuelGasOrder> list = entityManager.createQuery(hql,RefuelGasOrder.class)
				.setParameter("orderNo", orderNo)
				.getResultList();
		if(CollectionUtils.isNotEmpty(list)){
			return list.get(0);
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<RefuelGasOrderDto> findByUserNameAndLicense(String userName ,String license,String gasOrderNo,Integer businessNum,Integer gasNum,Integer orderStatus) {
		StringBuffer sb =new StringBuffer("SELECT r.id as id,r.create_date as createDate ,r.user_name as userName ,c.card_number as cardNumber ,r.business_num as businessNum ,r.order_price as orderPrice,r.tubing_num as tubingNum,");
		sb.append(" r.gas_num as gasNum	,r.gas_order_no as gasOrderNo ,r.order_status as orderStatus ,r.gun_num as gunNum	 	,r.order_amount as orderAmount ,");
		sb.append(" r.discounts_amount as discountsAmount , r.pay_type as payType ,r.oli_type as oliType	,r.oli_quantity as oliQuantity ,r.license_plate_number as licensePlateNumber ,");
		sb.append(" s.gas_name as gasName ,c.card_amount as cardAmount ,p.business_name as businessName,");
		sb.append(" p.address as businessAddress ,p.business_pic as businessPic ,p.lat as lat,p.lng as lng ,p.contact_phone as businessPhone");
		sb.append(" FROM refuel_gas_order r");
		sb.append(" LEFT JOIN p_business p ON p.business_num =r.business_num");
		sb.append(" LEFT JOIN refuel_gas_card c ON r.user_name = c.user_name AND r.gas_num=c.gas_num");	
		sb.append(" LEFT JOIN refuel_gas_station s ON r.gas_num =s.gas_num WHERE 1=1");
		if (StringUtils.isNotBlank(userName)) {
			sb.append(" AND r.user_name =:userName ");	
		}
		if (StringUtils.isNotBlank(license)) {
			sb.append(" and r.license_plate_number =:license");
		}
		if (StringUtils.isNotBlank(gasOrderNo)) {
			sb.append(" and r.gas_order_no =:orderNo");
		}
		if (businessNum!=null) {
			sb.append(" and r.business_num =:businessNum");
		}
		if (gasNum!=null) {
			sb.append(" AND r.gas_num=:gasNum");
		}
		if (orderStatus!=null) {
			sb.append(" AND r.order_status=:orderStatus");
		}
		sb.append(" ORDER BY r.create_date DESC");
		Query query = this.entityManager.createNativeQuery(sb.toString());
		if (StringUtils.isNotBlank(userName)) {
			query.setParameter("userName", userName);
		}
		if (StringUtils.isNotBlank(license)) {
			query.setParameter("license", license);
		}
		if (StringUtils.isNotBlank(gasOrderNo)) {
			query.setParameter("orderNo", gasOrderNo);
		}
		if (businessNum!=null) {
			query.setParameter("businessNum", businessNum);
		}
		if (gasNum!=null) {
			query.setParameter("gasNum", gasNum);
		}
		if (orderStatus!=null) {
			query.setParameter("orderStatus", orderStatus);
		}
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String,Object>> mapResult =query.getResultList();
		List<RefuelGasOrderDto> dto =new ArrayList<RefuelGasOrderDto>();
		for (Map<String, Object> map: mapResult) {
			dto.add(JSON.parseObject(JSON.toJSONString(map), RefuelGasOrderDto.class));
		}
		return dto;
	}
	@Override
	public PageResult<Map<String, Object>> findByUserNameAndGasNum(String userName, Integer gasNum, Integer businessNum,
			Integer pageSize, Integer pageNo, Date startTime, Date endTime,Integer orderStatus) {
		StringBuffer countHql = new StringBuffer("select count(r.id) as COUNT from refuel_gas_order r");
		StringBuffer queryHql = new StringBuffer("select ");
		queryHql.append(" r.id as id,r.create_date as createDate ,r.user_name as userName ,r.card_number as cardNumber ,r.business_num as businessNum ,");
		queryHql.append(" r.gas_num as gasNum	,r.gas_order_no as gasOrderNo ,r.order_status as orderStatus ,r.gun_num as gunNum	 	,r.order_amount as orderAmount ,");
		queryHql.append(" r.discounts_amount as discountsAmount , r.pay_type as payType ,r.oli_type as oliType	,r.oli_quantity as oliQuantity ,r.license_plate_number as licensePlateNumber ,");
		
		queryHql.append(" r.order_price as orderPrice ,r.tubing_num as tubingNum,s.gas_name gasName,");
		queryHql.append(" b.business_name businessName,b.address businessAddress,b.business_pic businessPic,");
		queryHql.append(" b.business_description businessDescription,b.lat lat,b.lng lng");
		queryHql.append(" from refuel_gas_order r ");
		
		queryHql.append(" left join  refuel_gas_station s on s.gas_num=r.gas_num");
		queryHql.append(" left join  p_business b on b.business_num=r.business_num");
		queryHql.append(" where 1=1");
		
		countHql.append(" left join  refuel_gas_station s on s.gas_num=r.gas_num");
		countHql.append(" left join  p_business b on b.business_num=r.business_num");
		countHql.append(" where 1=1");
		
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
		}else{
			queryHql.append(" and r.order_status!=3");
			countHql.append(" and r.order_status!=3");
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
			logger.error("查询会员订单出错", e);
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
	public Integer findTotalOrderAmountByGasNum(Integer gasNum, Integer businessNum) {
		StringBuffer countHql = new StringBuffer("select sum(r.order_amount-r.discounts_amount) as totalOrderAmount from refuel_gas_order r");
		countHql.append(" where r.order_status=1 and r.gas_num =:gasNum and r.business_num =:businessNum");
		Query queryCount = this.entityManager.createNativeQuery(countHql.toString());
		queryCount.setParameter("gasNum", gasNum);
		queryCount.setParameter("businessNum", businessNum);
		int count = 0;
		try{
			Object total = queryCount.getSingleResult();
			if(total!=null){
				count = ((BigDecimal)total).intValue();
			}
		} catch (Exception e) {
			logger.error("查询加油站加油订单总金额出错", e);
		}
		return count;
	}
	@Override
	public List<RefuelGasOrder> findByOrderStatus(OrderStatus orderStatus, Integer gasNum) {
		StringBuffer sb =new StringBuffer("select o from RefuelGasOrder o where o.orderStatus =:orderStatus and o.gasNum=:gasNum");
		Query query =entityManager.createQuery(sb.toString());
		query.setParameter("orderStatus", orderStatus);
		query.setParameter("gasNum", gasNum);
		@SuppressWarnings("unchecked")
		List<RefuelGasOrder>list =query.getResultList();
		return list;
	}

}

















