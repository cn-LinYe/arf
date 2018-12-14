package com.arf.access.dao;

import java.util.List;

import com.arf.access.entity.ElevatorAuthority;
import com.arf.core.dao.BaseDao;

public interface IElevatorAuthorityDao extends BaseDao<ElevatorAuthority, Long>{

	/**
	 * 根据用户名和状态查询
	 * @param userName
	 * @param status
	 * @return
	 */
	List<ElevatorAuthority> findByUserName(String userName,ElevatorAuthority.Status status);
}
