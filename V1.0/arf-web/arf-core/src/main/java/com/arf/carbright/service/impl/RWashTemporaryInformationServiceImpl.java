package com.arf.carbright.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.carbright.dao.IRWashTemporaryInformationDao;
import com.arf.carbright.entity.RWashTemporaryInformation;
import com.arf.carbright.service.IRWashTemporaryInformationService;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;

@Service("rWashTemporaryInformationServiceImpl")
public class RWashTemporaryInformationServiceImpl extends BaseServiceImpl<RWashTemporaryInformation, Long> implements IRWashTemporaryInformationService{

	@Resource(name ="rWashTemporaryInformationDaoImpl")
	private IRWashTemporaryInformationDao rWashTemporaryInformationDaoImpl;
	
	@Override
	protected BaseDao<RWashTemporaryInformation, Long> getBaseDao() {		
		return rWashTemporaryInformationDaoImpl;
	}

	@Override
	public List<RWashTemporaryInformation> findTemporaryUserbyCommunity(String communityNumber, String userName) {
		return rWashTemporaryInformationDaoImpl.findTemporaryUserbyCommunity(communityNumber, userName);
	}

}
