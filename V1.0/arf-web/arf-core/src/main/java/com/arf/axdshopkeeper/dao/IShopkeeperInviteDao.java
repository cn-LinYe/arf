package com.arf.axdshopkeeper.dao;

import java.util.List;

import com.arf.axdshopkeeper.entity.ShopkeeperInvite;
import com.arf.axdshopkeeper.entity.UserAccount.IdentityType;
import com.arf.base.PageResult;
import com.arf.core.dao.BaseDao;

public interface IShopkeeperInviteDao extends BaseDao<ShopkeeperInvite, Long> {

	PageResult<ShopkeeperInvite> findByCondition(Integer pageSize, Integer pageNo,String inviterUserName,
			IdentityType identityType);

	List<ShopkeeperInvite> findByBranchOrChannel(String inviterNumber,
			IdentityType identityType);

	/**
	 * 查询新的未读邀请数量
	 * @param userName
	 * @return
	 */
	int findNewCount(String userName);

	/**
	 *  设置未读的邀请为已读
	 * @param idList
	 */
	void readMyInvite(String userName, String[] idList);

}
