package com.arf.goldcard.dao.impl;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.arf.base.PageResult;
import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.goldcard.dao.IGoldCardExchangeCodeRecordDao;
import com.arf.goldcard.entity.GoldCardExchangeCodeRecord;

@Repository("goldCardExchangeCodeRecordDaoImpl")
public class GoldCardExchangeCodeRecordDaoImpl extends BaseDaoImpl<GoldCardExchangeCodeRecord, Long> implements IGoldCardExchangeCodeRecordDao{

	@Override
	public PageResult<Map<String, Object>> getIGoldCardExchangeCodeServiceOrders(String userName, Integer pageSize,Integer pageNO) {
		StringBuffer sql = new StringBuffer("SELECT ");
		sql.append(" r.create_date createDate,t.period_type periodType,t.useful_period usefulPeriod,t.theme_color themeColor,");
		sql.append(" t.card_name cardName,r.exchange_code exchangeCode,t.type_num typeNum,");
		sql.append(" t.face_value faceValue,e.batch_num batchNum,");
		sql.append(" r.business_name businessName,r.business_mobile businessMobile,r.grant_date grantDate,");
		sql.append(" r.user_name userName,r.card_no cardNo,r.exchange_date exchangeDate,e.price");
		sql.append(" FROM p_gold_card_exchange_code_record r");
		sql.append(" LEFT JOIN p_gold_card_exchange_code e ON e.exchange_code=r.exchange_code");
		sql.append(" LEFT JOIN p_gold_card_type t ON t.type_num=e.type_num");
		sql.append(" where 1=1");
		
		StringBuffer sqlCount = new StringBuffer("SELECT count(1)");
		sqlCount.append(" FROM p_gold_card_exchange_code_record r");
		sqlCount.append(" LEFT JOIN p_gold_card_exchange_code e ON e.exchange_code=r.exchange_code");
		sqlCount.append(" LEFT JOIN p_gold_card_type t ON t.type_num=e.type_num WHERE 1=1 ");
		
		if (userName!=null) {
			sql.append(" and r.user_name=:username");	
			sqlCount.append(" and r.user_name=:username");
		}
		sql.append(" order by exchangeDate desc");
		Query query =entityManager.createNativeQuery(sql.toString());
		Query queryConut =entityManager.createNativeQuery(sqlCount.toString());
		if (userName!=null) {
			query.setParameter("username", userName);
			queryConut.setParameter("username", userName);
		}
		query.setMaxResults(pageSize);
		query.setFirstResult((pageNO-1)*pageSize);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> map= query.getResultList();
		int count =0;
		try {
			count =((BigInteger)queryConut.getSingleResult()).intValue();			
		} catch (Exception e) {
		}
		PageResult<Map<String,Object>> result = new PageResult<Map<String, Object>>(map,count);
		return result;
	}
}
