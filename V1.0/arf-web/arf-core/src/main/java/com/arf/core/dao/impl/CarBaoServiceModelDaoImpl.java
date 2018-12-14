package com.arf.core.dao.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.CarBaoServiceModelDao;
import com.arf.core.entity.CarBaoServiceModel;

/**
 * Dao - 车保服务类型表
 * 
 * @author arf
 * @version 4.0
 */
@Repository("carBaoServiceModelDaoImpl")
public class CarBaoServiceModelDaoImpl extends BaseDaoImpl<CarBaoServiceModel, Long> implements CarBaoServiceModelDao {

	@Override
	public List<CarBaoServiceModel> select(int level) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<CarBaoServiceModel> criteriaQuery = criteriaBuilder.createQuery(CarBaoServiceModel.class);
		Root<CarBaoServiceModel> root = criteriaQuery.from(CarBaoServiceModel.class);
		criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("levle"), level));	
		return entityManager.createQuery(criteriaQuery).getResultList();
	}	
}
