package com.arf.core.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.ViolationRecordCompareDao;
import com.arf.core.entity.ViolationRecordCompare;

/**
 * Dao - 用于比较的违章记录类
 * 
 * @author arf
 * @version 4.0
 */
@Repository("violationRecordCompareDaoImpl")
public class ViolationRecordCompareDaoImpl extends BaseDaoImpl<ViolationRecordCompare, Long> implements ViolationRecordCompareDao {
	/**
	 * 根据用户名查询违章记录比较信息
	 * @param username
	 * @return
	 */
    public List<ViolationRecordCompare> findListByUserName(String username){
	    String hql="from ViolationRecordCompare vrc where vrc.username=:username";
	    return entityManager.createQuery(hql, ViolationRecordCompare.class).setParameter("username", username).getResultList();
	}
}
