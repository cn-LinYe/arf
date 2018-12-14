package com.arf.office.dao;

import java.util.List;

import com.arf.core.dao.BaseDao;
import com.arf.office.entity.OfficeCompanyEquipmentRelation;

public interface IOfficeCompanyEquipmentRelationDao extends
		BaseDao<OfficeCompanyEquipmentRelation, Long> {

	OfficeCompanyEquipmentRelation findByMac(String mac);

	List<OfficeCompanyEquipmentRelation> findByAccessId(Long accessId);

}
