package com.arf.axdshopkeeper.dao;

import com.arf.axdshopkeeper.dto.ShopkeeperCommunityDto;
import com.arf.axdshopkeeper.entity.ShopkeeperCommunity;
import com.arf.base.PageResult;
import com.arf.core.dao.BaseDao;

public interface IShopkeeperCommunityDao extends
		BaseDao<ShopkeeperCommunity, Long> {

	PageResult<ShopkeeperCommunity> findByCondition(Integer pageSize,
			Integer pageNo, String cityNo, ShopkeeperCommunityDto.OrderBy orderBy);

}
