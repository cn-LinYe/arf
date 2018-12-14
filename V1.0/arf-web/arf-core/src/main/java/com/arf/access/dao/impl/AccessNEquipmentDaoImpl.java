package com.arf.access.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.arf.access.dao.IAccessNEquipmentDao;
import com.arf.access.entity.AccessNEquipment;
import com.arf.core.dao.impl.BaseDaoImpl;

@Repository("accessNEquipmentDao")
public class AccessNEquipmentDaoImpl extends BaseDaoImpl<AccessNEquipment, Long> implements IAccessNEquipmentDao {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public List<AccessNEquipment> findByCommunityAndType(String communityNo, Integer equipmentType) {
		StringBuffer sql = new StringBuffer(" from AccessNEquipment where 1=1 ");
		if (StringUtils.isNotBlank(communityNo)) {
			sql.append(" and communityNumber=:communityNumber ");
		}
		if (equipmentType != null) {
			sql.append("  and equipmentType=:equipmentType ");
		}
		sql.append(" and equipmentStatus=:equipmentStatus");
		TypedQuery<AccessNEquipment> query = entityManager.createQuery(sql.toString(), AccessNEquipment.class);
		if (StringUtils.isNotBlank(communityNo)) {
			query.setParameter("communityNumber", communityNo);
		}
		if (equipmentType != null) {
			query.setParameter("equipmentType", equipmentType);
		}
		query.setParameter("equipmentStatus", 0);
		List<AccessNEquipment> list = new ArrayList<AccessNEquipment>();
		try {
			list = query.getResultList();
		} catch (Exception e) {
			logger.error("查询新门禁设备sql错误"+e);
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> findByCommunitys(List<String> communityList) {
		if(CollectionUtils.isEmpty(communityList)){
			return null;
		}
		StringBuffer queryHql = new StringBuffer("select anq.id id,");
		queryHql.append(" anq.community_number communityNumber,anq.mid_community_number midCommunityNumber, anq.access_name accessName,");
		queryHql.append(" anq.access_number accessNumber,anq.equipment_type equipmentType,anq.equipment_name equipmentName,anq.equipment_version equipmentVersion,");
		queryHql.append(" anq.equipment_mac equipmentMac,anq.equipment_status equipmentStatus,anq.building_name buildingName,");
		queryHql.append(" anq.unit_name unitName,anq.building building,anq.unit unit,c.community_name communityName,anq.use_type useType  ");
		queryHql.append(" from access_n_equipment anq ");
		queryHql.append(" left join community c on c.community_number=anq.community_number");
		queryHql.append(" where anq.equipment_status=:equipmentStatus and anq.community_number in (:communityList)");
		Query typedQuery = this.entityManager.createNativeQuery(queryHql.toString());
		typedQuery.setParameter("communityList", communityList);
		typedQuery.setParameter("equipmentStatus", 0);
		typedQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		
		try {
			@SuppressWarnings("unchecked")
			List<Map<String,Object>> rows = typedQuery.getResultList();
			return rows;
		} catch (Exception e) {
			logger.error("查询新门禁设备sql错误"+e);
			return null;
		}
	}

	@Override
	public List<AccessNEquipment> findByCommunityByBuilding(String communityNo,String buildName,String unitName,Integer building,Integer unit) {

		StringBuffer sql = new StringBuffer(" from AccessNEquipment where 1=1 ");
		if (StringUtils.isNotBlank(communityNo)) {
			sql.append(" and communityNumber=:communityNo ");
		}
		if (StringUtils.isNotBlank(buildName)) {
			sql.append("  and buildingName=:buildName ");
		}
		if (StringUtils.isNotBlank(unitName)) {
			sql.append("  and unitName=:unitName");
		}
		if (building!=null) {
			sql.append("  and building=:building");
		}
		if (unit!=null) {
			sql.append("  and unit=:unit");
		}
		
		sql.append(" and equipmentStatus=:equipmentStatus");
		TypedQuery<AccessNEquipment> query = entityManager.createQuery(sql.toString(), AccessNEquipment.class);
		if (StringUtils.isNotBlank(communityNo)) {
			query.setParameter("communityNo", communityNo);
		}
		if (StringUtils.isNotBlank(buildName)) {
			query.setParameter("buildName", buildName);
		}
		if (StringUtils.isNotBlank(unitName)&&!"无".equals(unitName)) {
			query.setParameter("unitName", unitName);
		}
		if (building!=null) {
			query.setParameter("building", building);
		}
		if (unit!=null) {
			query.setParameter("unit", unit);
		}
		
		query.setParameter("equipmentStatus", 0);
		List<AccessNEquipment> list = new ArrayList<AccessNEquipment>();
		try {
			list = query.getResultList();
		} catch (Exception e) {
			logger.error("查询新门禁设备sql错误"+e);
		}
		return list;
	
	}

	@Override
	public AccessNEquipment findByAccessNum(String accessNumber) {

		StringBuffer hql = new StringBuffer("from AccessNEquipment where accessNumber = :accessNumber");
		TypedQuery<AccessNEquipment> typedQuery = super.entityManager.createQuery(hql.toString(), AccessNEquipment.class);
		typedQuery.setParameter("accessNumber", accessNumber);
		try{
			return typedQuery.getSingleResult();
		}catch (Exception e) {
			return null;
		}
	}

}
