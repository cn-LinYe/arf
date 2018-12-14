package com.arf.gas.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.gas.dao.RefuelGasUserDao;
import com.arf.gas.entity.RefuelGasUser;
import com.arf.gas.entity.RefuelGasUser.Status;
import com.arf.gas.service.RefuelGasUserService;

@Service("refuelGasUserService")
public class RefuelGasUserServiceImpl extends BaseServiceImpl<RefuelGasUser, Long> implements RefuelGasUserService{

	@Resource(name="refuelGasUserDao")
	RefuelGasUserDao refuelGasUserDao;
	
	@Override
	protected BaseDao<RefuelGasUser, Long> getBaseDao() {
		return refuelGasUserDao;
	}

	@Override
	public RefuelGasUser findByLoginAccount(String loginAccount) {
		return refuelGasUserDao.findByLoginAccount(loginAccount);
	}

	@Override
	public List<RefuelGasUser> findByGasNumBusiness(Integer gasNum, Integer businessNum, Status status) {
		return refuelGasUserDao.findByGasNumBusiness(gasNum, businessNum, status);
	}

}
