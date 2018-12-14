package com.arf.access.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.access.dao.IElevatorAuthorityDao;
import com.arf.access.entity.ElevatorAuthority;
import com.arf.access.entity.ElevatorAuthority.Status;
import com.arf.access.service.IElevatorAuthorityService;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;

@Service("elevatorAuthorityService")
public class ElevatorAuthorityServiceImpl extends BaseServiceImpl<ElevatorAuthority, Long> implements IElevatorAuthorityService {

	@Resource(name="elevatorAuthorityDao")
	IElevatorAuthorityDao elevatorAuthorityDao;

	@Override
	protected BaseDao<ElevatorAuthority, Long> getBaseDao() {
		return elevatorAuthorityDao;
	}

	@Override
	public List<ElevatorAuthority> findByUserName(String userName, Status status) {
		return elevatorAuthorityDao.findByUserName(userName, status);
	}
	
}
