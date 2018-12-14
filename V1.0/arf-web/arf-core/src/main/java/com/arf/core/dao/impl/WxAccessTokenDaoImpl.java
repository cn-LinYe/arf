package com.arf.core.dao.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.arf.core.dao.WxAccessTokenDao;
import com.arf.core.entity.WxAccessToken;

@Repository("wxAccessTokenDao")
public class WxAccessTokenDaoImpl extends BaseDaoImpl<WxAccessToken, Long> implements WxAccessTokenDao{

	@Override
	public WxAccessToken findByClassifier(String classifier) {
		this.clear();
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<WxAccessToken> criteriaQuery = criteriaBuilder.createQuery(WxAccessToken.class);
		Root<WxAccessToken> root = criteriaQuery.from(WxAccessToken.class);
		criteriaBuilder.desc(root.get("id"));
		criteriaQuery.select(root);
		criteriaQuery.where(criteriaBuilder.equal(root.get("classifier"), classifier));
		List<WxAccessToken> list = entityManager.createQuery(criteriaQuery).getResultList();
		if(CollectionUtils.isEmpty(list)){
			return null;
		}else{
			return list.get(0);
		}
	}

}
