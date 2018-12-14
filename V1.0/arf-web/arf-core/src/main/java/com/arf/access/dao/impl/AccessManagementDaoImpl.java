package com.arf.access.dao.impl;

import java.util.Arrays;
import java.util.List;

import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.arf.access.dao.IAccessManagementDao;
import com.arf.access.entity.AccessManagement;
import com.arf.access.entity.AccessManagement.AccessType;
import com.arf.access.entity.AccessManagement.AuthencateAccessType;
import com.arf.access.entity.AccessManagement.Status;
import com.arf.core.dao.impl.BaseDaoImpl;
import com.google.common.collect.Lists;

@Repository("accessManagementDaoImpl")
public class AccessManagementDaoImpl extends BaseDaoImpl<AccessManagement, Long> implements IAccessManagementDao{

	@Override
	public List<AccessManagement> findByIds(Long[] array) {
		if (array ==null||array.length<=0) {
			return Lists.newArrayList();
		}
		StringBuffer sb =new StringBuffer(" from AccessManagement access where 1=1 and access.id in (:array)");
		TypedQuery<AccessManagement> query =this.entityManager.createQuery(sb.toString(),AccessManagement.class);
		query.setParameter("array", Arrays.asList(array));
		return query.getResultList();
	}
	@Override
	public List<AccessManagement> findByIdsAndStatus(Long[] Ids, String bluetoothMac, Status status) {
		StringBuffer sql =new StringBuffer(" from AccessManagement access");
		sql.append(" where 1=1  and access.id in (:Ids)");
		if(StringUtils.isNotBlank(bluetoothMac)){
			sql.append(" and bluetooth_mac = :bluetoothMac");
		}
		if(status!=null){
			sql.append(" and status = :status");
		}
		TypedQuery<AccessManagement> query =this.entityManager.createQuery(sql.toString(),AccessManagement.class);
		query.setParameter("Ids", Arrays.asList(Ids));
		if(StringUtils.isNotBlank(bluetoothMac)){
			query.setParameter("bluetoothMac", bluetoothMac);
		}
		if(status!=null){
			query.setParameter("status", status.getStaus());
		}
		
		List<AccessManagement> list =query.getResultList();
		return list;
	}

	@Override
	public List<AccessManagement> findByCommunityNumberAndStatus(String communityNumber,String keyword, String bluetoothMac,Status status) {
		StringBuffer hql = new StringBuffer("from AccessManagement where communityNumber = :communityNumber");
		
		if(StringUtils.isNotBlank(bluetoothMac)){
			hql.append(" and bluetoothMac = :bluetoothMac");
		}
		if(status!=null){
			hql.append(" and status = :status");
		}
		if(StringUtils.isNotBlank(keyword)){
			hql.append(" and (accessName like CONCAT('%',:keyword,'%') or buildingName like CONCAT('%',:keyword,'%') or unitName like CONCAT('%',:keyword,'%'))");
		}
		hql.append(" order by createDate desc");
		TypedQuery<AccessManagement> typedQuery = super.entityManager.createQuery(hql.toString(), AccessManagement.class);
		
		typedQuery.setParameter("communityNumber", communityNumber);
		if(StringUtils.isNotBlank(bluetoothMac)){
			typedQuery.setParameter("bluetoothMac", bluetoothMac);
		}
		if(status!=null){
			typedQuery.setParameter("status", status.getStaus());
		}
		if(StringUtils.isNotBlank(keyword)){
			typedQuery.setParameter("keyword", keyword);
		}
		List<AccessManagement> list = typedQuery.getResultList();
		
		return list;
	}

	@Override
	public List<AccessManagement> findByCommunityNumberMacIsnull(String communityNumber) {
		StringBuffer hql = new StringBuffer("from AccessManagement where communityNumber = :communityNumber");
		hql.append(" and (bluetoothMac is null or bluetoothMac = '')");
		hql.append(" order by createDate desc");
		TypedQuery<AccessManagement> typedQuery = super.entityManager.createQuery(hql.toString(), AccessManagement.class);
		typedQuery.setParameter("communityNumber", communityNumber);
		return typedQuery.getResultList();
	}

