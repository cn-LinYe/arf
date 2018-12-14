package com.arf.access.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.arf.access.dao.IAccessAuthorizationDao;
import com.arf.access.entity.AccessAuthorization;
import com.arf.access.entity.AccessAuthorization.Status;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("accessAuthorizationDaoImpl")
public class AccessAuthorizationDaoImpl extends BaseDaoImpl<AccessAuthorization, Long>
		implements IAccessAuthorizationDao {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public List<AccessAuthorization> findByCommunityNumberAndUserName(String communityNumber, String authorizeUserName,
			String userName) {
		StringBuffer sql = new StringBuffer(" from AccessAuthorization where 1=1 ");
		if (communityNumber != null) {
			sql.append(" and communityNumber=:communityNumber ");
		}
		if (authorizeUserName != null) {
			sql.append("  and authorizeUserName=:authorizeUserName ");
		}
		if (userName != null) {
			sql.append("  and userName=:userName ");
		}
		sql.append(" and authorizeStartDate <=:nowTime");
		sql.append(" and authorizeEndDate >=:nowTime");
		sql.append(" and status=:status");

		TypedQuery<AccessAuthorization> query = entityManager.createQuery(sql.toString(), AccessAuthorization.class);
		if (communityNumber != null) {
			query.setParameter("communityNumber", communityNumber);
		}
		if (authorizeUserName != null) {
			query.setParameter("authorizeUserName", authorizeUserName);
		}
		if (userName != null) {
			query.setParameter("userName", userName);
		}
		query.setParameter("nowTime", new Date());
		query.setParameter("status", com.arf.access.entity.AccessAuthorization.Status.normal);
		List<AccessAuthorization> list = new ArrayList<AccessAuthorization>();
		try {
			list = query.getResultList();
		} catch (Exception e) {

		}
		return list;
	}

	@Override
	public List<AccessAuthorization> findByCommunityNumberAndUserNameStatus(String communityNumber,
			String authorizeUserName, String userName, List<AccessAuthorization.Status> status) {
		StringBuffer sql = new StringBuffer(" from AccessAuthorization where 1=1 and status != 2");
		if (communityNumber != null) {
			sql.append(" and communityNumber=:communityNumber ");
		}
		if (authorizeUserName != null) {
			sql.append("  and authorizeUserName=:authorizeUserName ");
		}
		if (userName != null) {
			sql.append("  and userName=:userName ");
		}
		if (status != null) {
			sql.append(" and status in (:status)");
		}

		TypedQuery<AccessAuthorization> query = entityManager.createQuery(sql.toString(), AccessAuthorization.class);
		if (communityNumber != null) {
			query.setParameter("communityNumber", communityNumber);
		}
		if (authorizeUserName != null) {
			query.setParameter("authorizeUserName", authorizeUserName);
		}
		if (userName != null) {
			query.setParameter("userName", userName);
		}
		if (status != null) {
			query.setParameter("status", status);
		}
		List<AccessAuthorization> list = new ArrayList<AccessAuthorization>();
		try {
			list = query.getResultList();
		} catch (Exception e) {

		}
		return list;
	}
	
	@Override
	public AccessAuthorization findByUserNameRoom(String userName,String communityNumber,String building,String unit,String floor,String room){
		StringBuffer sql = new StringBuffer(" from AccessAuthorization where 1=1 and status = 0 and userName=:userName and communityNumber=:communityNumber ");
		if(StringUtils.isNotBlank(building)){
			sql.append("  and building=:building ");
		}
		if(StringUtils.isNotBlank(unit)){
			sql.append("  and unit=:unit ");
		}
		if(StringUtils.isNotBlank(floor)){
			sql.append("  and floor=:floor ");
		}
		if(StringUtils.isNotBlank(room)){
			sql.append("  and room=:room ");
		}
		TypedQuery<AccessAuthorization> query = entityManager.createQuery(sql.toString(), AccessAuthorization.class);
		query.setParameter("userName", userName);
		query.setParameter("communityNumber", communityNumber);
		if(StringUtils.isNotBlank(building)){
			query.setParameter("building", building);
		}
		if(StringUtils.isNotBlank(unit)){
			query.setParameter("unit", unit);
		}
		if(StringUtils.isNotBlank(floor)){
			query.setParameter("floor", floor);
		}
		if(StringUtils.isNotBlank(room)){
			query.setParameter("room", room);
		}
		
		List<AccessAuthorization> list = query.getResultList();
		if(CollectionUtils.isNotEmpty(list)){
			return list.get(0);
		}else{
			return null;
		}
	}

	@Override
	public List<AccessAuthorization> findByUserName(String userName) {
		StringBuffer sql = new StringBuffer(" from AccessAuthorization where 1=1 and status=0 ");
		if (userName != null) {
			sql.append("  and userName=:userName ");
		}
		TypedQuery<AccessAuthorization> query = entityManager.createQuery(sql.toString(), AccessAuthorization.class);
		if (userName != null) {
			query.setParameter("userName", userName);
		}
		return query.getResultList();
	}

	@Override
	public void updateStatus(List<Long> ids,Status status) {
		StringBuffer sb =new StringBuffer(" update AccessAuthorization a set a.status =:status where a.id in (:ids)");
		entityManager.createQuery(sb.toString()).setParameter("ids", ids).setParameter("status", status).executeUpdate();
	}

	@Override
	public List<AccessAuthorization> findByDateStatus(Status status) {
		StringBuffer sb =new StringBuffer(" from AccessAuthorization a where a.authorizeEndDate <:date and a.status =:status");
		TypedQuery<AccessAuthorization> query =entityManager.createQuery(sb.toString(),AccessAuthorization.class);
		query.setParameter("status", status);
		query.setParameter("date", new Date());
		return query.getResultList();
	}
	@Override
	public void updateByArrayIds(List<Long> ids,Status status) {
		StringBuffer sql =new StringBuffer("UPDATE AccessAuthorization set status=:status WHERE id in(:list)");
		entityManager.createQuery(sql.toString()).setParameter("status", status).setParameter("list", ids).executeUpdate();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<AccessAuthorization> findAllUserByHousrHolderRoomNumber(String userName, String roomNumber,Status status){
		StringBuffer sb = new StringBuffer("select ");
		sb.append(" a.id, a.create_date createDate, a.modify_date modifyDate, a.version,");
		sb.append(" a.user_name  as userName,");
		sb.append(" a.nick_name  as nickName,");
		sb.append(" a.authorize_user_name  as authorizeUserName,");
		sb.append(" a.community_name  as communityName,");
		sb.append(" a.community_number  as communityNumber,");
		sb.append(" a.status  as status,");
		sb.append(" a.user_type  as userType,");
		sb.append(" a.is_temporary  as isTemporary,");
		sb.append(" a.building  as building,");
		sb.append(" a.unit  as unit,");
		sb.append(" a.floor  as floor,");
		sb.append(" a.room  as room,");
		sb.append(" a.access_list  as accessList,");
		sb.append(" a.authorize_start_date  as authorizeStartDate,");
		sb.append(" a.authorize_end_date  as authorizeEndDate,");
		sb.append(" a.room_bound_number  as roomBoundNumber");
		sb.append(" from access_authorization a where a.room_bound_number in ");
		sb.append(" (select bound_number from pty_property_room_subuser_bind t where t.house_holder =:userName and t.room_number =:roomNumber and t.status =:status )");
		Query query = entityManager.createNativeQuery(sb.toString());
		query.setParameter("userName", userName);
		query.setParameter("roomNumber", roomNumber);
		query.setParameter("status", status.ordinal());
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<AccessAuthorization> resultList = new ArrayList<AccessAuthorization>();
		List<Map<String,Object>> rows = query.getResultList();
		for(Map<String,Object> map:rows){
			AccessAuthorization model = JSON.toJavaObject(new JSONObject(map), AccessAuthorization.class);
			resultList.add(model);
		}
		return resultList;
	}

	@Override
	public int updateByBoundNumbers(String[] boundNumbersArray, Status status) {
		StringBuffer hql = new StringBuffer("update access_authorization set status = :status");
		hql.append(" where room_bound_number in(:boundNumber)");
		Query query = entityManager.createNativeQuery(hql.toString());
		query.setParameter("status", status.ordinal());
		query.setParameter("boundNumber", Arrays.asList(boundNumbersArray));
		return query.executeUpdate();
	}

	@Override
	public List<AccessAuthorization> findByRoomBoundNumber(String boundNumber,
			Status status) {
		StringBuffer sb =new StringBuffer(" from AccessAuthorization a where roomBoundNumber = :roomBoundNumber");
		if(status != null){
			sb.append(" and a.status =:status");
		}
		TypedQuery<AccessAuthorization> query =entityManager.createQuery(sb.toString(),AccessAuthorization.class);
		query.setParameter("roomBoundNumber", boundNumber);
		if(status != null){
			query.setParameter("status", status);
		}
		List<AccessAuthorization> accessAuthorizations = query.getResultList();
		if(CollectionUtils.isEmpty(accessAuthorizations)
				|| accessAuthorizations.size() == 1){
			return accessAuthorizations;
		}else{
			List<AccessAuthorization> accessauthorizationBuilding = new ArrayList<AccessAuthorization>();
			List<AccessAuthorization> accessauthorizationGate = new ArrayList<AccessAuthorization>();
			for (AccessAuthorization accessAuthorization : accessAuthorizations) {
				if(StringUtils.isBlank(accessAuthorization.getBuildingName())
						&& StringUtils.isBlank(accessAuthorization.getUnitName())){
					accessauthorizationGate.add(accessAuthorization);
				}else{
					accessauthorizationBuilding.add(accessAuthorization);
				}
			}
			List<AccessAuthorization> accessAuthorizationProcess = new ArrayList<AccessAuthorization>();
			if(CollectionUtils.isNotEmpty(accessauthorizationBuilding)){
				accessAuthorizationProcess.addAll(accessauthorizationBuilding);
			}
			if(CollectionUtils.isNotEmpty(accessauthorizationGate)){
				accessAuthorizationProcess.addAll(accessauthorizationGate);
			}
			return accessAuthorizationProcess;
		}
	}

	/**
	 * 
	 * @param userName
	 * @param communityNumber
	 * @param building
	 * @param unit
	 * @param status
	 * @return
	 */
	@Override
	public AccessAuthorization findByUserNameCommunityNumberRoomInfo(
			String userName, String communityNumber, String building,
			String unit, AccessAuthorization.Status status) {
		StringBuffer sql = new StringBuffer(" from AccessAuthorization where 1=1 and userName=:userName and communityNumber=:communityNumber ");
		if(status != null){
			sql.append("  and status=:status ");
		}
		if(StringUtils.isNotBlank(building)){
			sql.append(" and building = :building");
		}else{
			sql.append(" and (building is null or building = '')");
		}
		if(StringUtils.isNotBlank(unit)){
			sql.append(" and unit = :unit");
		}else{
			sql.append(" and (unit is null or unit = '')");
		}
		TypedQuery<AccessAuthorization> query = entityManager.createQuery(sql.toString(), AccessAuthorization.class);
		query.setParameter("userName", userName);
		query.setParameter("communityNumber", communityNumber);
		if(status != null){
			query.setParameter("status", status);
		}
		if(StringUtils.isNotBlank(building)){
			query.setParameter("building", building);
		}
		if(StringUtils.isNotBlank(unit)){
			query.setParameter("unit", unit);
		}
		
		List<AccessAuthorization> list = query.getResultList();
		if(CollectionUtils.isNotEmpty(list)){
			return list.get(0);
		}else{
			return null;
		}
	}

	@Override
	public List<AccessAuthorization> findByUserNameCommunityNumberRoomInfoUnnormal(
			String userName, String communityNumber, String building,
			String unit) {
		StringBuffer sql = new StringBuffer(" from AccessAuthorization where 1=1 and status != 0 and userName=:userName and communityNumber=:communityNumber ");
		if(StringUtils.isNotBlank(building)){
			sql.append(" and building = :building");
		}else{
			sql.append(" and (building is null or building = '')");
		}
		if(StringUtils.isNotBlank(unit)){
			sql.append(" and unit = :unit");
		}else{
			sql.append(" and (unit is null or unit = '')");
		}
		TypedQuery<AccessAuthorization> query = entityManager.createQuery(sql.toString(), AccessAuthorization.class);
		query.setParameter("userName", userName);
		query.setParameter("communityNumber", communityNumber);
		if(StringUtils.isNotBlank(building)){
			query.setParameter("building", building);
		}
		if(StringUtils.isNotBlank(unit)){
			query.setParameter("unit", unit);
		}
		
		List<AccessAuthorization> list = query.getResultList();
		return list;
	}

	@Override
	public List<AccessAuthorization> findByRoomBoundList(List<String> boundList, Status status) {
		if(CollectionUtils.isEmpty(boundList)){
			return null;
		}
		StringBuffer sb =new StringBuffer(" from AccessAuthorization a where roomBoundNumber in (:boundList)");
		if(status != null){
			sb.append(" and a.status =:status");
		}
		TypedQuery<AccessAuthorization> query =entityManager.createQuery(sb.toString(),AccessAuthorization.class);
		query.setParameter("boundList", boundList);
		if(status != null){
			query.setParameter("status", status);
		}
		List<AccessAuthorization> accessAuthorizations = query.getResultList();
		return accessAuthorizations;
	}

}
