package com.arf.core.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.arf.core.dao.VerificationModelDao;
import com.arf.core.entity.VerificationModel;

/**
 * Dao - 验证码存储表
 * 
 * @author arf
 * @version 4.0
 */
@Repository("verificationModelDaoImpl")
public class VerificationModelDaoImpl extends BaseDaoImpl<VerificationModel, Long> implements VerificationModelDao {
	
	public List<VerificationModel> checkByPhone(String phone){
		if (StringUtils.isEmpty(phone)) {
			return null;
		}
		String jpql = "select v from VerificationModel v where v.phone=:phone";
		return entityManager.createQuery(jpql, VerificationModel.class).setParameter("phone", phone).getResultList();
	}
	
	
}
