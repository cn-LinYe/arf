package com.arf.office.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.office.dao.IOfficeCompanyEquipmentRelationDao;
import com.arf.office.entity.OfficeCompanyEquipmentRelation;
import com.arf.office.service.IOfficeCompanyEquipmentRelationService;

@Service("officeCompanyEquipmentRelationServiceImpl")
public class OfficeCompanyEquipmentRelationServiceImpl extends
		BaseServiceImpl<OfficeCompanyEquipmentRelation, Long> implements
		IOfficeCompanyEquipmentRelationService {

	@Resource(name = "officeCompanyEquipmentRelationDaoImpl")
	private IOfficeCompanyEquipmentRelationDao officeCompanyEquipmentRelationDaoImpl;
	
	@Override
	protected BaseDao<OfficeCompanyEquipmentRelation, Long> getBaseDao() {
		return officeCompanyEquipmentRelationDaoImpl;
	}

	@Override
	public OfficeCompanyEquipmentRelation findByMac(String mac) {
		return officeCompanyEquipmentRelationDaoImpl.findByMac(mac);
	}

	@Override
	public List<OfficeCompanyEquipmentRelation> findByAccessId(Long accessId) {
		return officeCompanyEquipmentRelationDaoImpl.findByAccessId(accessId);
	}

}
