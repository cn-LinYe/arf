package com.arf.core.dao.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.CameraCodeModelDao;
import com.arf.core.entity.CameraCodeModel;

/**
 * Dao - 摄像机管理
 * 
 * @author arf
 * @version 4.0
 */
@Repository("cameraCodeModelDaoImpl")
public class CameraCodeModelDaoImpl extends BaseDaoImpl<CameraCodeModel, Long> implements CameraCodeModelDao {

	@Override
	public List<CameraCodeModel> selectbycode(String cameraCode) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<CameraCodeModel> criteriaQuery = criteriaBuilder.createQuery(CameraCodeModel.class);
		Root<CameraCodeModel> root = criteriaQuery.from(CameraCodeModel.class);
		criteriaQuery.select(root);
		criteriaQuery.where(criteriaBuilder.equal(root.get("cameraCode"), cameraCode));
		return super.findList(criteriaQuery, null, null, null, null);
	}
	
}
