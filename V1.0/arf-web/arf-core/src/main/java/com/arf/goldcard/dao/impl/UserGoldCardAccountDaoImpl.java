package com.arf.goldcard.dao.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.arf.axd.axdgift.entity.AxdGiftConfig;
import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.goldcard.dao.IUserGoldCardAccountDao;
import com.arf.goldcard.dto.UserGoldCardAccountDto;
import com.arf.goldcard.entity.UserGoldCardAccount;

@Repository("userGoldCardAccountDaoImpl")
public class UserGoldCardAccountDaoImpl extends BaseDaoImpl<UserGoldCardAccount, Long> implements IUserGoldCardAccountDao {

	@SuppressWarnings("unchecked")
	@Override
	public UserGoldCardAccountDto findByUserName(String userName) {
		String sql = "select a.id, a.create_date as createDate, a.modify_date as modifyDate, a.balance, a.user_name as userName, "
				+ " a.end_date as endDate, a.card_no as cardNo, b.price, b.face_value as faceValue from "
				+ " p_user_gold_card_account a "
				+ " left join (select * from p_user_gold_card_record record where record.user_name = :userName order by record.buy_date desc limit 1) b on a.user_name = b.user_name "
				+ " where a.user_name = :userName";
		Query query = super.entityManager.createNativeQuery(sql);
		query.setParameter("userName", userName);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String,Object>> rows = query.getResultList();
		Map<String,Object> row = CollectionUtils.isEmpty(rows)?null:rows.get(0);
		return JSON.parseObject(JSON.toJSONString(row), UserGoldCardAccountDto.class);
	}

	@Override
	public UserGoldCardAccount findByUserNameEntity(String userName) {
		String hql = "from UserGoldCardAccount where userName=:userName";
		TypedQuery<UserGoldCardAccount> query = entityManager.createQuery(hql, UserGoldCardAccount.class);
		query.setParameter("userName", userName);
		List<UserGoldCardAccount> list =query.getResultList();
		try {
			return list.isEmpty()?null:list.get(0);
		} catch (Exception e) {
			return null;
		}
	}
}







