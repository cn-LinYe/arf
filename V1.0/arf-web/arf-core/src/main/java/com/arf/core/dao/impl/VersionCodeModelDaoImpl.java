package com.arf.core.dao.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.VersionCodeModelDao;
import com.arf.core.entity.VersionCodeModel;

/**
 * Dao - 版本号表
 * 
 * @author arf
 * @version 4.0
 */
@Repository("versionCodeModelDaoImpl")
public class VersionCodeModelDaoImpl extends BaseDaoImpl<VersionCodeModel, Long> implements VersionCodeModelDao {

	@Override
	public List<VersionCodeModel> findVersion() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<VersionCodeModel> criteriaQuery = criteriaBuilder.createQuery(VersionCodeModel.class);
		Root<VersionCodeModel> root = criteriaQuery.from(VersionCodeModel.class);
		criteriaQuery.select(root);		
		return super.findList(criteriaQuery, null, null, null, null);
	}
	
}
