package com.arf.relievedassist.dao.impl;

import java.math.BigInteger;
import java.util.Date;
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
import com.arf.relievedassist.dao.IRelievedAssistContentRecordDao;
import com.arf.relievedassist.entity.RelievedAssistContentRecord;

@Repository("relievedAssistContentRecordDao")
public class RelievedAssistContentRecordDaoImpl extends BaseDaoImpl<RelievedAssistContentRecord, Long> implements IRelievedAssistContentRecordDao{

	@Override
	public PageResult<Map<String, Object>> findListByCondition(String userName, String communityNumber, Date startTime,
			Date endTime, Long classifyId, Integer pageNo, Integer pageSize,Integer orderType) {
		StringBuffer countHql = new StringBuffer("select count(id) as COUNT from relieved_assist_content_record a");
		countHql.append(" where 1=1 and a.status=0");
		
		StringBuffer queryHql = new StringBuffer("select");
		queryHql.append(" a.id,a.user_name userName,a.status,a.content content,a.assist_image assistImage,");
		queryHql.append(" a.classify_id classifyId,a.shopping_url shoppingUrl,a.community_number communityNumber,a.remarks,");
		queryHql.append(" a.content_type contentType,a.comments_number commentsNumber,a.collection_number collectionNumber,");
		queryHql.append(" a.praise_number praiseNumber,a.shopping_number shoppingNumber,a.positioning_info positioningInfo,");
		queryHql.append(" a.create_date createDate,a.modify_date modifyDate,a.nick_name nickName,a.user_type userType,");
		queryHql.append(" a.top_time topTime,a.browse_number browseNumber,a.shopping_img_url shoppingImgUrl,");
		queryHql.append(" a.shopping_name shoppingName,a.shopping_price shoppingPrice,");
		queryHql.append(" c.community_name communityName,");
		queryHql.append(" r.classify_name classifyName,r.classify_url classifyUrl,r.short_url shortUrl,r.short_name shortName");
		queryHql.append(" from relieved_assist_content_record a");
		/*queryHql.append(" left join xx_member d on d.username = a.user_name");*/
		queryHql.append(" left join community c on c.community_number = a.community_number");
		queryHql.append(" left join relieved_assist_classify r on r.id = a.classify_id");
		queryHql.append(" where 1=1 and a.status=0");
		
		if(StringUtils.isNotBlank(userName)){
			queryHql.append(" and a.user_name =:userName");
			countHql.append(" and a.user_name =:userName");
		}
		if(StringUtils.isNotBlank(communityNumber)){
			queryHql.append(" and a.community_number =:communityNumber");
			countHql.append(" and a.community_number =:communityNumber");
		}
		if(startTime!=null&&endTime!=null){
			queryHql.append(" and a.create_date >=:startTime");
			countHql.append(" and a.create_date >=:startTime");
			queryHql.append(" and a.create_date <=:endTime");
			countHql.append(" and a.create_date <=:endTime");
		}
		if(classifyId!=null){
			queryHql.append(" and a.classify_id =:classifyId");
			countHql.append(" and a.classify_id =:classifyId");
		}
		if(orderType==1){
			queryHql.append(" order by a.collection_number desc");
		}else if(orderType==2){
			queryHql.append(" order by a.praise_number desc");
		}else{
			queryHql.append(" order by a.top_time desc,a.create_date desc");
		}
		
		Query queryCount = this.entityManager.createNativeQuery(countHql.toString());
		Query typedQuery = this.entityManager.createNativeQuery(queryHql.toString());
		
		if(StringUtils.isNotBlank(userName)){
			queryCount.setParameter("userName", userName);
			typedQuery.setParameter("userName", userName);
		}
		if(StringUtils.isNotBlank(communityNumber)){
			queryCount.setParameter("communityNumber", communityNumber);
			typedQuery.setParameter("communityNumber", communityNumber);
		}
		if(startTime!=null&&endTime!=null){
			queryCount.setParameter("startTime", startTime);
			typedQuery.setParameter("startTime", startTime);
			queryCount.setParameter("endTime", endTime);
			typedQuery.setParameter("endTime", endTime);
		}
		if(classifyId!=null){
			queryCount.setParameter("classifyId", classifyId);
			typedQuery.setParameter("classifyId", classifyId);
		}
		
		int count = 0;
		try{
			count = ((BigInteger)queryCount.getSingleResult()).intValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		PageResult<Map<String,Object>> pageResult = new PageResult<Map<String,Object>>();
		pageResult.setTotalNum(count);
		//分页查询
		typedQuery.setFirstResult((pageNo-1)*pageSize);
		typedQuery.setMaxResults(pageSize);
		typedQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> rows = typedQuery.getResultList();
		pageResult.setList(rows);
		return pageResult;
	}

	@Override
	public PageResult<Map<String, Object>> findByIds(String communityNumber,List<Long> idList,Integer pageNo,Integer pageSize,Integer orderType) {
		StringBuffer countHql = new StringBuffer("select count(id) as COUNT from relieved_assist_content_record a");
		countHql.append(" where 1=1 and a.status=0");
		
		StringBuffer queryHql = new StringBuffer("select");
		queryHql.append(" a.id,a.user_name userName,a.status,a.content content,a.assist_image assistImage,");
		queryHql.append(" a.classify_id classifyId,a.shopping_url shoppingUrl,a.community_number communityNumber,a.remarks,");
		queryHql.append(" a.content_type contentType,a.comments_number commentsNumber,a.collection_number collectionNumber,");
		queryHql.append(" a.praise_number praiseNumber,a.shopping_number shoppingNumber,a.positioning_info positioningInfo,");
		queryHql.append(" a.create_date createDate,a.modify_date modifyDate,a.nick_name nickName,a.user_type userType,");
		queryHql.append(" a.top_time topTime,a.browse_number browseNumber,a.shopping_img_url shoppingImgUrl,");
		queryHql.append(" a.shopping_name shoppingName,a.shopping_price shoppingPrice,");
		queryHql.append(" c.community_name communityName,");
		queryHql.append(" r.classify_name classifyName,r.classify_url classifyUrl,r.short_url shortUrl,r.short_name shortName");
		queryHql.append(" from relieved_assist_content_record a");
	/*	queryHql.append(" left join xx_member d on d.username = a.user_name");*/
		queryHql.append(" left join community c on c.community_number = a.community_number");
		queryHql.append(" left join relieved_assist_classify r on r.id = a.classify_id");
		queryHql.append(" where 1=1 and a.status=0");
		
		if(StringUtils.isNotBlank(communityNumber)){
			queryHql.append(" and a.community_number =:communityNumber");
			countHql.append(" and a.community_number =:communityNumber");
		}
		if(CollectionUtils.isNotEmpty(idList)){
			queryHql.append(" and a.id in (:idList)");
			countHql.append(" and a.id in (:idList)");
		}
		if(orderType==1){
			queryHql.append(" order by a.collection_number desc");
		}else if(orderType==2){
			queryHql.append(" order by a.praise_number desc");
		}else{
			queryHql.append(" order by a.top_time desc,a.create_date desc");
		}
		
		Query queryCount = this.entityManager.createNativeQuery(countHql.toString());
		Query typedQuery = this.entityManager.createNativeQuery(queryHql.toString());
		
		if(CollectionUtils.isNotEmpty(idList)){
			queryCount.setParameter("idList", idList);
			typedQuery.setParameter("idList", idList);
		}
		if(StringUtils.isNotBlank(communityNumber)){
			queryCount.setParameter("communityNumber", communityNumber);
			typedQuery.setParameter("communityNumber", communityNumber);
		}
		int count = 0;
		try{
			count = ((BigInteger)queryCount.getSingleResult()).intValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		PageResult<Map<String,Object>> pageResult = new PageResult<Map<String,Object>>();
		pageResult.setTotalNum(count);
		//分页查询
		typedQuery.setFirstResult((pageNo-1)*pageSize);
		typedQuery.setMaxResults(pageSize);
		typedQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> rows = typedQuery.getResultList();
		pageResult.setList(rows);
		return pageResult;
	}

	@Override
	public List<RelievedAssistContentRecord> findByKeyword(String keyword, String communityNumber,int limit) {
		StringBuffer sql = new StringBuffer("select a.* from relieved_assist_content_record a ");
		sql.append(" left join relieved_assist_classify b on a.classify_id = b.id ");
		sql.append(" inner join community c on a.community_number = c.community_number ");
		sql.append(" where 1=1 and a.status=:statusNormal ");
		if(StringUtils.isNotBlank(communityNumber)){
			sql.append(" and (a.community_number is null or a.community_number=:communityNumber) ");
		}else{
			sql.append(" and (a.community_number is null) ");
		}
		sql.append(" and (");
		
		sql.append( "	a.user_name like concat('%',:keyword,'%') ");
		sql.append( "	or a.content like concat('%',:keyword,'%') ");
		sql.append( "	or a.positioning_info like concat('%',:keyword,'%') ");
		
		sql.append( "	or b.classify_name like concat('%',:keyword,'%') ");
		for(int i=1;i<=7;i++){
			sql.append(" or b.classify_copywriting_"+i+" like concat('%',:keyword,'%') ");
		}
		
		sql.append( "	or c.community_name like concat('%',:keyword,'%') ");
		
		sql.append(" ) ");
		sql.append(" limit :limit ");
		Query query = this.entityManager.createNativeQuery(sql.toString(), RelievedAssistContentRecord.class);
		query.setParameter("statusNormal", RelievedAssistContentRecord.Status.Normal.ordinal());
		if(StringUtils.isNotBlank(communityNumber)){
			query.setParameter("communityNumber", communityNumber);
		}
		query.setParameter("keyword", keyword);
		query.setParameter("limit", limit);
		
		return query.getResultList();
	}

	@Override
	public List<RelievedAssistContentRecord> findByUserName(String userName) {
		StringBuffer sql =new StringBuffer(" from RelievedAssistContentRecord");
		sql.append(" where 1=1 and status=0");
		sql.append("and userName =:userName");
		
		TypedQuery<RelievedAssistContentRecord> query 
			=this.entityManager.createQuery(sql.toString(),RelievedAssistContentRecord.class);
		query.setParameter("userName", userName);
		
		List<RelievedAssistContentRecord> list =query.getResultList();
		return list;
	}
	
	
}
