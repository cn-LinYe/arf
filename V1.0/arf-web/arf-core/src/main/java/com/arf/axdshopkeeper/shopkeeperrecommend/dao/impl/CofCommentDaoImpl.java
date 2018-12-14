package com.arf.axdshopkeeper.shopkeeperrecommend.dao.impl;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.arf.axdshopkeeper.shopkeeperrecommend.dao.ICofCommentDao;
import com.arf.axdshopkeeper.shopkeeperrecommend.entity.CofComment;
import com.arf.base.PageResult;
import com.arf.core.dao.impl.BaseDaoImpl;

import cn.emay.slf4j.Logger;
import cn.emay.slf4j.LoggerFactory;

@Repository("cofCommentDaoImpl")
public class CofCommentDaoImpl extends BaseDaoImpl<CofComment, Long> implements ICofCommentDao {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public PageResult<CofComment> findByRecommendIdListPage(Integer pageNo,Integer pageSize, Long recommendId){
		if(recommendId==null){
			return null;
		}
		StringBuffer countSb = new StringBuffer("select count(1) as COUNT from cof_comment t where t.status=0");
		countSb.append(" and t.recommend_id = :recommendId");
		Query countQuery = entityManager.createNativeQuery(countSb.toString());
		countQuery.setParameter("recommendId", recommendId);
		Integer count = 0;
		try{
			count = ((BigInteger)countQuery.getSingleResult()).intValue();
		}catch(Exception e){
			logger.error("CofCommentDaoImpl.findByRecommendIdListPage查询错误");
		}
		
		StringBuffer sb = new StringBuffer("from CofComment t where t.status=0");
		sb.append(" and t.recommendId =:recommendId");
		sb.append(" order by t.createDate desc");
		TypedQuery<CofComment> query = entityManager.createQuery(sb.toString(), CofComment.class);
		query.setParameter("recommendId", recommendId);
		if(pageNo!=null && pageSize!=null){
			query.setFirstResult((pageNo-1)*pageSize);
			query.setMaxResults(pageSize);
		}
		List<CofComment> list = query.getResultList();
		PageResult<CofComment> result = new PageResult<CofComment>();
		result.setList(list);
		result.setTotalNum(count);
		return result;
	}
	
	@Override
	public PageResult<Map<String,Object>> findMoreByRecommendIdListPage(Integer pageNo,Integer pageSize, Long recommendId){
		if(recommendId==null){
			return null;
		}
		StringBuffer countSb = new StringBuffer("select count(1) as COUNT from cof_comment t ");
		//countSb.append(" left join xx_member mb on t.user_name = mb.username");
		countSb.append(" where t.status=0");
		countSb.append(" and t.recommend_id = :recommendId");
		Query countQuery = entityManager.createNativeQuery(countSb.toString());
		countQuery.setParameter("recommendId", recommendId);
		Integer count = 0;
		try{
			count = ((BigInteger)countQuery.getSingleResult()).intValue();
		}catch(Exception e){
			logger.error("CofCommentDaoImpl.findByRecommendIdListPage查询错误");
		}
		
		StringBuffer sb = new StringBuffer("select t.id id,t.create_date createDate,t.modify_date modifyDate,t.version version");
		sb.append(" ,t.user_name userName,t.recommend_id recommendId,t.parent_id parentId,t.content content,t.status status ");
		sb.append(" from cof_comment t ");
		//sb.append(" left join xx_member mb on t.user_name = mb.username");
		sb.append(" where t.status=0");
		sb.append(" and t.recommend_id =:recommendId");
		sb.append(" order by t.create_date desc");
		Query query = entityManager.createNativeQuery(sb.toString());
		query.setParameter("recommendId", recommendId);
		if(pageNo!=null && pageSize!=null){
			query.setFirstResult((pageNo-1)*pageSize);
			query.setMaxResults(pageSize);
		}
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String,Object>> list = query.getResultList();
		
		PageResult<Map<String,Object>> result = new PageResult<Map<String,Object>>();
		result.setList(list);
		result.setTotalNum(count);
		return result;
	}
	
	@Override
	public Integer countByRecommendId(Long recommendId){
		if(recommendId==null){
			return null;
		}
		StringBuffer sb = new StringBuffer("select count(1) as COUNT from cof_comment t where t.status=0");
		sb.append(" and t.recommend_id = :recommendId");
		Query query = entityManager.createNativeQuery(sb.toString());
		query.setParameter("recommendId", recommendId);
		Integer count = 0;
		try{
			count = ((BigInteger)query.getSingleResult()).intValue();
		}catch(Exception e){
			logger.error("CofCommentDaoImpl.countByRecommendId查询错误："+e);
			return null;
		}
		return count;
	}
}
