package com.arf.axdshopkeeper.shopkeeperrecommend.dao.impl;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.arf.axdshopkeeper.shopkeeperrecommend.dao.ICofUserFavoritesDao;
import com.arf.axdshopkeeper.shopkeeperrecommend.entity.CofUserFavorites;
import com.arf.core.dao.impl.BaseDaoImpl;

import cn.emay.slf4j.Logger;
import cn.emay.slf4j.LoggerFactory;

@Repository("cofUserFavoritesDaoImpl")
public class CofUserFavoritesDaoImpl extends BaseDaoImpl<CofUserFavorites, Long> implements ICofUserFavoritesDao {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public CofUserFavorites findByType(String userName,Long recommendId,CofUserFavorites.Type type){
		if(StringUtils.isBlank(userName) || recommendId==null || type==null){
			return null;
		}
		StringBuffer sb = new StringBuffer("from CofUserFavorites t where t.userName=:userName");
		sb.append(" and t.recommendId=:recommendId and t.type=:type");
		TypedQuery<CofUserFavorites> query = entityManager.createQuery(sb.toString(), CofUserFavorites.class);
		query.setParameter("userName", userName);
		query.setParameter("recommendId", recommendId);
		query.setParameter("type", type);
		List<CofUserFavorites> list = query.getResultList();
		if(CollectionUtils.isNotEmpty(list)){
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public Integer countByType(Long recommendId,CofUserFavorites.Type type){
		if(type==null || recommendId==null){
			return null;
		}
		StringBuffer sb = new StringBuffer("select count(1) as COUNT from cof_user_favorites t where t.status != 1");
		sb.append(" and t.type =:type and t.recommend_id = :recommendId");
		Query query = entityManager.createNativeQuery(sb.toString());
		query.setParameter("type", type.ordinal());
		query.setParameter("recommendId", recommendId);
		Integer count = 0;
		try{
			count = ((BigInteger)query.getSingleResult()).intValue();
		}catch(Exception e){
			logger.error("CofUserFavoritesDaoImpl.countByType 查询错误："+e);
			return null;
		}
		return count;
	}
}
