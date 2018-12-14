package com.arf.relievedassist.dao.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.relievedassist.dao.IRelievedAssistUserRecordDao;
import com.arf.relievedassist.entity.RelievedAssistUserRecord;

@Repository("relievedAssistUserRecordDao")
public class RelievedAssistUserRecordDaoImpl extends BaseDaoImpl<RelievedAssistUserRecord, Long> implements IRelievedAssistUserRecordDao{

	@Override
	public List<Map<String, Object>> findByUserList(List<String> userList) {
		if(CollectionUtils.isEmpty(userList)){
			return null;
		}
		StringBuffer queryHql = new StringBuffer("select");
		queryHql.append(" a.id,a.user_name userName,a.status,a.community_number communityNumber,a.building,a.unit,");
		queryHql.append(" a.building_name comment,a.unit_name unitName,a.grade_id gradeId,a.content_number contentNumber,");
		queryHql.append(" a.collection_number collectionNumber,a.praise_number praiseNumber,a.comment_number commentNumber,");
		queryHql.append(" a.assist_point assistPoint,a.commented_number commentedNumber,a.collected_number collectedNumber,");
		queryHql.append(" a.praised_number praisedNumber,a.is_main isMain,d.grade grade,d.grade_name gradeName,d.grade_url gradeUrl");
		queryHql.append(" from relieved_assist_user_record a");
		queryHql.append(" left join relieved_assist_user_grade_record d on d.id = a.grade_id");
		queryHql.append(" where 1=1 and a.status=0 and is_main=1");
		queryHql.append(" and a.user_name in (:userList)");
		
		Query typedQuery = this.entityManager.createNativeQuery(queryHql.toString());
		typedQuery.setParameter("userList", userList);
		
		typedQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> rows = typedQuery.getResultList();
		
		return rows;
	}

	@Override
	public List<Map<String, Object>> findByCommunityNumber(String communityNumber) {
		StringBuffer queryHql = new StringBuffer("select");
		queryHql.append(" a.id,a.user_name userName,a.status,a.community_number communityNumber,a.building,a.unit,");
		queryHql.append(" a.building_name comment,a.unit_name unitName,a.grade_id gradeId,a.content_number contentNumber,");
		queryHql.append(" a.collection_number collectionNumber,a.praise_number praiseNumber,a.comment_number commentNumber,");
		queryHql.append(" a.assist_point assistPoint,a.commented_number commentedNumber,a.collected_number collectedNumber,");
		queryHql.append(" a.praised_number praisedNumber,a.is_main isMain,d.grade grade,d.grade_name gradeName,d.grade_url gradeUrl");
		queryHql.append(" from relieved_assist_user_record a");
		queryHql.append(" left join relieved_assist_user_grade_record d on d.id = a.grade_id");
		queryHql.append(" where 1=1 and a.status=0");
		queryHql.append(" and a.community_number =:communityNumber");
		
		Query typedQuery = this.entityManager.createNativeQuery(queryHql.toString());
		typedQuery.setParameter("communityNumber", communityNumber);
		
		typedQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> rows = typedQuery.getResultList();
		
		return rows;
	}

	@Override
	public List<RelievedAssistUserRecord> findByUserAndCommunity(String userName, String communityNumber,Boolean isMain) {
		StringBuffer sql =new StringBuffer(" from RelievedAssistUserRecord");
		sql.append(" where 1=1");
		if(StringUtils.isNotBlank(userName)){
			sql.append(" and userName =:userName");
		}
		if(StringUtils.isNotBlank(communityNumber)){
			sql.append(" and communityNumber=:communityNumber");
		}
		if(isMain){
			sql.append(" and isMain=1");
		}else{
			sql.append(" and (isMain is null or isMain=0)");
		}
		TypedQuery<RelievedAssistUserRecord> query 
			=this.entityManager.createQuery(sql.toString(),RelievedAssistUserRecord.class);
		
		if(StringUtils.isNotBlank(userName)){
			query.setParameter("userName", userName);
		}
		if(StringUtils.isNotBlank(communityNumber)){
			query.setParameter("communityNumber", communityNumber);
		}
		List<RelievedAssistUserRecord> list =query.getResultList();
		return list;
	}

	@Override
	public RelievedAssistUserRecord findUserByCommUser(String communityNumber, String userName) {
		StringBuffer hql = new StringBuffer("from RelievedAssistUserRecord where 1 = 1 and isMain=1");
		if(StringUtils.isNotBlank(userName)){
			hql.append(" and userName=:userName");
		}
		if(StringUtils.isNotBlank(communityNumber)){
			hql.append(" and communityNumber=:communityNumber");
		}
		
		TypedQuery<RelievedAssistUserRecord> query = entityManager.createQuery(hql.toString(),RelievedAssistUserRecord.class);
		
		if(StringUtils.isNotBlank(userName)){
			query.setParameter("userName",userName);
		}
		if(StringUtils.isNotBlank(communityNumber)){
			query.setParameter("communityNumber",communityNumber);
		}
		List<RelievedAssistUserRecord> records = query.getResultList();
		if(CollectionUtils.isNotEmpty(records)){
			return records.get(0);
		}
		return null;
	}

	@Override
	public List<RelievedAssistUserRecord> findByUsers(List<String> userList) {
		if(CollectionUtils.isEmpty(userList)){
			return null;
		}
		StringBuffer sql =new StringBuffer(" from RelievedAssistUserRecord");
		sql.append(" where 1=1 and isMain=1");
		sql.append(" and userName in(:userList)");
		
		TypedQuery<RelievedAssistUserRecord> query 
			=this.entityManager.createQuery(sql.toString(),RelievedAssistUserRecord.class);
		query.setParameter("userList", userList);
		
		List<RelievedAssistUserRecord> list =query.getResultList();
		return list;
	}

	@Override
	public List<RelievedAssistUserRecord> findByUserAndCommunity(String userName, String communityNumber) {
		StringBuffer sql =new StringBuffer(" from RelievedAssistUserRecord");
		sql.append(" where 1=1");
		if(StringUtils.isNotBlank(userName)){
			sql.append(" and userName =:userName");
		}
		if(StringUtils.isNotBlank(communityNumber)){
			sql.append(" and communityNumber=:communityNumber");
		}
		TypedQuery<RelievedAssistUserRecord> query 
			=this.entityManager.createQuery(sql.toString(),RelievedAssistUserRecord.class);
		
		if(StringUtils.isNotBlank(userName)){
			query.setParameter("userName", userName);
		}
		if(StringUtils.isNotBlank(communityNumber)){
			query.setParameter("communityNumber", communityNumber);
		}
		List<RelievedAssistUserRecord> list =query.getResultList();
		return list;
	}

}
