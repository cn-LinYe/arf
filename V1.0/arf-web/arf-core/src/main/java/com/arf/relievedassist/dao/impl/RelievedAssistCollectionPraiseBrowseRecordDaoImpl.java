package com.arf.relievedassist.dao.impl;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.arf.base.PageResult;
import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.relievedassist.dao.IRelievedAssistCollectionPraiseBrowseRecordDao;
import com.arf.relievedassist.entity.RelievedAssistCollectionPraiseBrowseRecord;
import com.arf.relievedassist.entity.RelievedAssistCollectionPraiseBrowseRecord.CollectionStatus;
import com.arf.relievedassist.entity.RelievedAssistCollectionPraiseBrowseRecord.Type;

@Repository("relievedAssistCollectionPraiseBrowseRecordDao")
public class RelievedAssistCollectionPraiseBrowseRecordDaoImpl extends BaseDaoImpl<RelievedAssistCollectionPraiseBrowseRecord, Long> implements IRelievedAssistCollectionPraiseBrowseRecordDao{

	@Override
	public List<RelievedAssistCollectionPraiseBrowseRecord> findByUserAndContentId(List<Long> contentIdList,
			List<String> userList,Type type) {
		StringBuffer sql =new StringBuffer(" from RelievedAssistCollectionPraiseBrowseRecord");
		sql.append(" where 1=1 ");
		if(CollectionUtils.isNotEmpty(contentIdList)){
			sql.append(" and contentId in (:contentIdList)");
		}
		if(CollectionUtils.isNotEmpty(userList)){
			sql.append(" and userName in (:userList)");
		}
		if(type!=null){
			sql.append(" and type =:type");
		}
		TypedQuery<RelievedAssistCollectionPraiseBrowseRecord> query 
			=this.entityManager.createQuery(sql.toString(),RelievedAssistCollectionPraiseBrowseRecord.class);
		
		if(CollectionUtils.isNotEmpty(contentIdList)){
			query.setParameter("contentIdList", contentIdList);
		}
		if(CollectionUtils.isNotEmpty(userList)){
			query.setParameter("userList", userList);
		}
		if(type!=null){
			query.setParameter("type", type);
		}
		List<RelievedAssistCollectionPraiseBrowseRecord> list =query.getResultList();
		return list;
	}

	@Override
	public PageResult<Map<String, Object>> getSortContentId(List<Long> contentIdList, Type type,Integer pageNo,Integer pageSize) {
		StringBuffer countHql = new StringBuffer("select count(a.content_id) as COUNT from");
		
		StringBuffer queryHql = new StringBuffer("select");
		queryHql.append(" a.content_id from");
		queryHql.append("(select count(content_id) as contentCount , content_id from relieved_assist_collection_praise_browse_record");
		queryHql.append(" where 1=1 and collection_status=0");
		
		countHql.append("(select count(content_id) as contentCount , content_id from relieved_assist_collection_praise_browse_record");
		countHql.append(" where 1=1 and collection_status=0");
		
		if(CollectionUtils.isNotEmpty(contentIdList)){
			countHql.append(" and content_id in (:contentIdList) ");
			queryHql.append(" and content_id in (:contentIdList) ");
		}
		if(type!=null){
			countHql.append(" and type =:type ");
			queryHql.append(" and type =:type ");
		}
		queryHql.append(" group by content_id,type ");
		queryHql.append(" ) a ");
		queryHql.append(" order by a.contentCount ");
		
		countHql.append(" group by content_id,type ");
		countHql.append(" ) a ");
		countHql.append(" order by a.contentCount ");
		
		Query queryCount = this.entityManager.createNativeQuery(countHql.toString());
		Query typedQuery = this.entityManager.createNativeQuery(queryHql.toString());
		
		if(CollectionUtils.isNotEmpty(contentIdList)){
			queryCount.setParameter("contentIdList", contentIdList);
			typedQuery.setParameter("contentIdList", contentIdList);
		}
		if(type!=null){
			queryCount.setParameter("type", type.ordinal());
			typedQuery.setParameter("type", type.ordinal());
		}
		
		int count = 0;
		try{
			count = ((BigInteger)queryCount.getSingleResult()).intValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		PageResult<Map<String, Object>> pageResult = new PageResult<Map<String, Object>>();
		pageResult.setTotalNum(count);
		//分页查询
		typedQuery.setFirstResult((pageNo-1)*pageSize);
		typedQuery.setMaxResults(pageSize);
		typedQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> rows = typedQuery.getResultList();
		pageResult.setList(rows);
		return pageResult;
	}

	@Override
	public RelievedAssistCollectionPraiseBrowseRecord findByUserAndContentIdAndType(Long contentId,
			String userName, Type type, Long commentId) {
		StringBuffer sql =new StringBuffer(" from RelievedAssistCollectionPraiseBrowseRecord");
		sql.append(" where 1=1");
		sql.append(" and contentId =:contentId");
		if(commentId!=null){
			sql.append(" and commentId =:commentId");
		}else{
			sql.append(" and commentId is null");
		}
		sql.append(" and userName=:userName");
		sql.append(" and type =:type");
		
		TypedQuery<RelievedAssistCollectionPraiseBrowseRecord> query 
			=this.entityManager.createQuery(sql.toString(),RelievedAssistCollectionPraiseBrowseRecord.class);
		
		query.setParameter("contentId", contentId);
		if(commentId!=null){
			query.setParameter("commentId", commentId);
		}
		query.setParameter("userName", userName);
		query.setParameter("type", type);
		List<RelievedAssistCollectionPraiseBrowseRecord> list =query.getResultList();
		return CollectionUtils.isEmpty(list)?null:list.get(0);
	}

	@Override
	public List<RelievedAssistCollectionPraiseBrowseRecord> findByContentId(Long contentId) {
		StringBuffer sql =new StringBuffer(" from RelievedAssistCollectionPraiseBrowseRecord");
		sql.append(" where 1=1");
		sql.append(" and contentId =:contentId");
		
		TypedQuery<RelievedAssistCollectionPraiseBrowseRecord> query 
			=this.entityManager.createQuery(sql.toString(),RelievedAssistCollectionPraiseBrowseRecord.class);
		
		query.setParameter("contentId", contentId);
		List<RelievedAssistCollectionPraiseBrowseRecord> list =query.getResultList();
		return list;
	}

	@Override
	public void updateStatusById(List<Long> idList, CollectionStatus status) {
		StringBuffer sql =new StringBuffer("UPDATE RelievedAssistCollectionPraiseBrowseRecord set collectionStatus=:status WHERE id in(:idList)");
		entityManager.createQuery(sql.toString()).setParameter("status", status).setParameter("idList", idList).executeUpdate();
	}

	@Override
	public List<RelievedAssistCollectionPraiseBrowseRecord> findByCommentId(Long commentId) {
		StringBuffer sql =new StringBuffer(" from RelievedAssistCollectionPraiseBrowseRecord");
		sql.append(" where 1=1");
		sql.append(" and commentId =:commentId");
		
		TypedQuery<RelievedAssistCollectionPraiseBrowseRecord> query 
			=this.entityManager.createQuery(sql.toString(),RelievedAssistCollectionPraiseBrowseRecord.class);
		
		query.setParameter("commentId", commentId);
		List<RelievedAssistCollectionPraiseBrowseRecord> list =query.getResultList();
		return list;
	}

}
