package com.arf.axdshopkeeper.service;

import java.util.List;

import com.arf.axdshopkeeper.entity.ShopkeeperInvite;
import com.arf.axdshopkeeper.entity.UserAccount.IdentityType;
import com.arf.base.PageResult;
import com.arf.core.service.BaseService;

public interface IShopkeeperInviteService extends BaseService<ShopkeeperInvite, Long> {

	/**
	 * pageSize、pageNo为空，查询所有<br/>
	 * @param pageSize 
	 * @param pageNo
	 * @param inviterUserName
	 * @param identityType
	 * @return
	 */
	PageResult<ShopkeeperInvite> findByCondition(Integer pageSize, Integer pageNo,String inviterUserName,
			IdentityType identityType);

	List<ShopkeeperInvite> findByBranchOrChannel(String inviterNumber,IdentityType identityType);

	/*
	 * 查询新的未读的邀请数
	 */
	int findNewCount(String userName);

	/**
	 *  设置未读的邀请为已读
	 * @param idList
	 */
	void readMyInvite(String userName, String[] idList);
}