	@Override
	public AccessManagement findByAccessNum(String accessNum) {
		StringBuffer hql = new StringBuffer("from AccessManagement where access_num = :accessNum");
		TypedQuery<AccessManagement> typedQuery = super.entityManager.createQuery(hql.toString(), AccessManagement.class);
		typedQuery.setParameter("accessNum", accessNum);
		try{
			return typedQuery.getSingleResult();
		}catch (Exception e) {
			return null;
		}
	}
	@Override
	public List<AccessManagement> findByCommunityStatusAccessType(
			String communityNumber, Status status, AccessType accessType) {
		StringBuffer hql = new StringBuffer("from AccessManagement where communityNumber = :communityNumber");
		hql.append(" and status = :status");
		hql.append(" and accessType = :accessType");
		TypedQuery<AccessManagement> typedQuery = super.entityManager.createQuery(hql.toString(), AccessManagement.class);
		typedQuery.setParameter("communityNumber", communityNumber);
		typedQuery.setParameter("status", status.staus);
		typedQuery.setParameter("accessType", accessType.ordinal());
		return typedQuery.getResultList();
	}
	@Override
	public AccessManagement findByCommunityStatusAccessTypeRoom(
			String communityNumber, Status status, AccessType accessType,
			String buildingName, String unitName) {
		StringBuffer hql = new StringBuffer("from AccessManagement where communityNumber = :communityNumber");
		hql.append(" and status = :status");
		hql.append(" and accessType = :accessType");
		hql.append(" and buildingName = :buildingName");
		hql.append(" and unitName = :unitName");
		hql.append(" order by createDate Desc ");
		TypedQuery<AccessManagement> typedQuery = super.entityManager.createQuery(hql.toString(), AccessManagement.class);
		typedQuery.setParameter("communityNumber", communityNumber);
		typedQuery.setParameter("status", status.staus);
		typedQuery.setParameter("accessType", accessType.ordinal());
		typedQuery.setParameter("buildingName", buildingName);
		typedQuery.setParameter("unitName", unitName);
		List<AccessManagement> list = typedQuery.getResultList();
		if(CollectionUtils.isNotEmpty(list)){
			return list.get(0);
		}else{
			return null;
		}
	}
	@Override
	public AccessManagement findByCommunityBuildingInfo(String communityNumber,
			String buildingName, String unitName) {
		StringBuffer hql = new StringBuffer("from AccessManagement where communityNumber = :communityNumber and status = 0");
		hql.append(" and ifnull(buildingName,'') = ifnull(:buildingName,'')");
		if(StringUtils.isNotBlank(unitName)){
			hql.append(" and ifnull(unitName,'') = ifnull(:unitName,'')");
		}
		hql.append(" order by createDate DESC ");
		TypedQuery<AccessManagement> typedQuery = super.entityManager.createQuery(hql.toString(), AccessManagement.class);
		typedQuery.setParameter("communityNumber", communityNumber);
		typedQuery.setParameter("buildingName", buildingName);
		if(StringUtils.isNotBlank(unitName)){
			typedQuery.setParameter("unitName", unitName);
		}
		List<AccessManagement> list = typedQuery.getResultList();
		if(CollectionUtils.isNotEmpty(list)){
			return list.get(0);
		}else{
			return null;
		}
	}
	@Override
	public List<AccessManagement> findAccessManagementByBuildingAndUnit(
			String communityNumber, Integer building, Integer unit) {
		StringBuffer hql = new StringBuffer("from AccessManagement where communityNumber = :communityNumber");
		hql.append(" and building = :building");
		if(unit != null){
			hql.append(" and unit = :unit");
		}
		TypedQuery<AccessManagement> typedQuery = super.entityManager.createQuery(hql.toString(), AccessManagement.class);
		typedQuery.setParameter("communityNumber", communityNumber);
		typedQuery.setParameter("building", building);
		if(unit != null){
			typedQuery.setParameter("unit", unit);
		}
		return typedQuery.getResultList();
	}
	@Override
	public List<AccessManagement> findByCommunity(String communityNumber) {
		StringBuffer hql = new StringBuffer("from AccessManagement where communityNumber = :communityNumber");
		TypedQuery<AccessManagement> typedQuery = super.entityManager.createQuery(hql.toString(), AccessManagement.class);
		typedQuery.setParameter("communityNumber", communityNumber);
		return typedQuery.getResultList();
	}
	@Override
	public AccessManagement findByBluetoothMac(String bluetoothMac){
		StringBuffer hql = new StringBuffer("from AccessManagement where bluetoothMac = :bluetoothMac and status = 0");
		TypedQuery<AccessManagement> typedQuery = super.entityManager.createQuery(hql.toString(), AccessManagement.class);
		typedQuery.setParameter("bluetoothMac", bluetoothMac);
		try{
			return typedQuery.getSingleResult();
		}catch (Exception e) {
			return null;
		}
	}
	@Override
	public List<AccessManagement> findByCommunityBuildingnameUnitname(
			String communityNumber, String building, String unit) {
		StringBuffer hql = new StringBuffer("from AccessManagement where communityNumber = :communityNumber and status = 0");
		if(StringUtils.isNotBlank(building)){
			hql.append(" and buildingName = :buildingName");
		}else{
			hql.append(" and (buildingName is null or buildingName = '')");
		}
		if(StringUtils.isNotBlank(unit)){
			hql.append(" and unitName = :unitName");
		}else{
			hql.append(" and (unitName is null or unitName = '')");
		}
		TypedQuery<AccessManagement> typedQuery = super.entityManager.createQuery(hql.toString(), AccessManagement.class);
		typedQuery.setParameter("communityNumber", communityNumber);
		if(StringUtils.isNotBlank(building)){
			typedQuery.setParameter("buildingName", building);
		}
		if(StringUtils.isNotBlank(unit)){
			typedQuery.setParameter("unitName", unit);
		}
		return typedQuery.getResultList();
	}
	@Override
	public AccessManagement findByBluetoothMacAndStatus(String bluetoothMac, Status status) {
		StringBuffer hql = new StringBuffer("from AccessManagement where 1=1 and bluetoothMac = :bluetoothMac");
		if(status!=null){
			hql.append(" and status = :status");
		}
		TypedQuery<AccessManagement> typedQuery = super.entityManager.createQuery(hql.toString(), AccessManagement.class);
		typedQuery.setParameter("bluetoothMac", bluetoothMac);
		if(status!=null){
			typedQuery.setParameter("status", status.staus);
		}
		try{
			return typedQuery.getSingleResult();
		}catch (Exception e) {
			return null;
		}
	}
	@Override
	public List<AccessManagement> findByStatus(Status status,AuthencateAccessType authencateAccessType) {
		StringBuffer sb =new StringBuffer(" from AccessManagement access where 1=1 and access.status = :status");
		if(authencateAccessType!=null){
			sb.append(" and access.authencateAccessType = :authencateAccessType");
		}
		TypedQuery<AccessManagement> query =this.entityManager.createQuery(sb.toString(),AccessManagement.class);
		query.setParameter("status", status.staus);
		if(authencateAccessType!=null){
			query.setParameter("authencateAccessType", authencateAccessType);
		}
		return query.getResultList();
	}
	@Override
	public List<AccessManagement> findByCommunitys(List<String> communityNumbers) {
		if (communityNumbers ==null||communityNumbers.size()<=0) {
			return Lists.newArrayList();
		}
		StringBuffer sb =new StringBuffer(" from AccessManagement access where communityNumber in (:communityNumber)");
		TypedQuery<AccessManagement> query =this.entityManager.createQuery(sb.toString(),AccessManagement.class);
		query.setParameter("communityNumber", communityNumbers);
		return query.getResultList();
	}
}
