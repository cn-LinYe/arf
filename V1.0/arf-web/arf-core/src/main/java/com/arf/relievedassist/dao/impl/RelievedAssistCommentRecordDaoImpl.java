package com.arf.relievedassist.dao.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.relievedassist.dao.IRelievedAssistCommentRecordDao;
import com.arf.relievedassist.entity.RelievedAssistCommentRecord;
import com.arf.relievedassist.entity.RelievedAssistCommentRecord.Status;

@Repository("relievedAssistCommentRecordDao")
public class RelievedAssistCommentRecordDaoImpl extends BaseDaoImpl<RelievedAssistCommentRecord, Long> implements IRelievedAssistCommentRecordDao{

	@Override
	public List<Map<String, Object>> findByContentIds(List<Long> contentIdList) {
		if(CollectionUtils.isEmpty(contentIdList)){
			return null;
		}
		StringBuffer queryHql = new StringBuffer("select");
		queryHql.append(" a.id,a.user_name userName,a.status,a.content_id contentId,a.comment_Id commentId,");
		queryHql.append(" a.comment comment,a.praise_number praiseNumber,a.community_number communityNumber,");
		queryHql.append(" a.comment_type commentType,a.create_date createDate,");
		/*queryHql.append(" d.photo avatar,d.nickname nickName,");*/
		queryHql.append(" c.community_name communityName");
		queryHql.append(" from relieved_assist_comment_record a");
		/*queryHql.append(" left join xx_member d on d.username = a.user_name");*/
		queryHql.append(" left join community c on c.community_number = a.community_number");
		queryHql.append(" where 1=1 and a.status=0");
		queryHql.append(" and a.content_id in (:contentIdList)");
		
		Query typedQuery = this.entityManager.createNativeQuery(queryHql.toString());
		typedQuery.setParameter("contentIdList", contentIdList);
		
		typedQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> rows = typedQuery.getResultList();
		
		return rows;
	}

	@Override
	public void updateStatusById(List<Long> idList, Status status) {
		StringBuffer sql =new StringBuffer("UPDATE RelievedAssistCommentRecord set status=:status WHERE id in(:idList)");
		entityManager.createQuery(sql.toString()).setParameter("status", status).setParameter("idList", idList).executeUpdate();
	}
	
	@Override
	public List<RelievedAssistCommentRecord> findByContentId(Long contentId) {
		StringBuffer sql =new StringBuffer(" from RelievedAssistCommentRecord");
		sql.append(" where 1=1");
		sql.append(" and contentId =:contentId");
		
		TypedQuery<RelievedAssistCommentRecord> query 
			=this.entityManager.createQuery(sql.toString(),RelievedAssistCommentRecord.class);
		
		query.setParameter("contentId", contentId);
		List<RelievedAssistCommentRecord> list =query.getResultList();
		return list;
	}

	@Override
	public List<RelievedAssistCommentRecord> findByCommentId(Long commentId) {
		StringBuffer sql =new StringBuffer(" from RelievedAssistCommentRecord");
		sql.append(" where 1=1");
		sql.append(" and commentId =:commentId");
		
		TypedQuery<RelievedAssistCommentRecord> query 
			=this.entityManager.createQuery(sql.toString(),RelievedAssistCommentRecord.class);
		
		query.setParameter("commentId", commentId);
		List<RelievedAssistCommentRecord> list =query.getResultList();
		return list;
	}

}
