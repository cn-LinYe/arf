package com.arf.core.dao.impl;

import java.util.Collections;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.arf.core.dao.RBookrecordDao;
import com.arf.core.entity.RBookrecord;

/**
 * Dao - RBookrecord表
 * 
 * @author arf  dg
 * @version 4.0
 */
@Repository("RBookrecordDaoImpl")
public class RBookrecordDaoImpl extends BaseDaoImpl<RBookrecord, Long> implements RBookrecordDao {

	@Override
	public List<RBookrecord> findByOrderSn(String orderSn) {
		if (StringUtils.isEmpty(orderSn)) {
            return Collections.emptyList();
        }
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<RBookrecord> criteriaQuery = criteriaBuilder.createQuery(RBookrecord.class);
		Root<RBookrecord> root = criteriaQuery.from(RBookrecord.class);
		criteriaQuery.select(root);		
		criteriaQuery.where(criteriaBuilder.equal(root.get("orderNo"), orderSn));
		return super.findList(criteriaQuery, null, null, null, null);
	}
	
}
