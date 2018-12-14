package com.arf.propertymgr.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
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

import com.alibaba.fastjson.JSON;
import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.propertymgr.dao.IPropertyRoomInfoDao;
import com.arf.propertymgr.dto.PropertyRoomDto;
import com.arf.propertymgr.dto.PropertyRoomInfoDto;
import com.arf.propertymgr.entity.PropertyRoomInfo;
import com.arf.propertymgr.entity.PropertyRoomInfo.RoomType;
import com.arf.propertymgr.entity.PropertyRoomSubuserBind;
import com.arf.propertymgr.entity.PropertyRoomUserBind;
import com.arf.propertymgr.entity.PtyPropertyFeeConfig.Compare;

@Repository("propertyRoomInfoDaoImpl")
public class PropertyRoomInfoDaoImpl extends BaseDaoImpl<PropertyRoomInfo, Long> implements IPropertyRoomInfoDao {

	@Override
	public PropertyRoomInfo findByRoomNumber(String roomNumber) {
		String hql = "from PropertyRoomInfo where roomNumber = :roomNumber";
		TypedQuery<PropertyRoomInfo> query = entityManager.createQuery(hql,PropertyRoomInfo.class);
		query.setParameter("roomNumber", roomNumber);
		try {
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<PropertyRoomInfo> findByCommunityGbyBuilding(String communityNumber) {
		StringBuffer hql = new StringBuffer(" select id,community_number,building from pty_property_room_info");
		hql.append(" where community_number = :community_number");
		hql.append(" GROUP BY building ORDER BY building");
		Query query =entityManager.createNativeQuery(hql.toString().toString());
		query.setParameter("community_number", communityNumber);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String,Object>> propertyRoomInfoMap = query.getResultList();
		List<PropertyRoomInfo> propertyRoomInfoMapList = null; 
		if(CollectionUtils.isNotEmpty(propertyRoomInfoMap)){
			propertyRoomInfoMapList = new ArrayList<PropertyRoomInfo>();
			for (Map<String, Object> map : propertyRoomInfoMap) {
				try {
					PropertyRoomInfo propertyRoomInfo = new PropertyRoomInfo();
					propertyRoomInfo.setId(Long.valueOf(map.get("id").toString()));
					propertyRoomInfo.setCommunityNumber(map.get("community_number").toString());
					propertyRoomInfo.setBuilding(map.get("building").toString());
					propertyRoomInfoMapList.add(propertyRoomInfo);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return propertyRoomInfoMapList;
		}else{
			return null;
		}
	}

	@Override
	public List<PropertyRoomInfo> findByCommunityBuildingGbyUnits(String communityNumber, String building) {
		StringBuffer hql = new StringBuffer(" select id,community_number,building,unit from pty_property_room_info");
		hql.append(" where community_number = :community_number and building = :building");
		hql.append(" GROUP BY unit ORDER BY unit");
		Query query =entityManager.createNativeQuery(hql.toString().toString());
		query.setParameter("community_number", communityNumber);
		query.setParameter("building", building);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String,Object>> propertyRoomInfoMap = query.getResultList();
		List<PropertyRoomInfo> propertyRoomInfoMapList = null; 
		if(CollectionUtils.isNotEmpty(propertyRoomInfoMap)){
			propertyRoomInfoMapList = new ArrayList<PropertyRoomInfo>();
			for (Map<String, Object> map : propertyRoomInfoMap) {
				try {
					PropertyRoomInfo propertyRoomInfo = new PropertyRoomInfo();
					propertyRoomInfo.setId(Long.valueOf(map.get("id").toString()));
					propertyRoomInfo.setCommunityNumber(map.get("community_number").toString());
					propertyRoomInfo.setBuilding(map.get("building").toString());
					propertyRoomInfo.setUnit(map.get("unit").toString());
					propertyRoomInfoMapList.add(propertyRoomInfo);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return propertyRoomInfoMapList;
		}else{
			return null;
		}
	}

	@Override
	public List<PropertyRoomInfo> findByCommunityBuildingUnitsGbyFloor(String communityNumber, String building, String unit) {
		StringBuffer hql = new StringBuffer(" select id,community_number,building,unit,floor from pty_property_room_info");
		hql.append(" where community_number = :community_number and building = :building and unit = :unit");
		hql.append(" GROUP BY floor ORDER BY floor");
		Query query =entityManager.createNativeQuery(hql.toString().toString());
		query.setParameter("community_number", communityNumber);
		query.setParameter("building", building);
		query.setParameter("unit", unit);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String,Object>> propertyRoomInfoMap = query.getResultList();
		List<PropertyRoomInfo> propertyRoomInfoMapList = null; 
		if(CollectionUtils.isNotEmpty(propertyRoomInfoMap)){
			propertyRoomInfoMapList = new ArrayList<PropertyRoomInfo>();
			for (Map<String, Object> map : propertyRoomInfoMap) {
				try {
					PropertyRoomInfo propertyRoomInfo = new PropertyRoomInfo();
					propertyRoomInfo.setId(Long.valueOf(map.get("id").toString()));
					propertyRoomInfo.setCommunityNumber(map.get("community_number").toString());
					propertyRoomInfo.setBuilding(map.get("building").toString());
					propertyRoomInfo.setUnit(map.get("unit").toString());
					propertyRoomInfo.setFloor(map.get("floor").toString());
					propertyRoomInfoMapList.add(propertyRoomInfo);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return propertyRoomInfoMapList;
		}else{
			return null;
		}
	}

	@Override
	public List<PropertyRoomInfo> findByComBuilUnitFloor(String communityNumber, String building, String unit, String floor) {
		StringBuffer hql = new StringBuffer(" select id,community_number,building,unit,floor,room,room_number from pty_property_room_info");
		hql.append(" where community_number = :community_number and building = :building and unit = :unit and floor = :floor");
		hql.append(" ORDER BY room");
		Query query =entityManager.createNativeQuery(hql.toString().toString());
		query.setParameter("community_number", communityNumber);
		query.setParameter("building", building);
		query.setParameter("unit", unit);
		query.setParameter("floor", floor);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String,Object>> propertyRoomInfoMap = query.getResultList();
		List<PropertyRoomInfo> propertyRoomInfoMapList = null; 
		if(CollectionUtils.isNotEmpty(propertyRoomInfoMap)){
			propertyRoomInfoMapList = new ArrayList<PropertyRoomInfo>();
			for (Map<String, Object> map : propertyRoomInfoMap) {
				try {
					PropertyRoomInfo propertyRoomInfo = new PropertyRoomInfo();
					propertyRoomInfo.setId(Long.valueOf(map.get("id").toString()));
					propertyRoomInfo.setCommunityNumber(map.get("community_number").toString());
					propertyRoomInfo.setBuilding(map.get("building").toString());
					propertyRoomInfo.setUnit(map.get("unit").toString());
					propertyRoomInfo.setFloor(map.get("floor").toString());
					propertyRoomInfo.setRoom(map.get("room").toString());
					propertyRoomInfo.setRoomNumber(map.get("room_number").toString());
					propertyRoomInfoMapList.add(propertyRoomInfo);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return propertyRoomInfoMapList;
		}else{
			return null;
		}
	}

	@Override
	public List<PropertyRoomInfo> findByCommunity(String communityNumber) {
		String hql = "from PropertyRoomInfo where communityNumber = :communityNumber";
		TypedQuery<PropertyRoomInfo> query = entityManager.createQuery(hql,PropertyRoomInfo.class);
		query.setParameter("communityNumber", communityNumber);
		return query.getResultList();
	}

	@Override
	public List<PropertyRoomInfoDto> findByCommunityNumber(
			String communityNumber) {
		StringBuffer hql = new StringBuffer(" select");
		hql.append(" a.id,");
		hql.append(" a.create_date,");
		hql.append(" a.modify_date,");
		hql.append(" a.version,");
		hql.append(" a.acreage,");
		hql.append(" a.billing_period,");
		hql.append(" a.building,");
		hql.append(" a.charge_type,");
		hql.append(" a.community_number,");
		hql.append(" a.floor,");
		hql.append(" a.price_perm2,");
		hql.append(" a.room,");
		hql.append(" a.room_number,");
		hql.append(" a.unit,");
		hql.append(" b.user_name AS houseHolderUserName,");
		hql.append(" b.status");
		hql.append(" from pty_property_room_info a");
		hql.append(" left join pty_property_room_user_bind b on b.room_number = a.room_number");
		hql.append(" and ((b.bound_type =1 and (b.status = 0 or b.status = 1))");
		hql.append(" or (b.bound_type =0 and b.status = 1))");
		hql.append(" where a.community_number = :communityNumber");
		Query query =entityManager.createNativeQuery(hql.toString().toString());
		query.setParameter("communityNumber", communityNumber);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String,Object>> propertyRoomInfoMap = query.getResultList();
		List<PropertyRoomInfoDto> propertyRoomInfoMapList = null; 
		if(CollectionUtils.isNotEmpty(propertyRoomInfoMap)){
			propertyRoomInfoMapList = new ArrayList<PropertyRoomInfoDto>();
			for (Map<String, Object> map : propertyRoomInfoMap) {
				try {
					PropertyRoomInfoDto propertyRoomInfo = new PropertyRoomInfoDto();
					Object id = map.get("id");
					Object create_date = map.get("create_date");
					Object modify_date = map.get("modify_date");
					Object version = map.get("version");
					Object acreage = map.get("acreage");
					Object billing_period = map.get("billing_period");
					Object building = map.get("building");
					Object charge_type = map.get("charge_type");
					Object community_number = map.get("community_number");
					Object floor = map.get("floor");
					Object price_perm2 = map.get("price_perm2");
					Object room = map.get("room");
					Object room_number = map.get("room_number");
					Object unit = map.get("unit");
					Object houseHolderUserName = map.get("houseHolderUserName");
					Object status = map.get("status");
					propertyRoomInfo.setId(Long.valueOf(id.toString()));
					propertyRoomInfo.setCreateDate((Date)create_date);
					propertyRoomInfo.setModifyDate((Date)modify_date);
					propertyRoomInfo.setVersion(Long.valueOf(version.toString()));
					propertyRoomInfo.setAcreage(acreage==null?null:(BigDecimal)acreage);
					propertyRoomInfo.setBillingPeriod((Byte)billing_period);
					propertyRoomInfo.setBuilding(building==null?null:building.toString());
					propertyRoomInfo.setChargeType((Byte)charge_type);
					propertyRoomInfo.setCommunityNumber(community_number==null?null:community_number.toString());
					propertyRoomInfo.setFloor(floor==null?null:floor.toString());
					propertyRoomInfo.setPricePerM2(price_perm2==null?null:(BigDecimal)price_perm2);
					propertyRoomInfo.setRoom(room==null?null:room.toString());
					propertyRoomInfo.setRoomNumber(room_number==null?null:room_number.toString());
					propertyRoomInfo.setUnit(unit==null?null:unit.toString());
					propertyRoomInfo.setHouseHolderUserName(houseHolderUserName==null?null:houseHolderUserName.toString());
					propertyRoomInfo.setStatus(status==null?null:Integer.parseInt(status.toString()));
					propertyRoomInfoMapList.add(propertyRoomInfo);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return propertyRoomInfoMapList;
		}else{
			return null;
		}
	}

	@Override
	public PropertyRoomInfo findByComBuilUnitFloorRoom(String communityNumber,
			String building, String unit, String floor, String room) {
		StringBuffer hql = new StringBuffer("from PropertyRoomInfo where communityNumber = :communityNumber");
		hql.append(" and building = :building");
		hql.append(" and unit = :unit");
		hql.append(" and floor = :floor");
		hql.append(" and room = :room");
		TypedQuery<PropertyRoomInfo> query = entityManager.createQuery(hql.toString(),PropertyRoomInfo.class);
		query.setParameter("communityNumber", communityNumber);
		query.setParameter("building", building);
		query.setParameter("unit", unit);
		query.setParameter("floor", floor);
		query.setParameter("room", room);
		try {
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<PropertyRoomInfo> findByCommunityBuildingUnits(
			String communityNumber, String buildingName, String unitName) {
		StringBuffer hql = new StringBuffer("from PropertyRoomInfo where communityNumber = :communityNumber");
		if(StringUtils.isNotBlank(buildingName)){
			hql.append(" and building = :buildingName");
		}
		if(StringUtils.isNotBlank(unitName)){
			hql.append(" and unit = :unitName");
		}
		TypedQuery<PropertyRoomInfo> query = entityManager.createQuery(hql.toString(),PropertyRoomInfo.class);
		query.setParameter("communityNumber", communityNumber);
		if(StringUtils.isNotBlank(buildingName)){
			query.setParameter("buildingName", buildingName);
		}
		if(StringUtils.isNotBlank(unitName)){
			query.setParameter("unitName", unitName);
		}
		return query.getResultList();
	}

	@Override
	public List<PropertyRoomInfo> findByComBuilUnitRoom(String communityNumber, String buildingName, String unitName,
			String room) {
		StringBuffer hql = new StringBuffer(" from PropertyRoomInfo where 1=1 ");
		hql.append(" and communityNumber = :communityNumber ");
		if(StringUtils.isNotBlank(buildingName)){
			hql.append(" and building = :buildingName");
		}
		if(StringUtils.isNotBlank(unitName)){
			hql.append(" and unit = :unitName");
		}
		if(StringUtils.isNotBlank(buildingName)){
			hql.append(" and room like concat('%',:room,'%')");
		}
		TypedQuery<PropertyRoomInfo> query = entityManager.createQuery(hql.toString(),PropertyRoomInfo.class);
		query.setParameter("communityNumber", communityNumber);
		if(StringUtils.isNotBlank(buildingName)){
			query.setParameter("buildingName", buildingName);
		}
		if(StringUtils.isNotBlank(unitName)){
			query.setParameter("unitName", unitName);
		}
		if(StringUtils.isNotBlank(room)){
			query.setParameter("room", room);
		}
		return query.getResultList();
	}

	@Override
	public List<PropertyRoomDto> findByConfig(String communityNumber, String building, RoomType roomType, String floor,
			Compare compare) {
		StringBuffer sb =new StringBuffer("SELECT p.create_date as createDate ,p.modify_date as modifyDate ,p.version as version,p.id as id,p.community_number as communityNumber,");
		sb.append(" p.building as building ,p.unit as unit ,p.room as room,p.floor as floor,p.room_index as roomIndex,p.acreage as acreage ,p.billing_period as billingPeriod,");
		sb.append(" p.price_perm2 as pricePerM2,p.charge_type as chargeType,p.room_number as roomNumber,p.end_time as endTime,p.room_type as roomType,");
		sb.append(" ub.user_name as userName ,ub.bound_number as boundNumber ");
		sb.append(" FROM pty_property_room_info p  ");
		sb.append(" LEFT JOIN pty_property_room_user_bind ub ON ub.room_number =p.room_number ");
		sb.append(" WHERE ub.`status` =1 ");
		sb.append(" AND p.community_number =:communityNumber");
		
		if (StringUtils.isNotBlank(building)) {
			sb.append(" AND p.building=:building");
		}
		if (roomType!=null) {
			sb.append(" and p.room_type=:roomType");
		}
		if (floor!=null&&compare!=null) {
			if (compare==Compare.equal) {
				sb.append(" AND p.floor=:floor");
			}
			if (compare==Compare.less) {
				sb.append(" AND p.floor<=:floor");
			}
			if (compare==Compare.greater) {
				sb.append(" AND p.floor>=:floor");
			}
		}
		sb.append(" AND p.end_time<now() ");
		
		Query query =entityManager.createNativeQuery(sb.toString());
		query.setParameter("communityNumber", communityNumber);
		if (StringUtils.isNotBlank(building)) {
			query.setParameter("building", building);
		}
		if (roomType!=null) {
			query.setParameter("roomType", roomType.ordinal());
		}
		if (floor!=null&&compare!=null) {
			query.setParameter("floor", floor);
		}
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> rows = query.getResultList();
		List<PropertyRoomDto> list =new ArrayList<PropertyRoomDto>();
		if (CollectionUtils.isNotEmpty(rows)) {
			list = JSON.parseArray(JSON.toJSONString(rows), PropertyRoomDto.class);
		}
		return list;
	}

	@Override
	public void updateById(List<Long> list) {
		StringBuffer sb =new StringBuffer("update PropertyRoomInfo set endTime =now() where id in(:list)");
		entityManager.createQuery(sb.toString()).setParameter("list", list).executeUpdate();
	}

	@Override
	public List<PropertyRoomInfo> findRoomInfoByCondition(String communityNumber, String buildingName, String unitName,
			String floor, String room) {
		StringBuffer hql = new StringBuffer(" from PropertyRoomInfo where 1=1 ");
		hql.append(" and communityNumber = :communityNumber ");
		if(StringUtils.isNotBlank(buildingName)){
			hql.append(" and building  like concat('',:buildingName,'%')");
		}
		if(StringUtils.isNotBlank(unitName)){
			hql.append(" and unit like concat('',:unitName,'%')");
		}
		if(StringUtils.isNotBlank(floor)){
			hql.append(" and floor like concat('',:floor,'%')");
		}
		if(StringUtils.isNotBlank(room)){
			hql.append(" and room like concat('',:room,'%')");
		}
		TypedQuery<PropertyRoomInfo> query = entityManager.createQuery(hql.toString(),PropertyRoomInfo.class);
		query.setParameter("communityNumber", communityNumber);
		if(StringUtils.isNotBlank(buildingName)){
			query.setParameter("buildingName", buildingName);
		}
		if(StringUtils.isNotBlank(unitName)){
			query.setParameter("unitName", unitName);
		}
		if(StringUtils.isNotBlank(floor)){
			query.setParameter("floor", floor);
		}
		if(StringUtils.isNotBlank(room)){
			query.setParameter("room", room);
		}
		return query.getResultList();
	}

	@Override
	public List<PropertyRoomInfo> findByRoomNumbers(List<String> roomNumberList) {
		String hql = "from PropertyRoomInfo where roomNumber in (:roomNumberList)";
		TypedQuery<PropertyRoomInfo> query = entityManager.createQuery(hql,PropertyRoomInfo.class);
		query.setParameter("roomNumberList", roomNumberList);
		return query.getResultList();
	}
	
	@Override
	public List<PropertyRoomInfo> findByCommunityNumbers(List<String> communityNumberList) {
		String hql = "from PropertyRoomInfo where communityNumber in (:communityNumberList)";
		TypedQuery<PropertyRoomInfo> query = entityManager.createQuery(hql,PropertyRoomInfo.class);
		query.setParameter("communityNumberList", communityNumberList);
		return query.getResultList();
	}

	@Override
	public List<Map<String, Object>> searchByKeyWord(String communityNumber, String buildingName, String unitName,
			String keyword) {
		StringBuffer sb = new StringBuffer(
				"	select t.* from ("+
				"		( "+
				" 			select "+
				"				a.room_number as roomNumber, "+
				"				ub.user_name as userName, "+
				"				ub.bound_number as roomBoundNumber,"+
				"				a.floor as floor, "+
				"				a.room as room,"+
				"				a.building as buildingName, "+
				"				a.unit as unitName "+
				"			from pty_property_room_info a "+
				" 			inner join pty_property_room_user_bind ub on a.room_number = ub.room_number "+
				" 			where 1=1 and ub.status in("
								+PropertyRoomUserBind.Status.PASS.getStaus()
								+","
								+PropertyRoomUserBind.Status.MODIFIED_PTY.getStaus()
								+","
								+PropertyRoomUserBind.Status.MODIFIED_USER.getStaus()
								+")"+
				" 			and a.community_number = :communityNumber ");
		if(StringUtils.isNotBlank(buildingName)){
			sb.append(
				" 			and ifnull(if(a.building='无','',a.building),'') = ifnull(:buildingName,'') "
			);
		}
		if(StringUtils.isNotBlank(unitName)){
			sb.append(
				" 			and ifnull(if(a.unit='无','',a.unit),'') = ifnull(:unitName,'') "
			);
		}
		
		
		sb.append(	
				" 			and (a.room like concat('%',:keyword,'%') or ub.user_name like concat('%',:keyword,'%')) "+
				"		) "+
				" 		union all "+
				" 		( "+
				" 			select "+
				"				a.room_number as roomNumber, "+
				"				usb.user_name as userName, "+
				"				usb.bound_number as roomBoundNumber,"+
				"				a.floor as floor, "+
				"				a.room as room, "+
				"				a.building as buildingName, "+
				"				a.unit as unitName "+
				"			from pty_property_room_info a "+
				" 			inner join pty_property_room_subuser_bind usb on a.room_number = usb.room_number "+
				" 			where 1=1 and usb.status in("+PropertyRoomSubuserBind.Status.ACCEPT.ordinal()+")"+
				" 			and a.community_number = :communityNumber ");
		if(StringUtils.isNotBlank(buildingName)){
			sb.append(
				" 			and ifnull(if(a.building='无','',a.building),'') = ifnull(:buildingName,'') "
			);
		}
		if(StringUtils.isNotBlank(unitName)){
			sb.append(
				" 			and ifnull(if(a.unit='无','',a.unit),'') = ifnull(:unitName,'') "
			);
		}
		sb.append(
				" 			and (a.room like concat('%',:keyword,'%') or usb.user_name like concat('%',:keyword,'%')) "+
				" 		)"+
				"	) t group by t.roomNumber"
		);
		Query query = entityManager.createNativeQuery(sb.toString());
		query.setParameter("communityNumber",communityNumber);
		if(StringUtils.isNotBlank(buildingName)){
			query.setParameter("buildingName",buildingName);
		}
		
		if(StringUtils.isNotBlank(unitName)){
			query.setParameter("unitName",unitName);
		}
		query.setParameter("keyword",keyword);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> rows = query.getResultList();
		return rows;
	}

	
}
