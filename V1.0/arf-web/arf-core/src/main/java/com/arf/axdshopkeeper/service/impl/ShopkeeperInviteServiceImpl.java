package com.arf.axdshopkeeper.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arf.axdshopkeeper.dao.IShopkeeperInviteDao;
import com.arf.axdshopkeeper.entity.ShopkeeperInvite;
import com.arf.axdshopkeeper.entity.UserAccount.IdentityType;
import com.arf.axdshopkeeper.service.IShopkeeperInviteService;
import com.arf.base.PageResult;
import com.arf.core.dao.BaseDao;
import com.arf.core.service.impl.BaseServiceImpl;

@Service("shopkeeperInviteService")
public class ShopkeeperInviteServiceImpl extends
		BaseServiceImpl<ShopkeeperInvite, Long> implements
		IShopkeeperInviteService {

	@Resource(name = "shopkeeperInviteDao")
	private IShopkeeperInviteDao shopkeeperInviteDao;
	
	@Override
	protected BaseDao<ShopkeeperInvite, Long> getBaseDao() {
		return shopkeeperInviteDao;
	}

	@Override
	public PageResult<ShopkeeperInvite> findByCondition(Integer pageSize, Integer pageNo,String inviterUserName,
			IdentityType identityType) {
		return shopkeeperInviteDao.findByCondition(pageSize,pageNo,inviterUserName,identityType);
	}

	@Override
	public List<ShopkeeperInvite> findByBranchOrChannel(String inviterNumber,
			IdentityType identityType) {
		return shopkeeperInviteDao.findByBranchOrChannel(inviterNumber,identityType);
	}

	@Override
	public int findNewCount(String userName) {
		return shopkeeperInviteDao.findNewCount(userName);
	}

	@Override
	public void readMyInvite(String userName, String[] idList) {
		shopkeeperInviteDao.readMyInvite(userName,idList);
	}

}
