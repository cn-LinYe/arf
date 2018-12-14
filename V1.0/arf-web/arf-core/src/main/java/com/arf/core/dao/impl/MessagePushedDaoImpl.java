package com.arf.core.dao.impl;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.arf.base.PageResult;
import com.arf.core.dao.MessagePushedDao;
import com.arf.core.entity.MessagePushed;
import com.arf.core.entity.MessagePushed.AppType;
import com.arf.core.entity.MessagePushed.MsgStatus;
import com.arf.core.entity.MessagePushed.PushType;
import com.arf.core.entity.MessagePushed.UserType;
import com.arf.core.util.MD5Util;

@Repository("messagePushedDao")
public class MessagePushedDaoImpl extends BaseDaoImpl<MessagePushed, Long> implements MessagePushedDao{
	
	private Logger log = LoggerFactory.getLogger(MessagePushedDaoImpl.class);

	@Override
	public PageResult<Map<String, Object>> findListByCondition(String userName,UserType userType,String alias,
			Integer pageSize,Integer pageNo, MsgStatus msgStatus, PushType pushType,AppType appType) {
		StringBuffer countHql = new StringBuffer("select count(id) as COUNT from r_message_pushed a");
		countHql.append(" where 1=1 and a.msg_status in (0,1)");
		countHql.append(" and (a.user_name = :userName");
		countHql.append(" or a.alias = :alias)");
		if(msgStatus != null){
			countHql.append(" and a.msg_status = :msgStatus");
		}else{
			countHql.append(" and a.msg_status != " + MessagePushed.MsgStatus.DELETED.ordinal());
		}
		if(pushType != null){
			countHql.append(" and a.push_type = :pushType");
		}
		if(appType != null){
			countHql.append(" and a.app_type = :appType");
		}else{
			countHql.append(" and (a.app_type is null or a.app_type=0)");
		}
		countHql.append(" and a.user_type = :userType");
		StringBuffer queryHql = new StringBuffer("select");
//		queryHql.append(" a.id id,");
		queryHql.append(" a.create_date createDate,");
//		queryHql.append(" a.modify_date modifyDate,");
//		queryHql.append(" a.version version,");
		queryHql.append(" a.alias alias,");
		queryHql.append(" a.content_type contentType,");
		queryHql.append(" a.extras extras,");
		queryHql.append(" a.msg_content msgContent,");
		queryHql.append(" a.msg_id msgId,");
		queryHql.append(" a.msg_status msgStatus,");
		queryHql.append(" a.push_type pushType,");
		queryHql.append(" a.registration_id registrationId,");
		queryHql.append(" a.sendno sendno,");
		queryHql.append(" a.time_to_live timeToLive,");
		queryHql.append(" a.title title,");
		queryHql.append(" a.user_name userName,");
		queryHql.append(" a.user_type userType");
		queryHql.append(" from r_message_pushed a");
		queryHql.append(" where 1=1 and a.msg_status in (0,1)");
		queryHql.append(" and (a.user_name = :userName");
		queryHql.append(" or a.alias = :alias)");
		if(msgStatus != null){
			queryHql.append(" and a.msg_status = :msgStatus");
		}else{
			queryHql.append(" and a.msg_status != " + MessagePushed.MsgStatus.DELETED.ordinal());
		}
		if(pushType != null){
			queryHql.append(" and a.push_type = :pushType");
		}
		if(appType != null){
			queryHql.append(" and a.app_type = :appType");
		}else{
			queryHql.append(" and (a.app_type is null or a.app_type=0)");
		}
		queryHql.append(" and a.user_type = :userType");
		queryHql.append(" order by a.create_date desc");
		
		Query queryCount = this.entityManager.createNativeQuery(countHql.toString());
		queryCount.setParameter("userName", userName);
		queryCount.setParameter("alias", alias);
		if(msgStatus != null){
			queryCount.setParameter("msgStatus", msgStatus.ordinal());
		}
		if(pushType != null){
			queryCount.setParameter("pushType", pushType.ordinal());
		}
		if(appType != null){
			queryCount.setParameter("appType", appType.ordinal());
		}
		queryCount.setParameter("userType", userType.ordinal());
		Query typedQuery = this.entityManager.createNativeQuery(queryHql.toString());
		typedQuery.setParameter("userName", userName);
		typedQuery.setParameter("alias", alias);
		if(msgStatus != null){
			typedQuery.setParameter("msgStatus", msgStatus.ordinal());
		}
		if(pushType != null){
			typedQuery.setParameter("pushType", pushType.ordinal());
		}
		if(appType != null){
			typedQuery.setParameter("appType", appType.ordinal());
		}
		typedQuery.setParameter("userType", userType.ordinal());
		int count = 0;
		try{
			count = ((BigInteger)queryCount.getSingleResult()).intValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		PageResult<Map<String,Object>> pageResult = new PageResult<Map<String,Object>>();
		//查询所有
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

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> statistics(String userName,UserType userType,String alias,AppType appType) {
		StringBuffer queryHql = new StringBuffer("select");
		queryHql.append(" (SELECT COUNT(1) from r_message_pushed a where 1=1 and a.msg_status = 0 and a.user_type = :userType1");
		if(appType != null){
			queryHql.append(" and a.app_type = :appType1");
		}else{
			queryHql.append(" and (a.app_type is null or a.app_type=0)");
		}
		queryHql.append(" and (a.user_name = :userName1");
		queryHql.append(" or a.alias = :alias1");
		queryHql.append(" )) AS 'UNREAD',");
		
		queryHql.append(" (SELECT COUNT(1) from r_message_pushed b where 1=1 and b.msg_status = 1 and b.user_type = :userType2");
		if(appType != null){
			queryHql.append(" and b.app_type = :appType2");
		}else{
			queryHql.append(" and (b.app_type is null or b.app_type=0)");
		}
		queryHql.append(" and (b.user_name = :userName2");
		queryHql.append(" or b.alias = :alias2");
		queryHql.append(" )) AS 'READ',");
		
		queryHql.append(" (SELECT COUNT(1) from r_message_pushed c where 1=1 and c.msg_status = 2 and c.user_type = :userType3");
		if(appType != null){
			queryHql.append(" and c.app_type = :appType3");
		}else{
			queryHql.append(" and (c.app_type is null or c.app_type=0)");
		}
		queryHql.append(" and (c.user_name = :userName3");
		queryHql.append(" or c.alias = :alias3");
		queryHql.append(" )) AS 'DELETED'");
		
		Query typedQuery = this.entityManager.createNativeQuery(queryHql.toString());
		typedQuery.setParameter("userType1", userType.ordinal());
		typedQuery.setParameter("userName1", userName);
		typedQuery.setParameter("alias1", alias);
		typedQuery.setParameter("userType2", userType.ordinal());
		typedQuery.setParameter("userName2", userName);
		typedQuery.setParameter("alias2", alias);
		typedQuery.setParameter("userType3", userType.ordinal());
		typedQuery.setParameter("userName3", userName);
		typedQuery.setParameter("alias3", alias);
		if(appType != null){
			typedQuery.setParameter("appType1", appType.ordinal());
			typedQuery.setParameter("appType2", appType.ordinal());
			typedQuery.setParameter("appType3", appType.ordinal());
		}
		
		typedQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		try {
			return (Map<String, Object>) typedQuery.getSingleResult();
		} catch (Exception e) {
			log.error("查询用户消息数异常",e);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("UNREAD", 0);
			map.put("READ", 0);
			map.put("DELETED", 0);
			return map;
		}
	}

	@Override
	public int deleteRecord(String userName, String alias, String msgIds){
		String[] msgId = msgIds.split(",");
		StringBuffer hql = new StringBuffer("update r_message_pushed set msg_status = :msgStatus where (user_name = :userName or alias = :alias)");
		hql.append(" and (");
		for (int i = 0; i < msgId.length; i++) {
			if(i == msgId.length - 1){
				hql.append(" msg_id = :id" + i);
			}else{
				hql.append(" msg_id = :id" + i + " or");
			}
		}
		hql.append(")");
		Query query = this.entityManager.createNativeQuery(hql.toString());
		query.setParameter("msgStatus", MessagePushed.MsgStatus.DELETED.ordinal());
		query.setParameter("userName", userName);
		query.setParameter("alias", alias);
		for (int i = 0; i < msgId.length; i++) {
			query.setParameter("id" + i, msgId[i]);
		}
		return query.executeUpdate();
	}
	
	@Override
	public int operationRecord(String userName, String alias, String msgIds, MessagePushed.MsgStatus msgStatus,AppType appType){
		if(StringUtils.isBlank(msgIds)){
			return 0;
		}
		String[] msgIdArr = msgIds.split(",");
		StringBuffer sb = new StringBuffer(" update r_message_pushed set msg_status = :msgStatus ");
		sb.append(" where (user_name =:userName or alias =:alias)");
		if(!FLAG_READ_ALL.equals(msgIds.trim())){
			sb.append(" and msg_id in(:msgIds)");
		}
		if(appType != null){
			sb.append(" and app_type = :appType");
		}else{
			sb.append(" and (app_type is null or app_type=0)");
		}
		sb.append(" and msg_status != 2");
		Query query = this.entityManager.createNativeQuery(sb.toString());
		query.setParameter("msgStatus", msgStatus.ordinal());
		query.setParameter("userName", userName);
		query.setParameter("alias", alias);
		if(!FLAG_READ_ALL.equals(msgIds.trim())){
			query.setParameter("msgIds", Arrays.asList(msgIdArr));
		}
		if(appType != null){
			query.setParameter("appType", appType.ordinal());
		}
		return query.executeUpdate();
	}

	@Override
	public MessagePushed findByMsgId(String msgId) {
		StringBuffer sql = new StringBuffer("from MessagePushed where msgId = :msgId");
		TypedQuery<MessagePushed> typedQuery = this.entityManager.createQuery(sql.toString(), MessagePushed.class);
		typedQuery.setParameter("msgId",msgId );
		try {
			return typedQuery.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int findUnreadCount(String userName,MessagePushed.UserType userType) {
		StringBuffer queryHql = new StringBuffer("SELECT COUNT(1) as COUNT from r_message_pushed a "
				+" where a.msg_status = :unreadStatus and a.user_type = :userType "
				+" and (a.user_name = :userName or a.alias = :alias)");
		Query typedQuery = this.entityManager.createNativeQuery(queryHql.toString());
		typedQuery.setParameter("unreadStatus", MessagePushed.MsgStatus.UNREAD.ordinal());
		typedQuery.setParameter("userType", userType.ordinal());
		typedQuery.setParameter("userName", userName);
		typedQuery.setParameter("alias", MD5Util.getMD5(userName));
		int count = 0;
		try{
			count = ((BigInteger)typedQuery.getSingleResult()).intValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public MessagePushed findByMsgUUID(String msgUUID) {
		StringBuffer sql = new StringBuffer("from MessagePushed where msgUUID = :msgUUID");
		TypedQuery<MessagePushed> typedQuery = this.entityManager.createQuery(sql.toString(), MessagePushed.class);
		typedQuery.setParameter("msgUUID",msgUUID );
		try {
			return typedQuery.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<MessagePushed> findByTransactionIdContentType(String transactionId,String contentType){
		StringBuffer sb = new StringBuffer(" from MessagePushed where transactionId =:transactionId and contentType=:contentType");
		TypedQuery<MessagePushed> typedQuery = entityManager.createQuery(sb.toString(), MessagePushed.class);
		typedQuery.setParameter("transactionId", transactionId);
		typedQuery.setParameter("contentType", contentType);
		return typedQuery.getResultList();
	}
}
