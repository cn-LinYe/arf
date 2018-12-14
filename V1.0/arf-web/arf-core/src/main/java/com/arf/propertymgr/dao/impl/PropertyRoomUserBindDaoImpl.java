package com.arf.propertymgr.dao.impl;

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
import com.arf.propertymgr.dao.IPropertyRoomUserBindDao;
import com.arf.propertymgr.entity.PropertyRoomUserBind;
import com.arf.propertymgr.entity.PropertyRoomUserBind.EnableApplyResp;
import com.arf.propertymgr.entity.PropertyRoomUserBind.Status;

@Repository("propertyRoomUserBindDaoImpl")
public class PropertyRoomUserBindDaoImpl extends BaseDaoImpl<PropertyRoomUserBind, Long> implements IPropertyRoomUserBindDao {

	@Override
	public List<PropertyRoomUserBind> findByUserName(String userName) {
		String hql = "from PropertyRoomUserBind where userName = :userName order by createDate desc";
		TypedQuery<PropertyRoomUserBind> query = entityManager.createQuery(hql,PropertyRoomUserBind.class);
		query.setParameter("userName", userName);
		return query.getResultList();
	}
	
	@Override
	public PropertyRoomUserBind findByUserNameBoundNumber(String userName,String boundNumber) {
		String hql = "from PropertyRoomUserBind where userName = :userName and boundNumber = :boundNumber and status != -1 and status != 2 order by createDate desc";
		TypedQuery<PropertyRoomUserBind> query = entityManager.createQuery(hql,PropertyRoomUserBind.class);
		query.setParameter("userName", userName);
		query.setParameter("boundNumber", boundNumber);
		List<PropertyRoomUserBind> resultList = query.getResultList();
		if(CollectionUtils.isNotEmpty(resultList)){
			return resultList.get(0);
		}else{
			return null;
		}
	}
	
	@Override
	public PropertyRoomUserBind findByUserNameBoundNumberNotUnBound(String userName,String boundNumber) {
		String hql = "from PropertyRoomUserBind where userName = :userName and boundNumber = :boundNumber and status != -1 order by createDate desc";
		TypedQuery<PropertyRoomUserBind> query = entityManager.createQuery(hql,PropertyRoomUserBind.class);
		query.setParameter("userName", userName);
		query.setParameter("boundNumber", boundNumber);
		List<PropertyRoomUserBind> resultList = query.getResultList();
		if(CollectionUtils.isNotEmpty(resultList)){
			return resultList.get(0);
		}else{
			return null;
		}
	}

	@Override
	public PropertyRoomUserBind findByRoomNumber(String roomNumber) {
		String hql = "from PropertyRoomUserBind where roomNumber = :roomNumber and status != -1 and status != 2 order by createDate desc";
		TypedQuery<PropertyRoomUserBind> query = entityManager.createQuery(hql,PropertyRoomUserBind.class);
		query.setParameter("roomNumber", roomNumber);
		List<PropertyRoomUserBind> resultList = query.getResultList();
		PropertyRoomUserBind result = null;
		PropertyRoomUserBind newOne = null;
		PropertyRoomUserBind passOne = null;
		PropertyRoomUserBind modifiedPtyOne = null;
		PropertyRoomUserBind modifiedUserOne = null;
		//兼容一些老数据
		if(CollectionUtils.isNotEmpty(resultList)){
			for (PropertyRoomUserBind pr : resultList) {
				if(pr.getStatus() == PropertyRoomUserBind.Status.NEW.getStaus()){
					newOne = pr;
				}else if(pr.getStatus() == PropertyRoomUserBind.Status.PASS.getStaus()){
					passOne = pr;
				}else if(pr.getStatus() == PropertyRoomUserBind.Status.MODIFIED_PTY.getStaus()){
					modifiedPtyOne = pr;
				}else if(pr.getStatus() == PropertyRoomUserBind.Status.MODIFIED_USER.getStaus()){
					modifiedUserOne = pr;
				}
			}
		}
		if(passOne != null){
			result = passOne;
		}else if(modifiedPtyOne != null){
			result = modifiedPtyOne;
		}else if(modifiedUserOne != null){
			result = modifiedUserOne;
		}else if(newOne != null){
			result = newOne;
		}
//		if(CollectionUtils.isNotEmpty(resultList)){
//			return resultList.get(0);
//		}else{
//			return null;
//		}
		return result;
	}
	
	@Override
	public List<PropertyRoomUserBind> findListByRoomNumber(String roomNumber){
		String hql = "from PropertyRoomUserBind where roomNumber = :roomNumber";
		TypedQuery<PropertyRoomUserBind> query = entityManager.createQuery(hql, PropertyRoomUserBind.class);
		query.setParameter("roomNumber", roomNumber);
		return query.getResultList();
	}

	@Override
	public List<PropertyRoomUserBind> findByUserNameStatus(String userName,
			Status status) {
		StringBuffer hql = new StringBuffer("from PropertyRoomUserBind where userName = :userName");
		if(status != null){
			hql.append(" and status = :status");
		}
		hql.append(" order by createDate desc");
		TypedQuery<PropertyRoomUserBind> query = entityManager.createQuery(hql.toString(),PropertyRoomUserBind.class);
		query.setParameter("userName", userName);
		if(status != null){
			query.setParameter("status", (byte)status.getStaus());
		}
		return query.getResultList();
	}

