package com.arf.carbright.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.carbright.dao.IREngineeringBoxSynchInfoDao;
import com.arf.carbright.entity.REngineeringBoxSynchInfo;
import com.arf.carbright.service.IREngineeringBoxSynchInfoService;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;

@Service("rEngineeringBoxSynchInfoServiceImpl")
public class REngineeringBoxSynchInfoServiceImpl extends BaseServiceImpl<REngineeringBoxSynchInfo, Long> implements IREngineeringBoxSynchInfoService {

	@Resource(name ="rEngineeringBoxSynchInfoDaoImpl")
	private IREngineeringBoxSynchInfoDao rEngineeringBoxSynchInfoDaoImpl;
	@Override
	
	protected BaseDao<REngineeringBoxSynchInfo, Long> getBaseDao() {		
		return rEngineeringBoxSynchInfoDaoImpl;
	}
	@Override
	public int statisticsCountByBox(String communityNumber, String cabinetNum, String boxNum) {
		return rEngineeringBoxSynchInfoDaoImpl.statisticsCountByBox(communityNumber, cabinetNum, boxNum);
	}

}
