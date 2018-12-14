package com.arf.core.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.arf.core.dao.UserPropretyInforRecordDao;
import com.arf.core.entity.UserPropretyInforRecord;

/**
 * Dao - 用户物业信息记录
 * 
 * @author arf  liaotao
 * @version 4.0
 */
@Repository("userPropretyInforRecordDaoImpl")
public class UserPropretyInforRecordDaoImpl extends BaseDaoImpl<UserPropretyInforRecord, Long> implements UserPropretyInforRecordDao {

	
	@Override
	public UserPropretyInforRecord selectByHouseId(String user_name,String houseId) {
		if (StringUtils.isEmpty(houseId)||StringUtils.isEmpty(user_name)) {
			return null;
		}
		try {
			String jpql = "select userPropretyInforRecord from UserPropretyInforRecord userPropretyInforRecord where userPropretyInforRecord.houseId = :houseId and userPropretyInforRecord.userName = :userName";
			return entityManager.createQuery(jpql, UserPropretyInforRecord.class).setParameter("houseId", houseId).setParameter("userName", user_name).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public List<UserPropretyInforRecord> selectByUsername(String user_name) {
		if (StringUtils.isEmpty(user_name)) {
			return null;
		}
		String jpql = "select userPropretyInforRecord from UserPropretyInforRecord userPropretyInforRecord where userPropretyInforRecord.userName = :userName";
		return entityManager.createQuery(jpql, UserPropretyInforRecord.class).setParameter("userName", user_name).getResultList();
	}
}
