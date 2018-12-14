package com.arf.carbright.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.carbright.dao.IPEngineeringMachineUserDao;
import com.arf.carbright.entity.PEngineeringMachineUser;
import com.arf.carbright.service.IPEngineeringMachineUserService;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;

@Service("pEngineeringMachineUserServiceImpl")
public class PEngineeringMachineUserServiceImpl extends BaseServiceImpl<PEngineeringMachineUser, Long> implements IPEngineeringMachineUserService{

	@Resource(name="pEngineeringMachineUserDaoImpl")
	private IPEngineeringMachineUserDao pEngineeringMachineUserDaoImpl;
	
	@Override
	protected BaseDao<PEngineeringMachineUser, Long> getBaseDao() {
		return pEngineeringMachineUserDaoImpl;
	}
	
	@Override
	public PEngineeringMachineUser findByUser(String userName, String passwrod) {		
		return pEngineeringMachineUserDaoImpl.findByUser(userName, passwrod);
	}

}
