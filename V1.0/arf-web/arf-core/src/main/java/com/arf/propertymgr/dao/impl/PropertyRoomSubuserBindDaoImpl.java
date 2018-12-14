package com.arf.propertymgr.dao.impl;

import java.util.Arrays;
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

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.propertymgr.dao.IPropertyRoomSubuserBindDao;
import com.arf.propertymgr.entity.PropertyRoomSubuserBind;
import com.arf.propertymgr.entity.PropertyRoomSubuserBind.ManagerStatus;
import com.arf.propertymgr.entity.PropertyRoomSubuserBind.Status;
import com.arf.propertymgr.entity.PropertyRoomSubuserBind.UserType;
import com.arf.propertymgr.entity.PropertyRoomUserBind.EnableApplyResp;

@Repository("propertyRoomSubuserBindDaoImpl")
public class PropertyRoomSubuserBindDaoImpl extends BaseDaoImpl<PropertyRoomSubuserBind, Long> implements IPropertyRoomSubuserBindDao {
	
	@Override
	public PropertyRoomSubuserBind findByBoundNumber(String boundNumber) {
		String hql = "from PropertyRoomSubuserBind where boundNumber = :boundNumber";
		TypedQuery<PropertyRoomSubuserBind> query = entityManager.createQuery(hql,PropertyRoomSubuserBind.class);
		query.setParameter("boundNumber", boundNumber);
		try {
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public PropertyRoomSubuserBind findByUserNameRoomNumber(String userName, String roomNumber){
		String hql = "from PropertyRoomSubuserBind where userName = :userName and roomNumber = :roomNumber order by createDate desc";
		TypedQuery<PropertyRoomSubuserBind> query = entityManager.createQuery(hql,PropertyRoomSubuserBind.class);
		query.setParameter("userName", userName);
		query.setParameter("roomNumber", roomNumber);
		try {
			List<PropertyRoomSubuserBind> propertyRoomSubuserBinds = query.getResultList();
			if(CollectionUtils.isNotEmpty(propertyRoomSubuserBinds)){
				return propertyRoomSubuserBinds.get(0);
			}else{
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public List<PropertyRoomSubuserBind> findByHouseHolderRoomNumber(
			String houseHolder, String roomNumber) {
		String hql = "from PropertyRoomSubuserBind where houseHolder = :houseHolder and roomNumber = :roomNumber order by createDate desc";
		TypedQuery<PropertyRoomSubuserBind> query = entityManager.createQuery(hql,PropertyRoomSubuserBind.class);
		query.setParameter("houseHolder", houseHolder);
		query.setParameter("roomNumber", roomNumber);
		return query.getResultList();
	}
	
	@Override
	public List<PropertyRoomSubuserBind> findNewUserByRoomNumber(String roomNumber){
		String hql = "from PropertyRoomSubuserBind where roomNumber = :roomNumber and status=0";
		TypedQuery<PropertyRoomSubuserBind> query = entityManager.createQuery(hql,PropertyRoomSubuserBind.class);
		query.setParameter("roomNumber", roomNumber);
		return query.getResultList();
	}

	@Override
	public List<PropertyRoomSubuserBind> findByUserName(String userName) {
		String hql = "from PropertyRoomSubuserBind where userName = :userName order by createDate desc";
		TypedQuery<PropertyRoomSubuserBind> query = entityManager.createQuery(hql,PropertyRoomSubuserBind.class);
		query.setParameter("userName", userName);
		return query.getResultList();
	}

	@Override
	public List<PropertyRoomSubuserBind> findByBoundNumbers(
			String[] boundNumbersArray) {
		String hql = "from PropertyRoomSubuserBind where boundNumber in (:boundNumber)";
		TypedQuery<PropertyRoomSubuserBind> query = entityManager.createQuery(hql,PropertyRoomSubuserBind.class);
		query.setParameter("boundNumber", Arrays.asList(boundNumbersArray));
		return query.getResultList();
	}

	@Override
	public int updateByBoundNumbers(String[] boundNumbersArray,Status status,String unboundUser) {
		StringBuffer hql = new StringBuffer("update pty_property_room_subuser_bind set status = :status,unbound_user = :unboundUser,unbound_date = now()");
		hql.append(" where bound_number in(:boundNumber)");
		Query query = entityManager.createNativeQuery(hql.toString());
		query.setParameter("status", status.ordinal());
		query.setParameter("boundNumber", Arrays.asList(boundNumbersArray));
		query.setParameter("unboundUser", unboundUser);
		return query.executeUpdate();
	}

	@Override
	public List<PropertyRoomSubuserBind> findByUserNameUserType(
			String userName, UserType userType) {
		String hql = "from PropertyRoomSubuserBind where userName = :userName and userType = :userType order by createDate desc";
		TypedQuery<PropertyRoomSubuserBind> query = entityManager.createQuery(hql,PropertyRoomSubuserBind.class);
		query.setParameter("userName", userName);
		query.setParameter("userType", userType);
		return query.getResultList();
	}

	@Override
	public PropertyRoomSubuserBind findManager(String roomNumber) {
		String hql = "from PropertyRoomSubuserBind where roomNumber = :roomNumber and userType = :userType";
		TypedQuery<PropertyRoomSubuserBind> query = entityManager.createQuery(hql,PropertyRoomSubuserBind.class);
		query.setParameter("roomNumber", roomNumber);
		query.setParameter("userType", UserType.MANAGER);
		try {
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public List<PropertyRoomSubuserBind> findManagerByRoomNumber(String roomNumber){
		String hql = "from PropertyRoomSubuserBind where roomNumber =:roomNumber and userType =:userType and status =:status";
		TypedQuery<PropertyRoomSubuserBind> query = entityManager.createQuery(hql,PropertyRoomSubuserBind.class);
		query.setParameter("roomNumber", roomNumber);
		query.setParameter("userType", PropertyRoomSubuserBind.UserType.MANAGER);
		query.setParameter("status", PropertyRoomSubuserBind.Status.ACCEPT);
		return query.getResultList();
	}

	@Override
	public List<PropertyRoomSubuserBind> findByRoomNumber(String roomNumber) {
		String hql = "from PropertyRoomSubuserBind where roomNumber =:roomNumber and status =:status";
		TypedQuery<PropertyRoomSubuserBind> query = entityManager.createQuery(hql,PropertyRoomSubuserBind.class);
		query.setParameter("roomNumber", roomNumber);
		query.setParameter("status", PropertyRoomSubuserBind.Status.ACCEPT);
		return query.getResultList();
	}

	@Override
	public List<PropertyRoomSubuserBind> findListByRoomNumber(String roomNumber) {
		String hql = "from PropertyRoomSubuserBind where roomNumber =:roomNumber";
		TypedQuery<PropertyRoomSubuserBind> query = entityManager.createQuery(hql,PropertyRoomSubuserBind.class);
		query.setParameter("roomNumber", roomNumber);
		return query.getResultList();
	}

	@Override
	public List<Map<String, Object>> findByCommunityUserNameStatus(String communityNumber, String userName,
			Status status) {
		StringBuffer queryHql = new StringBuffer("select");
		queryHql.append(" a.id,");
		queryHql.append(" a.user_name userName,");
		queryHql.append(" a.room_number roomNumber,");
		queryHql.append(" a.real_name realName,");
		queryHql.append(" a.house_holder_short_mobile houseHolderShortMobile,");
		queryHql.append(" a.bound_number boundNumber,");
		queryHql.append(" a.unbound_date unboundDate,");
		queryHql.append(" a.bound_date boundDate,");
		queryHql.append(" a.status,");
		queryHql.append(" a.user_type userType,");
		queryHql.append(" a.sponsor sponsor,");
		queryHql.append(" a.house_holder houseHolder,");
		queryHql.append(" a.enable_apply_resp enableApplyResp,");
		queryHql.append(" a.need_first_alert needFirstAlert,");
		queryHql.append(" a.unbound_user unboundUser,");
		queryHql.append(" a.property_audit propertyAudit,");
		queryHql.append(" pi.community_number communityNumber,");
		queryHql.append(" c.community_name communityName,");
		queryHql.append(" pi.building,");
		queryHql.append(" pi.unit,");
		queryHql.append(" pi.floor,");
		queryHql.append(" pi.room");
		queryHql.append(" from pty_property_room_subuser_bind a");
		queryHql.append(" left join pty_property_room_info pi on pi.room_number = a.room_number");
		queryHql.append(" left join community c on pi.community_number = c.community_number");
		queryHql.append(" where 1=1 and a.user_name =:userName ");
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
			typedQuery.setParameter("status", status.ordinal());
		}
		
		typedQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> rows = typedQuery.getResultList();
		return rows;
	}

	@Override
	public List<PropertyRoomSubuserBind> findByRoomList(List<String> roomList) {
		if(CollectionUtils.isEmpty(roomList)){
			return null;
		}
		String hql = "from PropertyRoomSubuserBind where roomNumber in (:roomList) and status =:status";
		TypedQuery<PropertyRoomSubuserBind> query = entityManager.createQuery(hql,PropertyRoomSubuserBind.class);
		query.setParameter("roomList", roomList);
		query.setParameter("status", PropertyRoomSubuserBind.Status.ACCEPT);
		return query.getResultList();
	}

}
