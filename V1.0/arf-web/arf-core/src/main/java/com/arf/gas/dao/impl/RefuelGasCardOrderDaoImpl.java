package com.arf.gas.dao.impl;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.aliyun.common.utils.DateUtil;
import com.arf.base.PageResult;
import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.gas.dao.IRefuelGasCardOrderDao;
import com.arf.gas.entity.RefuelGasCardOrder;
import com.arf.platform.utils.DateUtils;

@Repository("refuelGasCardOrderDaoImpl")
public class RefuelGasCardOrderDaoImpl extends BaseDaoImpl<RefuelGasCardOrder,Long> implements IRefuelGasCardOrderDao{

	@Override
	public RefuelGasCardOrder findByOrderNo(String orderNo) {
		String hql = "from RefuelGasCardOrder where orderNo = :orderNo";
		List<RefuelGasCardOrder> list = entityManager.createQuery(hql,RefuelGasCardOrder.class).setParameter("orderNo", orderNo).getResultList();
		if(CollectionUtils.isNotEmpty(list)){
			return list.get(0);
		}
		return null;
	}

	@Override
	public RefuelGasCardOrder findByCarNumBusiness(Integer gunNum, String userName) {
		String hql = "from RefuelGasCardOrder where gunNum=:gunNum and userName=:userName";
		List<RefuelGasCardOrder> list = entityManager.createQuery(hql,RefuelGasCardOrder.class).setParameter("gunNum", gunNum).setParameter("userName", userName).getResultList();
		if(CollectionUtils.isNotEmpty(list)){
			return list.get(0);
		}
		return null;
	}

