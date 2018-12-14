package com.arf.axdshopkeeper.service;

import com.arf.axdshopkeeper.entity.UserAccount;
import com.arf.core.service.BaseService;

public interface IUserAccountService extends
		BaseService<UserAccount, Long> {
	/**
	 * 查询店主账户信息
	 * @param shopkeeper
	 * @return
	 */
	UserAccount findShopkeeper(String shopkeeper);

	/**
	 * 根据userName查询
	 * @param userName
	 * @return
	 */
	UserAccount findByUserName(String userName);
}
