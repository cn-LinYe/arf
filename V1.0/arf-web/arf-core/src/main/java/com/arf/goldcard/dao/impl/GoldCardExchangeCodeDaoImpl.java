package com.arf.goldcard.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.goldcard.dao.IGoldCardExchangeCodeDao;
import com.arf.goldcard.entity.GoldCardExchangeCode;

@Repository("goldCardExchangeCodeDaoImpl")
public class GoldCardExchangeCodeDaoImpl extends BaseDaoImpl<GoldCardExchangeCode, Long> implements IGoldCardExchangeCodeDao{
	@Override
	public GoldCardExchangeCode findByExchangeCode(String exchangeCode) {
		StringBuffer sql =new StringBuffer(" from GoldCardExchangeCode where exchangeCode=:exchangeCode ");
		TypedQuery<GoldCardExchangeCode> query = entityManager.createQuery(sql.toString(),GoldCardExchangeCode.class);
		query.setParameter("exchangeCode", exchangeCode);
		List<GoldCardExchangeCode> list ;
		try {
			 list = query.getResultList();
		} catch (Exception e) {
			return null;
		}
		
		return list.isEmpty()?null:list.get(0);
	}
}
