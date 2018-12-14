package com.arf.access.dao.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.arf.access.dao.IAccessManagementDao;
import com.arf.access.dao.IAccessUserDao;
import com.arf.access.entity.AccessManagement;
import com.arf.access.entity.AccessUser;
import com.arf.access.entity.AccessUser.Status;
import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.propertymgr.entity.PropertyOfficeUser;
import com.google.common.collect.Lists;

@Repository("accessUserDaoImpl")
public class AccessUserDaoImpl extends BaseDaoImpl<AccessUser, Long> implements IAccessUserDao {

	@Resource(name = "accessManagementDaoImpl")
	private IAccessManagementDao accessManagementDao;
	
	@Override
	public List<AccessUser> findByUserNameCommunity(String communityNumber, String userName) {
		StringBuffer hql = new StringBuffer(" from AccessUser a where 1=1 and a.accessList is not null and a.userName=:userName and a.status="+AccessUser.Status.normal.ordinal());
		hql.append(" and a.authorizeStartDate<=:nowTime");
		hql.append(" and a.authorizeEndDate>=:nowTime");
		if (communityNumber != null) {
			hql.append(" and a.communityNumber=:communityNumber");
		}
		
		/*hql.append(" group By a.communityNumber");*/
		TypedQuery<AccessUser> query = entityManager.createQuery(hql.toString(), AccessUser.class);
		query.setParameter("userName", userName);
		query.setParameter("nowTime",new Date());
		if (communityNumber != null) {
			query.setParameter("communityNumber", communityNumber);
		}
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String,Object>> findByCondition(String communityNumber,String building,String unit,String keyword){
		//先查询出这个对应的门禁信息
		StringBuffer maSql = new StringBuffer(" from AccessManagement where 1=1 and communityNumber =:communityNumber ");
		if(StringUtils.isNotBlank(building)){
			maSql.append(" and CONVERT(building,SIGNED) = CONVERT(:building,SIGNED) ");
		}
		if(StringUtils.isNotBlank(unit)){
			maSql.append(" and CONVERT(unit,SIGNED) = CONVERT(:unit,SIGNED) ");
		}
		Query maQuery = this.entityManager.createQuery(maSql.toString(),AccessManagement.class);
		maQuery.setParameter("communityNumber", communityNumber);
		if(StringUtils.isNotBlank(building)){
			maQuery.setParameter("building", building);
		}
		if(StringUtils.isNotBlank(unit)){
			maQuery.setParameter("unit", unit);
		}
		List<AccessManagement> accessManagementList = maQuery.getResultList();
		Set<String> managementIds = new HashSet<String>();
		for(AccessManagement accessManagement : accessManagementList){
			managementIds.add(accessManagement.getId().toString());
		}
		StringBuffer sb = new StringBuffer(" select au.id as accessUserId, au.building_name as buildingName, au.unit_name as unitName, "
				+ " au.building, au.unit, au.floor, au.room, au.user_name as userName, au.user_type as userType,"
				+ " au.room_bound_number as roomBoundNumber, ");
		sb.append(" mb.photo as userAvatar, mb.nickname as userNickname ");
		sb.append(" from access_user au ");
		sb.append(" left join xx_member mb on au.user_name = mb.username ");
		sb.append(" where 1=1 and au.status = 0");
		sb.append(" and au.community_number =:communityNumber");
		
		if(managementIds.size() > 0){
			sb.append(" and ( ");
			int index = 0;
			int size = managementIds.size();
			for(String id : managementIds){
				sb.append(" find_in_set('" + id + "', au.access_list) > 0 ");
				if(index != (size - 1)){
					sb.append(" or ");
				}
				index = index + 1;
			}
			if(StringUtils.isNotBlank(building)){
				sb.append(" or (CONVERT(au.building,SIGNED) = CONVERT(:building,SIGNED) ");
				if(StringUtils.isNotBlank(unit)){
					sb.append(" and CONVERT(au.unit,SIGNED) = CONVERT(:unit,SIGNED)) ");
				}else{
					sb.append(" ) ");
				}
			}
			sb.append(" ) ");
		}else{
			if(StringUtils.isNotBlank(building)){
				sb.append(" and CONVERT(au.building,SIGNED) = CONVERT(:building,SIGNED) ");
				if(StringUtils.isNotBlank(unit)){
					sb.append(" and CONVERT(au.unit,SIGNED) = CONVERT(:unit,SIGNED) ");
				}
			}
		}
		
		sb.append(" and (au.room like concat('%',:keyword,'%') or au.user_name =:keyword)");
		sb.append(" group by au.building,au.unit,au.room,au.user_name ");
		Query query = this.entityManager.createNativeQuery(sb.toString());
		query.setParameter("communityNumber", communityNumber);
		if(StringUtils.isNotBlank(building)){
			query.setParameter("building", building);
		}
		if(StringUtils.isNotBlank(unit)){
			query.setParameter("unit", unit);
		}
		query.setParameter("keyword", keyword);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.getResultList();
	}

	@Override
	public AccessUser findByUserNameCommunityBuildingUnit(String communityNumber,
			String userName, String building, String unit,Status status) {
		StringBuffer hql = new StringBuffer(" from AccessUser a where 1=1 and a.accessList is not null and a.userName=:userName");
		hql.append(" and a.communityNumber=:communityNumber");
		if(status != null){
			hql.append(" and a.status = :status ");
		}
		if(StringUtils.isNotBlank(building)){
			hql.append(" and building = :building");
		}else{
			hql.append(" and (building is null or building = '')");
		}
		if(StringUtils.isNotBlank(unit)){
			hql.append(" and unit = :unit");
		}else{
			hql.append(" and (unit is null or unit = '')");
		}
		hql.append(" order by a.createDate desc");
		TypedQuery<AccessUser> query = entityManager.createQuery(hql.toString(), AccessUser.class);
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
		List<AccessUser> accessUserList = query.getResultList();
		if(accessUserList!=null && !accessUserList.isEmpty()){
			return accessUserList.get(0);
		}else{
			return null;
		}
	}
	
	@Override
	public AccessUser findByUserNameBoundNumberStatus(String userName, String roomBoundNumber,Status status){
		StringBuffer hql = new StringBuffer(" from AccessUser a where 1=1 and a.userName =:userName ");
		hql.append(" and a.roomBoundNumber =:roomBoundNumber");
		if(status==AccessUser.Status.normal){
			hql.append(" and a.authorizeStartDate<=:nowTime");
			hql.append(" and a.authorizeEndDate>=:nowTime");
		}
		hql.append(" and a.status=:status order by a.authorizeEndDate");
		TypedQuery<AccessUser> query = entityManager.createQuery(hql.toString(), AccessUser.class);
		query.setParameter("userName", userName);
		query.setParameter("roomBoundNumber", roomBoundNumber);
		if(status==AccessUser.Status.normal){
			query.setParameter("nowTime",new Date());
		}
		query.setParameter("status", status);
		try{
			List<AccessUser> accessUserList = query.getResultList();
			if(CollectionUtils.isNotEmpty(accessUserList)){
				for (AccessUser accessUser : accessUserList) {
					if(StringUtils.isNotBlank(accessUser.getBuildingName())
							|| StringUtils.isNotBlank(accessUser.getUnitName())){
						return accessUser;
					}
				}
			}
			if(CollectionUtils.isNotEmpty(accessUserList)){
				return accessUserList.get(0);
			}else{
				return null;
			}
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public AccessUser findByUserNameCommunityFloorRoom(String communityNumber,
			String userName, String floor, String room) {
		StringBuffer hql = new StringBuffer(" from AccessUser a where 1=1 and a.status = 0 and a.accessList is not null and a.userName=:userName");
		hql.append(" and a.communityNumber=:communityNumber");
		if(StringUtils.isNotBlank(floor)){
			hql.append(" and a.floor=:floor");
		}
		if(StringUtils.isNotBlank(room)){
			hql.append(" and a.room=:room");
		}
		hql.append(" order by a.createDate desc");
		TypedQuery<AccessUser> query = entityManager.createQuery(hql.toString(), AccessUser.class);
		query.setParameter("userName", userName);
		query.setParameter("communityNumber", communityNumber);
		if(StringUtils.isNotBlank(floor)){
			query.setParameter("floor", floor);
		}
		if(StringUtils.isNotBlank(room)){
			query.setParameter("room", room);
		}
		List<AccessUser> accessUserList = query.getResultList();
		if(accessUserList!=null && !accessUserList.isEmpty()){
			return accessUserList.get(0);
		}else{
			return null;
		}
	}

	@Override
	public AccessUser findByUserNameCommunityBuildingNameUnitNameStatus(String communityNumber, String userName, String buildingName, String unitName,
			Status status) {
		StringBuffer hql = new StringBuffer(" from AccessUser a where 1=1 and a.accessList is not null and a.userName=:userName ");
		hql.append(" and a.communityNumber=:communityNumber ");
		if(StringUtils.isNotBlank(buildingName)){
			hql.append(" and a.buildingName=:buildingName ");
		}
		if(StringUtils.isNotBlank(unitName)){
			hql.append(" and a.unitName=:unitName ");
		}
		if(status != null){
			hql.append(" and a.status=:status ");
		}
		hql.append(" order by a.createDate desc ");
		TypedQuery<AccessUser> query = entityManager.createQuery(hql.toString(), AccessUser.class);
		query.setParameter("userName", userName);
		query.setParameter("communityNumber", communityNumber);
		if(StringUtils.isNotBlank(buildingName)){
			query.setParameter("buildingName", buildingName);
		}
		if(StringUtils.isNotBlank(unitName)){
			query.setParameter("unitName", unitName);
		}
		if(status != null){
			query.setParameter("status", status);
		}
		List<AccessUser> accessUserList = query.getResultList();
		if(accessUserList!=null && !accessUserList.isEmpty()){
			return accessUserList.get(0);
		}else{
			return null;
		}
	}

	@Override
	public AccessUser findGateAccessByUserNameCommunity(String communityNumber,
			String userName) {
		StringBuffer hql = new StringBuffer(" from AccessUser a where 1=1 and a.status = 0 and a.communityNumber = :communityNumber and a.userName=:userName");
		hql.append(" and (a.building is null or a.building = '') and (a.unit is null or a.unit = '')");
		hql.append(" order by a.accessList desc, a.createDate desc");
		TypedQuery<AccessUser> query = entityManager.createQuery(hql.toString(), AccessUser.class);
		query.setParameter("userName", userName);
		query.setParameter("communityNumber", communityNumber);
		List<AccessUser> accessUserList = query.getResultList();
		if(accessUserList!=null && !accessUserList.isEmpty()){
			return accessUserList.get(0);
		}else{
			return null;
		}
	}

	@Override
	public List<AccessUser> findByUsernameBoundnumberStatus(String userName,
			String boundNumber, Status status) {
		StringBuffer hql = new StringBuffer(" from AccessUser a where 1=1 and a.userName =:userName ");
		hql.append(" and a.roomBoundNumber =:roomBoundNumber");
		if(status==AccessUser.Status.normal){
			hql.append(" and a.authorizeStartDate<=:nowTime");
			hql.append(" and a.authorizeEndDate>=:nowTime");
		}
		hql.append(" and a.status=:status");
		TypedQuery<AccessUser> query = entityManager.createQuery(hql.toString(), AccessUser.class);
		query.setParameter("userName", userName);
		query.setParameter("roomBoundNumber", boundNumber);
		if(status==AccessUser.Status.normal){
			query.setParameter("nowTime",new Date());
		}
		query.setParameter("status", status);
		return query.getResultList();
	}

	@Override
	public List<AccessUser> findByUserNameCommunityNumberRoomInfoUnnormal(
			String communityNumber, String userName, String building,
			String unit) {
		StringBuffer hql = new StringBuffer(" from AccessUser a where 1=1 and a.status != 0 and a.accessList is not null and a.userName=:userName");
		hql.append(" and a.communityNumber=:communityNumber");
		if(StringUtils.isNotBlank(building)){
			hql.append(" and building = :building");
		}else{
			hql.append(" and (building is null or building = '')");
		}
		if(StringUtils.isNotBlank(unit)){
			hql.append(" and unit = :unit");
		}else{
			hql.append(" and (unit is null or unit = '')");
		}
		hql.append(" order by a.createDate desc");
		TypedQuery<AccessUser> query = entityManager.createQuery(hql.toString(), AccessUser.class);
		query.setParameter("userName", userName);
		query.setParameter("communityNumber", communityNumber);
		if(StringUtils.isNotBlank(building)){
			query.setParameter("building", building);
		}
		if(StringUtils.isNotBlank(unit)){
			query.setParameter("unit", unit);
		}
		List<AccessUser> accessUserList = query.getResultList();
		return accessUserList;
	}

	@Override
	public List<AccessUser> findByBoundnumbersStatus(List<String> boundNumbers,
			Status status) {
		String hql = "from AccessUser where roomBoundNumber in (:boundNumbers) and (status = :status))";
		TypedQuery<AccessUser> query = entityManager.createQuery(hql,AccessUser.class);
		query.setParameter("boundNumbers", boundNumbers);
		query.setParameter("status", status);
		return query.getResultList();
	}

	@Override
	public List<AccessUser> findByUserNameStatus(String user_name, int status) {
		
		StringBuffer hql = new StringBuffer(" from AccessUser a where 1=1 and a.status = 0 and a.userName=:userName");
		
		TypedQuery<AccessUser> query = entityManager.createQuery(hql.toString(), AccessUser.class);
		query.setParameter("userName", user_name);
		//query.setParameter("status", status);
		List<AccessUser> accessUserList = query.getResultList();
		return accessUserList;
	}
	@Override
	public List<AccessUser> findByUserNamesAndCommunityNumbers(List<String> numList, List<String> nameList) {
		if (numList ==null||numList.size()<=0||nameList ==null||nameList.size()<=0) {
			return Lists.newArrayList();
		}
		StringBuffer sb =new StringBuffer(" from AccessUser access where communityNumber in (:communityNumber) and userName in (:userName)and status="+AccessUser.Status.normal.ordinal());
		TypedQuery<AccessUser> query =this.entityManager.createQuery(sb.toString(),AccessUser.class);
		query.setParameter("communityNumber", numList);
		query.setParameter("userName", nameList);
		return query.getResultList();
	}
}
