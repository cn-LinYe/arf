package com.arf.goldcard.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;
import com.arf.goldcard.dao.IUserGoldCardAccountDao;
import com.arf.goldcard.dto.UserGoldCardAccountDto;
import com.arf.goldcard.entity.UserGoldCardAccount;
import com.arf.goldcard.service.IUserGoldCardAccountService;

@Service("userGoldCardAccountServiceImpl")
public class UserGoldCardAccountServiceImpl extends BaseServiceImpl<UserGoldCardAccount, Long> implements IUserGoldCardAccountService {

	@Resource(name = "userGoldCardAccountDaoImpl")
	private IUserGoldCardAccountDao userGoldCardAccountDao;
	
	@Override
	protected BaseDao<UserGoldCardAccount, Long> getBaseDao() {
		return userGoldCardAccountDao;
	}
	
	@Override
	public UserGoldCardAccountDto findByUserName(String userName) {
		return userGoldCardAccountDao.findByUserName(userName);
	}

	@Override
	public UserGoldCardAccount findByUserNameEntity(String userName) {
		return userGoldCardAccountDao.findByUserNameEntity(userName);
	}

}
