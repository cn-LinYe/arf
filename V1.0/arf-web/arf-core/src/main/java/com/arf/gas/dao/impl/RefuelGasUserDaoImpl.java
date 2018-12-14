package com.arf.gas.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.gas.dao.RefuelGasUserDao;
import com.arf.gas.entity.RefuelGasUser;
import com.arf.gas.entity.RefuelGasUser.Status;

@Repository("refuelGasUserDao")
public class RefuelGasUserDaoImpl extends BaseDaoImpl<RefuelGasUser, Long> implements RefuelGasUserDao{
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public RefuelGasUser findByLoginAccount(String loginAccount) {
		StringBuffer queryHql = new StringBuffer(" from RefuelGasUser where loginAccount=:loginAccount");
		TypedQuery<RefuelGasUser> typedQuery = this.entityManager.createQuery(queryHql.toString(), RefuelGasUser.class);
		typedQuery.setParameter("loginAccount", loginAccount);
		
		List<RefuelGasUser> list =typedQuery.getResultList();
		try {
			return list.isEmpty()?null:list.get(0);
		} catch (Exception e) {
			logger.error("查找加油员登录账号出错", e);
			return null;
		}
	}

	@Override
	public List<RefuelGasUser> findByGasNumBusiness(Integer gasNum, Integer businessNum, Status status) {
		StringBuffer queryHql = new StringBuffer(" from RefuelGasUser where gasNum=:gasNum");
		if (businessNum!=null) {
			queryHql.append(" and businessNum =:businessNum");
		}
		if (status!=null) {
			queryHql.append(" and status ="+status.ordinal());
		}
		TypedQuery<RefuelGasUser> typedQuery = this.entityManager.createQuery(queryHql.toString(), RefuelGasUser.class);
		typedQuery.setParameter("gasNum", gasNum);
		if (businessNum!=null) {
			typedQuery.setParameter("businessNum", businessNum);
		}
		try {
			List<RefuelGasUser> list =typedQuery.getResultList();
			return list;
		} catch (Exception e) {
			return null;
		}
	}

}
