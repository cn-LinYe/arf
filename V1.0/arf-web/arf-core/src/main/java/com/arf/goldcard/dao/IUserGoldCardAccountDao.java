package com.arf.goldcard.dao;

import com.arf.core.dao.BaseDao;
import com.arf.goldcard.dto.UserGoldCardAccountDto;
import com.arf.goldcard.entity.UserGoldCardAccount;

public interface IUserGoldCardAccountDao extends BaseDao<UserGoldCardAccount, Long> {

	/**
	 * 根据用户名查询用户金卡信息
	 * @param userName
	 * @return
	 */
	public UserGoldCardAccountDto findByUserName(String userName);
	
	/**
	 * 通过用户名查找实体（与上面方法不同上面返回的是dto）
	 * @param userName
	 * @return
	 */
	public UserGoldCardAccount findByUserNameEntity(String userName);
}
