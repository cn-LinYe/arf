package com.arf.carbright.service;

import com.arf.carbright.entity.PEngineeringMachineUser;
import com.arf.core.service.BaseService;

public interface IPEngineeringMachineUserService extends BaseService<PEngineeringMachineUser, Long>{
	/**
	 * 查找信息信息
	 * @return
	 */
	public PEngineeringMachineUser findByUser(String userName,String passwrod);
}
