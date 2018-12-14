package com.arf.gas.service;

import java.util.List;

import com.arf.core.service.BaseService;
import com.arf.gas.entity.RefuelGasUser;

public interface RefuelGasUserService extends BaseService<RefuelGasUser, Long> {
	
	/**
	 * 根据登录名查找
	 * @param loginAccount
	 * @return
	 */
	RefuelGasUser findByLoginAccount(String loginAccount);
	
	/**
	 * 根据油站编号及商户编号查询
	 * @param gasNum
	 * @param businessNum
	 * @param status
	 * @return
	 */
	List<RefuelGasUser> findByGasNumBusiness(Integer gasNum,Integer businessNum,RefuelGasUser.Status status);

}
