package com.arf.office.service;

import java.util.List;

import com.arf.core.service.BaseService;
import com.arf.office.entity.OfficeCompanyEquipmentRelation;

public interface IOfficeCompanyEquipmentRelationService extends
		BaseService<OfficeCompanyEquipmentRelation, Long> {

	OfficeCompanyEquipmentRelation findByMac(String mac);

	List<OfficeCompanyEquipmentRelation> findByAccessId(Long accessId);

}
