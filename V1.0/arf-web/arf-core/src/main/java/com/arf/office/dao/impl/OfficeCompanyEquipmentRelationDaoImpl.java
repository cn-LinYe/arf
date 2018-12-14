package com.arf.office.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.arf.core.dao.impl.BaseDaoImpl;
import com.arf.office.dao.IOfficeCompanyEquipmentRelationDao;
import com.arf.office.entity.OfficeCompanyEquipmentRelation;

@Repository("officeCompanyEquipmentRelationDaoImpl")
public class OfficeCompanyEquipmentRelationDaoImpl extends
		BaseDaoImpl<OfficeCompanyEquipmentRelation, Long> implements
		IOfficeCompanyEquipmentRelationDao {

	@Override
	public OfficeCompanyEquipmentRelation findByMac(String mac) {
		StringBuffer hql = new StringBuffer("from OfficeCompanyEquipmentRelation a where 1 = 1 and a.bluetoothMac = :bluetoothMac");
		TypedQuery<OfficeCompanyEquipmentRelation> typedQuery = entityManager.createQuery(hql.toString(), OfficeCompanyEquipmentRelation.class);
		typedQuery.setParameter("bluetoothMac", mac);
		try {
			return typedQuery.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<OfficeCompanyEquipmentRelation> findByAccessId(Long accessId) {
		if(accessId == null){
			return null;
		}
		StringBuffer hql = new StringBuffer("from OfficeCompanyEquipmentRelation a where 1 = 1 and a.accessId = :accessId");
		TypedQuery<OfficeCompanyEquipmentRelation> typedQuery = entityManager.createQuery(hql.toString(), OfficeCompanyEquipmentRelation.class);
		typedQuery.setParameter("accessId", accessId);
		return typedQuery.getResultList();
	}

}
