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
import com.arf.goldcard.dao.IUserGoldCardRecordDao;
import com.arf.goldcard.entity.UserGoldCardRecord;

@Repository("userGoldCardRecordDao")
public class UserGoldCardRecordDaoImpl extends BaseDaoImpl<UserGoldCardRecord, Long> implements IUserGoldCardRecordDao {

	@SuppressWarnings("unchecked")
	@Override
	public PageResult<Map<String,Object>> getGoldCardOrders(String userName,Integer pageSize,Integer pageNO) {
		StringBuffer sql = new StringBuffer(" SELECT ");
		sql.append(" r.create_date createDate,r.price price,r.face_value faceValue,r.buy_date buyDate,r.end_date endDate, ");
		sql.append(" r.type_num typeNum,r.status status ,r.pay_type payType,r.pay_status payStatus,");
		sql.append(" r.user_name userName,t.period_type periodType, t.useful_period usefulPeriod,t.useful_period unShelved, ");
		sql.append(" t.theme_color themeColor,t.card_name cardName");
		sql.append(" FROM p_user_gold_card_record r LEFT JOIN p_gold_card_type t ON t.type_num=r.type_num");
		sql.append(" WHERE 1=1 ");

		StringBuffer sqlCount =new StringBuffer(" SELECT Count(*) ");
		sqlCount.append(" FROM p_user_gold_card_record r LEFT JOIN p_gold_card_type t ON t.type_num=r.type_num ");
		sqlCount.append(" WHERE 1=1 AND r.user_name =:userName");
		if (userName!=null) {
			sql.append(" AND r.user_name =:userName");
			sqlCount.append(" AND r.user_name =:userName");
		}
		sql.append(" order by buyDate desc");
		Query query = entityManager.createNativeQuery(sql.toString());
		Query queryCount=entityManager.createNativeQuery(sqlCount.toString());
		if (userName!=null) {
			query.setParameter("userName", userName);
			queryCount.setParameter("userName", userName);
		}
		query.setMaxResults(pageSize);
		query.setFirstResult((pageNO-1)*pageSize);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String,Object>> map=query.getResultList();
		int count=0;
		try {
			count=((BigInteger)queryCount.getSingleResult()).intValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		PageResult<Map<String,Object>> pageResult=new PageResult<Map<String,Object>>(map,count);
		
		return pageResult;
	}

}
