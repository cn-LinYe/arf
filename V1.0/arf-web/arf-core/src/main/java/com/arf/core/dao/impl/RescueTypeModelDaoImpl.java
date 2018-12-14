package com.arf.core.dao.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import com.arf.core.dao.RescueTypeModelDao;
import com.arf.core.entity.RescueTypeModel;

/**
 * Dao - 救援类型表
 * 
 * @author arf
 * @version 4.0
 */
@Repository("rescueTypeModelDaoImpl")
public class RescueTypeModelDaoImpl extends BaseDaoImpl<RescueTypeModel, Long> implements RescueTypeModelDao {
	
	public List<RescueTypeModel> sellectAll(){
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<RescueTypeModel> criteriaQuery = criteriaBuilder.createQuery(RescueTypeModel.class);
		Root<RescueTypeModel> root = criteriaQuery.from(RescueTypeModel.class);
		criteriaQuery.select(root);
		return super.findList(criteriaQuery, null, null, null, null);
	}
}
