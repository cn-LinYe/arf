package com.arf.carbright.dao;

import com.arf.carbright.entity.PEngineeringMachineUser;
import com.arf.core.dao.BaseDao;

public interface IPEngineeringMachineUserDao extends BaseDao<PEngineeringMachineUser, Long>{

	/**
	 * 查找信息信息
	 * @return
	 */
	public PEngineeringMachineUser findByUser(String userName,String passwrod);
}