	@Override
	public PageResult<Map<String,Object>> findByCondition(Integer gasNum,Integer audit,String orderNo, Integer orderStatus, String memberUserName,
			String startTime, String endTime, Integer pageNo, Integer pageSize) {
		SimpleDateFormat sim =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		StringBuffer sb =new StringBuffer("SELECT o.user_reward_amount as userRewardAmount, o.create_date as createDate,o.modify_date as modifyDate,o.order_no as orderNo,o.user_name as userName,o.card_amount as cardAmount,o.pay_tpye as payTpye, o.order_status as orderStatus ,o.gun_num as gunNum,o.business_num as businessNum,o.id as id ,o.reward_amount as rewardAmount");
		sb.append(" FROM refuel_gas_card_order o ");
		sb.append(" INNER  JOIN refuel_gas_member r ON r.user_name=o.user_name and r.gas_num=o.gun_num where 1=1");
		StringBuffer sql=new StringBuffer("SELECT COUNT(*) FROM refuel_gas_card_order o");
		sql.append(" INNER  JOIN refuel_gas_member r ON r.user_name=o.user_name and r.gas_num=o.gun_num where 1=1");

		if (gasNum!=null) {
			sb.append(" and o.gun_num=:gasNum");
			sql.append(" AND o.gun_num=:gasNum");
		}
		if (StringUtils.isNotBlank(orderNo)) {
			sb.append(" and o.order_no=:orderNo");
			sql.append(" AND o.order_no=:orderNo");
		}
		if (orderStatus!=null) {
			sb.append(" and o.order_status="+orderStatus);
			sql.append(" AND o.order_status="+orderStatus);
		}
		if (memberUserName!=null) {
			sb.append(" and o.user_name=:userName");
			sql.append(" AND o.user_name=:userName");
		}
		if (StringUtils.isNotBlank(startTime)) {
			sb.append(" and o.create_date>=:startTime");
			sql.append(" AND o.create_date>=:startTime");
		}
		if (StringUtils.isNotBlank(endTime)) {
			sb.append(" and o.create_date<=:endTime");
			sql.append(" AND o.create_date<=:endTime");
		}
		sb.append(" and LENGTH(o.user_name)<=11");
		sql.append(" AND LENGTH(o.user_name)<=11");
		if (audit!=null) {
			if (audit==0) {
				sb.append(" and(r.member_status in('0','5','7','9') or (r.member_status in ('8','6') AND o.order_status ='0'))");
				sql.append(" and(r.member_status in('0','5','7','9') or (r.member_status in ('8','6') AND o.order_status ='0'))");
			}
			if (audit==1) {
				sb.append(" and (r.member_status ='3' or (r.member_status in ('8','6') AND o.order_status ='1'))");
				sql.append(" and (r.member_status ='3' or (r.member_status in ('8','6') AND o.order_status ='1'))");
			}
			if (audit==2) {
				sb.append(" and r.member_status =1");
				sql.append(" and r.member_status =1");
			}
		}
		sb.append(" order by o.create_date desc");
		Query query=entityManager.createNativeQuery(sb.toString());
		Query queryCount=entityManager.createNativeQuery(sql.toString());
		
		if (gasNum!=null) {
			query.setParameter("gasNum", gasNum);
			queryCount.setParameter("gasNum", gasNum);
		}
		if (StringUtils.isNotBlank(orderNo)) {
			query.setParameter("orderNo", orderNo);
			queryCount.setParameter("orderNo", orderNo);
		}
		if (memberUserName!=null) {
			query.setParameter("userName", memberUserName);
			queryCount.setParameter("userName", memberUserName);
		}
		if (StringUtils.isNotBlank(startTime)) {
			Date start=null;
			try {
			 start=sim.parse(startTime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			query.setParameter("startTime", start);
			queryCount.setParameter("startTime", start);
		}
		if (StringUtils.isNotBlank(endTime)) {
			Date end=null;
			try {
				end=sim.parse(endTime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			query.setParameter("endTime", end);
			queryCount.setParameter("endTime", end);
		}
		if (pageNo!=null&&pageSize!=null) {
			query.setMaxResults(pageSize);
			query.setFirstResult((pageNo-1)*pageSize);
		}
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String,Object>> list=query.getResultList();
		int count=0;
		try {
		count=((BigInteger)queryCount.getSingleResult()).intValue();		
		} catch (Exception e) {
			e.printStackTrace();
		}
		PageResult<Map<String,Object>> result = new PageResult<Map<String,Object>>(list,count);
		return result;
	}

	@Override
	public Map<String, Object> statisticsCardAmountAndNumber(String startTime, String endTime,Integer gasNum) {
		SimpleDateFormat sim =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		StringBuffer sb =new StringBuffer("SELECT COUNT(1)  as totlalNumber, IFNULL(sum(o.card_amount),0)  as totalAmount  FROM  refuel_gas_card_order o WHERE o.order_status ='1' ");
		sb.append(" and o.gun_num=:gasNum");
		if (StringUtils.isNotBlank(startTime)) {
			sb.append(" and o.modify_date>=:startTime");
		}
		if (StringUtils.isNotBlank(endTime)) {
			sb.append("  and o.modify_date<=:endTime");
		}
		Query query =this.entityManager.createNativeQuery(sb.toString());
			try {
				if (StringUtils.isNotBlank(startTime)) {
					query.setParameter("startTime",sim.parse(startTime));
				}
				if (StringUtils.isNotBlank(endTime)) {
				query.setParameter("endTime",sim.parse(endTime) );
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			query.setParameter("gasNum",gasNum);
	
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		Map<String,Object> map=(Map<String, Object>) query.getSingleResult();
		return map;
	}
	
	@Override
	public Map<String, Object> statisticsRefulesMember(String startTime, String endTime,Integer gasNum) {
		SimpleDateFormat sim =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		StringBuffer sb =new StringBuffer("SELECT COUNT(*) as pendingCount,");
		sb.append(" (SELECT COUNT(*) FROM refuel_gas_card_order o INNER JOIN refuel_gas_member r ON r.user_name =o.user_name and r.gas_num=o.gun_num ");
		sb.append(" WHERE 1=1  and (r.member_status ='3' or (r.member_status in ('8','6') AND o.order_status ='1'))  and o.gun_num=:gun_num");
		if (StringUtils.isNotBlank(startTime)) {
			sb.append(" and o.modify_date>=:startTime");
		}		
		if (StringUtils.isNotBlank(endTime)) {
			sb.append("  and o.modify_date<=:endTime");
		}
		sb.append(" )as pendingAuditCount,");
		
		sb.append(" (SELECT COUNT(*) FROM refuel_gas_card_order o INNER JOIN refuel_gas_member r ON r.user_name =o.user_name and r.gas_num=o.gun_num ");
		sb.append(" WHERE r.member_status ='1'  and o.gun_num=:gun_num and r.gas_num=o.gun_num ");
		if (StringUtils.isNotBlank(startTime)) {
			sb.append(" and o.modify_date>=:startTime");
		}		
		if (StringUtils.isNotBlank(endTime)) {
			sb.append("  and o.modify_date<=:endTime");
		}
		sb.append(" )as havePassedCount");
		
		sb.append(" FROM refuel_gas_card_order o INNER JOIN refuel_gas_member r ON r.user_name =o.user_name and r.gas_num=o.gun_num ");
		sb.append(" WHERE 1=1  and(r.member_status in('0','5','7','9') or (r.member_status in ('8','6') AND o.order_status ='0')) and o.gun_num=:gun_num" );
		if (StringUtils.isNotBlank(startTime)) {
			sb.append(" and o.modify_date>=:startTime");
		}
		if (StringUtils.isNotBlank(endTime)) {
			sb.append("  and o.modify_date<=:endTime");
		}
		Query query =this.entityManager.createNativeQuery(sb.toString());
			try {
				if (StringUtils.isNotBlank(startTime)) {
					query.setParameter("startTime",sim.parse(startTime));
				}
				if (StringUtils.isNotBlank(endTime)) {
				query.setParameter("endTime",sim.parse(endTime) );
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			query.setParameter("gun_num", gasNum);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		Map<String,Object> map=(Map<String, Object>) query.getSingleResult();
		return map;
	}

	@Override
	public PageResult<RefuelGasCardOrder> findByCondition(Integer gasNum,String orderNo, Integer orderStatus, String memberUserName,
			String startTime, String endTime, Integer pageNo, Integer pageSize) {
		
		StringBuffer sb =new StringBuffer("from RefuelGasCardOrder o where 1=1");
		StringBuffer sql=new StringBuffer("SELECT COUNT(*) FROM refuel_gas_card_order o WHERE 1=1");
		
		if (gasNum!=null) {
			sb.append(" and o.gunNum=:gasNum");
			sql.append(" AND o.gun_num=:gasNum");
		}
		if (StringUtils.isNotBlank(orderNo)) {
			sb.append(" and o.orderNo=:orderNo");
			sql.append(" AND o.order_no=:orderNo");
		}
		if (orderStatus!=null) {
			sb.append(" and o.orderStatus=:orderStatus");
			sql.append(" AND o.order_status=:orderStatus");
		}
		if (memberUserName!=null) {
			sb.append(" and o.userName=:userName");
			sql.append(" AND o.user_name=:userName");
		}
		if (StringUtils.isNotBlank(startTime)) {
			sb.append(" and o.createDate>=:startTime");
			sql.append(" AND o.create_date>=:startTime");
		}
		if (StringUtils.isNotBlank(endTime)) {
			sb.append(" and o.createDate<=:endTime");
			sql.append(" AND o.create_date<=:endTime");
		}
		sb.append(" and LENGTH(o.userName)>11");
		sql.append(" AND LENGTH(o.user_name)>11");
		sb.append(" order by o.createDate desc");
		TypedQuery<RefuelGasCardOrder> query=entityManager.createQuery(sb.toString(),RefuelGasCardOrder.class);
		Query queryCount=entityManager.createNativeQuery(sql.toString());
		if (gasNum!=null) {
			query.setParameter("gasNum", gasNum);
			queryCount.setParameter("gasNum", gasNum);
		}
		if (StringUtils.isNotBlank(orderNo)) {
			query.setParameter("orderNo", orderNo);
			queryCount.setParameter("orderNo", orderNo);
		}
		if (orderStatus!=null) {
			query.setParameter("orderStatus", orderStatus);
			queryCount.setParameter("orderStatus", orderStatus);
		}
		if (memberUserName!=null) {
			query.setParameter("userName", memberUserName);
			queryCount.setParameter("userName", memberUserName);
		}
		if (StringUtils.isNotBlank(startTime)) {
			query.setParameter("startTime", DateUtils.stringFomatDate(startTime,"yyyy-MM-dd HH:mm:ss"));
			queryCount.setParameter("startTime",DateUtils.stringFomatDate(startTime,"yyyy-MM-dd HH:mm:ss"));
		}
		if (StringUtils.isNotBlank(endTime)) {
			query.setParameter("endTime", DateUtils.stringFomatDate(endTime,"yyyy-MM-dd HH:mm:ss"));
			queryCount.setParameter("endTime", DateUtils.stringFomatDate(endTime,"yyyy-MM-dd HH:mm:ss"));
		}
		if (pageNo!=null&&pageSize!=null) {
			query.setMaxResults(pageSize);
			query.setFirstResult((pageNo-1)*pageSize);
		}
		List<RefuelGasCardOrder> list=query.getResultList();
		int count=0;
		try {
		count=((BigInteger)queryCount.getSingleResult()).intValue();		
		} catch (Exception e) {
			e.printStackTrace();
		}
		PageResult<RefuelGasCardOrder> result = new PageResult<RefuelGasCardOrder>(list,count);
		return result;
	}

}











