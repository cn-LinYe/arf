package com.arf.salesman.service;

import com.arf.core.service.BaseService;
import com.arf.salesman.entity.SalesmanInfo;

public interface ISalesmanInfoService extends BaseService<SalesmanInfo, Long> {

	/**
	 * 通过用户名查找
	 * @param userName
	 * @return
	 */
	SalesmanInfo findByUserName(String userName);
}