	@Override
	public List<PropertyRoomUserBind> findByUserNameNewMgruploadModifyedMgr(
			String userName) {
		StringBuffer hql = new StringBuffer("from PropertyRoomUserBind where userName = :userName");
		hql.append(" and (status = 3 or (status = 0 and boundType = 1) or status = 1)");
		hql.append(" order by createDate desc");
		TypedQuery<PropertyRoomUserBind> query = entityManager.createQuery(hql.toString(),PropertyRoomUserBind.class);
		query.setParameter("userName", userName);
		return query.getResultList();
	}

	@Override
	public PropertyRoomUserBind findHouseHolderByRoomNumber(String roomNumber) {
		String hql = "from PropertyRoomUserBind where roomNumber = :roomNumber and (status = 1 or (status = 0 and boundType = 1)) order by status desc";
		TypedQuery<PropertyRoomUserBind> query = entityManager.createQuery(hql,PropertyRoomUserBind.class);
		query.setParameter("roomNumber", roomNumber);
		List<PropertyRoomUserBind> resultList = query.getResultList();
		if(CollectionUtils.isNotEmpty(resultList)){
			return resultList.get(0);
		}else{
			return null;
		}
	}

	@Override
	public List<PropertyRoomUserBind> findByRoomNumbers(List<String> roomNumbers) {
		String hql = "from PropertyRoomUserBind where roomNumber in (:roomNumbers) and (status = 1 or (status = 0 and boundType = 1))";
		TypedQuery<PropertyRoomUserBind> query = entityManager.createQuery(hql,PropertyRoomUserBind.class);
		query.setParameter("roomNumbers", roomNumbers);
		return query.getResultList();
	}

	@Override
	public Integer findBindRoomCount(String userName,String communityNumber) {
	
		StringBuffer sb =new StringBuffer("SELECT ");
		sb.append("(SELECT COUNT(1) FROM pty_property_room_subuser_bind a ");
		sb.append(" left join pty_property_room_info r on a.room_number=r.room_number ");
		sb.append(" WHERE 1=1 and a.user_name =:userName AND a.`status` ='1' ");
		if(StringUtils.isNotBlank(communityNumber)){
			sb.append(" and r.community_number =:communityNumber");
		}
		sb.append(")+");
		sb.append("(SELECT COUNT(1) FROM pty_property_room_user_bind s ");
		sb.append(" left join pty_property_room_info r on s.room_number=r.room_number ");
		sb.append(" WHERE 1=1 and s.user_name =:userName AND s.`status` ='1' ");
		if(StringUtils.isNotBlank(communityNumber)){
			sb.append(" and r.community_number =:communityNumber");
		}
		sb.append(")");
		Query query =entityManager.createNativeQuery(sb.toString());
		query.setParameter("userName", userName);
		if(StringUtils.isNotBlank(communityNumber)){
			query.setParameter("communityNumber", communityNumber);
		}
		int count=0;
		try {
			count=((BigInteger)query.getSingleResult()).intValue();

		} catch (Exception e) {
		}
		return count;
	}

	@Override
	public List<PropertyRoomUserBind> findByUserNameNewMgruploadModifyedMgrRefuse(
			String userName) {
		StringBuffer hql = new StringBuffer("from PropertyRoomUserBind where userName = :userName");
		hql.append(" and (status = 3 or (status = 0 and boundType = 1) or status = 1 or status = 2)");
		hql.append(" order by createDate desc");
		TypedQuery<PropertyRoomUserBind> query = entityManager.createQuery(hql.toString(),PropertyRoomUserBind.class);
		query.setParameter("userName", userName);
		return query.getResultList();
	}

	@Override
	public List<Map<String, Object>> findByCommunityUserNameStatus(String communityNumber, String userName,
			Status status) {
		StringBuffer queryHql = new StringBuffer("select");
		queryHql.append(" a.id,");
		queryHql.append(" a.user_name userName,");
		queryHql.append(" a.room_number roomNumber,");
		queryHql.append(" a.house_holder houseHolder,");
		queryHql.append(" a.house_holder_phone houseHolderPhone,");
		queryHql.append(" a.bound_number boundNumber,");
		queryHql.append(" a.unbound_date unboundDate,");
		queryHql.append(" a.bound_date boundDate,");
		queryHql.append(" a.status,");
		queryHql.append(" a.bound_type boundType,");
		queryHql.append(" a.sponsor sponsor,");
		queryHql.append(" a.auditor auditor,");
		queryHql.append(" a.enable_apply_resp enableApplyResp,");
		queryHql.append(" a.need_first_alert needFirstAlert,");
		queryHql.append(" a.unbound_user unboundUser,");
		queryHql.append(" pi.community_number communityNumber,");
		queryHql.append(" c.community_name communityName,");
		queryHql.append(" pi.building,");
		queryHql.append(" pi.unit,");
		queryHql.append(" pi.floor,");
		queryHql.append(" pi.room");
		queryHql.append(" from pty_property_room_user_bind a");
		queryHql.append(" left join pty_property_room_info pi on pi.room_number = a.room_number");
		queryHql.append(" left join community c on pi.community_number = c.community_number");
		queryHql.append(" where 1=1 and a.user_name =:userName");
		if(StringUtils.isNotBlank(communityNumber)){
			queryHql.append(" and pi.community_number=:communityNumber");
		}
		if(status!=null){
			queryHql.append(" and a.status =:status");
		}
		Query typedQuery = this.entityManager.createNativeQuery(queryHql.toString());
		typedQuery.setParameter("userName", userName);
		if(StringUtils.isNotBlank(communityNumber)){
			typedQuery.setParameter("communityNumber", communityNumber);
		}
		if(status!=null){
			typedQuery.setParameter("status", status.getStaus());
		}
		
		typedQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> rows = typedQuery.getResultList();
		return rows;
	}

}
