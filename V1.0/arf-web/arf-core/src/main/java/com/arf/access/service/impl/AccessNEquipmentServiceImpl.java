package com.arf.access.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.access.dao.IAccessNEquipmentDao;
import com.arf.access.entity.AccessNEquipment;
import com.arf.access.service.IAccessNEquipmentService;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;

@Service("accessNEquipmentService")
public class AccessNEquipmentServiceImpl extends BaseServiceImpl<AccessNEquipment, Long> implements IAccessNEquipmentService{

	@Resource(name="accessNEquipmentDao")
	IAccessNEquipmentDao accessNEquipmentDao;
	
	@Override
	protected BaseDao<AccessNEquipment, Long> getBaseDao() {
		return accessNEquipmentDao;
	}
	
	@Override
	public List<AccessNEquipment> findByCommunityAndType(String communityNo, Integer equipmentType) {
		return accessNEquipmentDao.findByCommunityAndType(communityNo, equipmentType);
	}

	@Override
	public List<Map<String, Object>> findByCommunitys(List<String> communityList) {
		return accessNEquipmentDao.findByCommunitys(communityList);
	}

	@Override
	public List<AccessNEquipment> findByCommunityByBuilding(String communityNo,String buildName,String unitName,Integer building,Integer unit) {
		return accessNEquipmentDao.findByCommunityByBuilding(communityNo, buildName, unitName, building, unit);
	}

	@Override
	public AccessNEquipment findByAccessNum(String accessNumber) {
		return accessNEquipmentDao.findByAccessNum(accessNumber);
	}

}
