package com.arf.salesman.dao;

import com.arf.core.dao.BaseDao;
import com.arf.salesman.entity.SalesmanInfo;

public interface ISalesmanInfoDao extends BaseDao<SalesmanInfo, Long> {

	/**
	 * 通过用户名查找
	 * @param userName
	 * @return
	 */
	SalesmanInfo findByUserName(String userName);
}
